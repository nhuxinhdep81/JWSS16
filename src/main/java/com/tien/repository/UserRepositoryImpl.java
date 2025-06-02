package com.tien.repository;

import com.tien.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepositoryImpl implements UserRepository {


    @Override
    public void save(UserDTO user, Connection conn) throws Exception {
        String sql = "{CALL RegisterUser(?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole().name());
            stmt.executeUpdate();
        }
    }

    @Override
    public String findRoleByUsernameAndPassword(String username, String password, Connection conn) throws Exception {
        String sql = "{CALL LoginUser(?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.execute();
            return stmt.getString(3);
        }
    }
}