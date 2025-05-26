package com.matrix.SHOPPE.service;

import com.matrix.SHOPPE.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(Integer id);

    UserDto createUser(UserAddRequestDto user);

    UserDto updateUser(UserAddRequestDto user, String token);

    void deleteUser(Integer id);

    RegisterResponseDto login(RegisterRequestDto registerRequestDto);

    void updateRole(Integer userId, String role, String token);

    UserDto forgotPassword(String usernameOrEmail);

    UserDto resetPassword(ResetPasswordDto resetPasswordDto);
}
