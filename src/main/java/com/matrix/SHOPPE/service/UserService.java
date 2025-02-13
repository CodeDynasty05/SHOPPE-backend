package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  List<User> getUsers();
  User getUserById(Integer id);
  void addUser(User user);
  void updateUser(User user);
  void deleteUser(Integer id);
}
