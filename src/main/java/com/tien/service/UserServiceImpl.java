package com.tien.service;

import com.tien.config.ConnectionDB;
import com.tien.dto.UserDTO;
import com.tien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(UserDTO user) {
        try (Connection conn = ConnectionDB.openConnection()) {
            conn.setAutoCommit(false);
            userRepository.save(user, conn);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findRoleByUsernameAndPassword(String username, String password) {
        try (Connection conn = ConnectionDB.openConnection()) {
            return userRepository.findRoleByUsernameAndPassword(username, password, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}