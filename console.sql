create database if not exists ss16;
use ss16;

create table if not exists users (
                                     id int auto_increment primary key,
                                     username varchar(50) not null unique,
                                     password varchar(100) not null,
                                     email varchar(100) not null unique,
                                     role varchar(20) default 'USER',
                                     status varchar(20) default 'ACTIVE'
);

create table if not exists trips (
                                     id int auto_increment primary key,
                                     departure varchar(100) not null,
                                     destination varchar(100) not null,
                                     departureTime datetime,
                                     price decimal(10, 2)
);

create table if not exists buses (
                                     id int auto_increment primary key,
                                     licensePlate varchar(20) not null unique,
                                     busType varchar(20) not null,
                                     rowSeat int not null,
                                     colSeat int not null,
                                     totalSeat int not null,
                                     image varchar(255)
);

create table if not exists seats (
                                     id int auto_increment primary key,
                                     nameSeat varchar(10) not null,
                                     price decimal(10, 2) not null,
                                     busId int not null,
                                     status varchar(20) default 'AVAILABLE',
                                     foreign key (busId) references buses(id)
);

insert ignore into users (username, password, email, role) values
                                                               ('admin', 'admin123', 'admin@example.com', 'ADMIN'),
                                                               ('user1', 'user123', 'user1@example.com', 'USER'),
                                                               ('user2', 'user456', 'user2@example.com', 'USER');

insert ignore into trips (departure, destination, departureTime, price) values
                                                                            ('Hà Nội', 'Đà Nẵng', '2025-06-02 08:00:00', 500000.00),
                                                                            ('Đà Nẵng', 'Hồ Chí Minh', '2025-06-02 14:00:00', 600000.00),
                                                                            ('Hồ Chí Minh', 'Hà Nội', '2025-06-03 06:00:00', 550000.00),
                                                                            ('Hải Phòng', 'Quảng Ninh', '2025-06-03 10:00:00', 300000.00);

insert ignore into buses (licensePlate, busType, rowSeat, colSeat, totalSeat, image) values
                                                                                         ('51H-12345', 'NORMAL', 5, 4, 20, 'https://example.com/bus1.jpg'),
                                                                                         ('51H-67890', 'VIP', 6, 5, 30, 'https://example.com/bus2.jpg'),
                                                                                         ('51H-11111', 'LUXURY', 7, 6, 42, 'https://example.com/bus3.jpg');

delimiter //
create procedure GetTrips(
    in p_departure varchar(100),
    in p_destination varchar(100),
    in p_page int,
    in p_size int,
    out p_total int
)
begin
    declare v_offset int;
    set v_offset = (p_page - 1) * p_size;

    select count(*) into p_total
    from trips
    where (p_departure is null or departure like concat('%', p_departure, '%'))
      and (p_destination is null or destination like concat('%', p_destination, '%'));

    select * from trips
    where (p_departure is null or departure like concat('%', p_departure, '%'))
      and (p_destination is null or destination like concat('%', p_destination, '%'))
    limit p_size offset v_offset;
end //
delimiter ;

delimiter //
create procedure AddBusWithSeats(
    in p_licensePlate varchar(20),
    in p_busType varchar(20),
    in p_rowSeat int,
    in p_colSeat int,
    in p_image varchar(255)
)
begin
    declare v_totalSeat int;
    set v_totalSeat = p_rowSeat * p_colSeat;
    insert into buses (licensePlate, busType, rowSeat, colSeat, totalSeat, image)
    values (p_licensePlate, p_busType, p_rowSeat, p_colSeat, v_totalSeat, p_image);

    set @busId = last_insert_id();
    set @price = case p_busType
                     when 'NORMAL' then 100000
                     when 'VIP' then 150000
                     when 'LUXURY' then 200000
                     else 100000
        end;

    set @row = 1;
    while @row <= p_rowSeat do
            set @col = 1;
            while @col <= p_colSeat do
                    insert into seats (nameSeat, price, busId, status)
                    values (concat('S', @row, @col), @price, @busId, 'AVAILABLE');
                    set @col = @col + 1;
                end while;
            set @row = @row + 1;
        end while;
end //
delimiter ;

delimiter //
create procedure UpdateBus(
    in p_id int,
    in p_licensePlate varchar(20),
    in p_busType varchar(20),
    in p_rowSeat int,
    in p_colSeat int,
    in p_image varchar(255)
)
begin
    update buses
    set licensePlate = p_licensePlate,
        busType = p_busType,
        rowSeat = p_rowSeat,
        colSeat = p_colSeat,
        totalSeat = p_rowSeat * p_colSeat,
        image = p_image
    where id = p_id;
end //
delimiter ;

delimiter //
create procedure DeleteBus(
    in p_id int
)
begin
    delete from seats where busId = p_id;
    delete from buses where id = p_id;
end //
delimiter ;

delimiter //
create procedure GetBusById(
    in p_id int
)
begin
    select * from buses where id = p_id;
end //
delimiter ;

delimiter //
create procedure GetAllBuses()
begin
    select * from buses;
end //
delimiter ;

delimiter //
create procedure RegisterUser(
    in p_username varchar(50),
    in p_password varchar(100),
    in p_email varchar(100),
    in p_role varchar(20)
)
begin
    insert into users (username, password, email, role)
    values (p_username, p_password, p_email, p_role);
end //
delimiter ;

delimiter //
create procedure LoginUser(
    in p_username varchar(50),
    in p_password varchar(100),
    out p_role varchar(20)
)
begin
    select role into p_role
    from users
    where username = p_username and password = p_password;
end //
delimiter ;

create table if not exists bus_trips (
                                         id int auto_increment primary key,
                                         departurePoint varchar(100) not null,
                                         destination varchar(100) not null,
                                         departureTime datetime not null,
                                         arrivalTime datetime not null,
                                         busId int not null,
                                         seatsAvailable int not null,
                                         image varchar(255),
                                         foreign key (busId) references buses(id)
);

delimiter //
create procedure AddBusTrip(
    in p_departurePoint varchar(100),
    in p_destination varchar(100),
    in p_departureTime datetime,
    in p_arrivalTime datetime,
    in p_busId int,
    in p_seatsAvailable int,
    in p_image varchar(255)
)
begin
    insert into bus_trips (departurePoint, destination, departureTime, arrivalTime, busId, seatsAvailable, image)
    values (p_departurePoint, p_destination, p_departureTime, p_arrivalTime, p_busId, p_seatsAvailable, p_image);
end //
delimiter ;

delimiter //
create procedure UpdateBusTrip(
    in p_id int,
    in p_departurePoint varchar(100),
    in p_destination varchar(100),
    in p_departureTime datetime,
    in p_arrivalTime datetime,
    in p_busId int,
    in p_seatsAvailable int,
    in p_image varchar(255)
)
begin
    update bus_trips
    set departurePoint = p_departurePoint,
        destination = p_destination,
        departureTime = p_departureTime,
        arrivalTime = p_arrivalTime,
        busId = p_busId,
        seatsAvailable = p_seatsAvailable,
        image = p_image
    where id = p_id;
end //
delimiter ;

delimiter //
create procedure DeleteBusTrip(
    in p_id int
)
begin
    delete from bus_trips where id = p_id;
end //
delimiter ;

delimiter //
create procedure GetBusTripById(
    in p_id int
)
begin
    select * from bus_trips where id = p_id;
end //
delimiter ;

delimiter //
create procedure GetAllBusTrips()
begin
    select * from bus_trips;
end //
delimiter ;
