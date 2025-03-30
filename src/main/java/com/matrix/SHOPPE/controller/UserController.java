package com.matrix.SHOPPE.controller;

import com.matrix.SHOPPE.model.DTO.UserAddRequestDTO;
import com.matrix.SHOPPE.model.DTO.UserDTO;
import com.matrix.SHOPPE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final    UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody UserAddRequestDTO user) {
        return userService.updateUser(user);
    }

    @PostMapping
    public UserDTO addUser(@RequestBody UserAddRequestDTO user) {
        return userService.addUser(user);
    }
}
