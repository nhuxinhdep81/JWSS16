package com.tien.config;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/ss16";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";


    public static Connection openConnection(){
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn, CallableStatement callSt){
        try {
            if (conn!=null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (callSt!=null){
                callSt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
