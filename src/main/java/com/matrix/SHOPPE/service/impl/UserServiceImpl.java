package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.model.User;
import com.matrix.SHOPPE.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(Integer id) {

    }
}
