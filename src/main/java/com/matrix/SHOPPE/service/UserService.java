package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.DTO.UserAddRequestDTO;
import com.matrix.SHOPPE.model.DTO.UserDTO;
import com.matrix.SHOPPE.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  List<UserDTO> getUsers();
  UserDTO getUserById(Integer id);
  UserDTO addUser(UserAddRequestDTO user);
  UserDTO updateUser(UserAddRequestDTO user);
  void deleteUser(Integer id);
}
