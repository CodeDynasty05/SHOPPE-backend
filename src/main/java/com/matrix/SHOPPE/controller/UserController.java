package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.dto.*;
import com.matrix.SHOPPE.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/change-credentials")
    public UserDto changeCredentials(@RequestBody UserAddRequestDto user, @RequestHeader(name = "Authorization") String token) {
        return userService.updateUser(user, token);
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody UserAddRequestDto user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public RegisterResponseDto getUser(@RequestBody RegisterRequestDto registerRequestDto) {
        return userService.login(registerRequestDto);
    }

    @PutMapping("/update-role")
    public void updateRole(@RequestParam Integer userId, @RequestParam String role, @RequestHeader(name = "Authorization") String token) {
        userService.updateRole(userId, role, token);
    }

    @PutMapping("/forgot-password")
    public UserDto forgotPassword(@RequestParam String usernameOrEmail) {
        return userService.forgotPassword(usernameOrEmail);
    }

    @PostMapping("/reset-password")
    public UserDto resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return userService.resetPassword(resetPasswordDto);
    }
}
