package com.example.hospital.service;

import com.example.hospital.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();
    List<User> getAllUserWithoutRole();
    List<User> getAllNurse();
}
