package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.RegisterRequestDto;
import com.matrix.SHOPPE.model.dto.RegisterResponseDto;
import com.matrix.SHOPPE.model.dto.UserAddRequestDto;
import com.matrix.SHOPPE.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
  List<UserDto> getUsers();
  UserDto getUserById(Integer id);
  UserDto createUser(UserAddRequestDto user);
  UserDto updateUser(UserAddRequestDto user, Integer id);
  void deleteUser(Integer id);
  RegisterResponseDto login(RegisterRequestDto registerRequestDto);
  void updateRole(Integer userId,String role,Integer senderId);
}
