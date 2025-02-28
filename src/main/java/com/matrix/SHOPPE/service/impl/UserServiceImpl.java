package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.model.User;
import com.matrix.SHOPPE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void addUser(User user) {
        //Some password encryption process
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        //Some password decryption process
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {

    }
}
