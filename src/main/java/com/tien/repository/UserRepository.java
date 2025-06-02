package com.tien.repository;

import com.tien.dto.UserDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public interface UserRepository {
    void save(UserDTO user, Connection conn) throws Exception;
    String findRoleByUsernameAndPassword(String username, String password, Connection conn) throws Exception;
}