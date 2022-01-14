package com.example.practical.service;


import com.example.practical.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(int id);

    boolean delete(User user);

    User update( User user);
}
