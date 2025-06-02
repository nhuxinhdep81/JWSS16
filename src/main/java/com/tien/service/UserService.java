package com.tien.service;

import com.tien.dto.UserDTO;

public interface UserService {
    void saveUser(UserDTO user);
    String findRoleByUsernameAndPassword(String username, String password);
}