package com.matrix.SHOPPE.dao;

import com.matrix.SHOPPE.model.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    User getUserById(Integer id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Integer id);
}
