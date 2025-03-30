package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.ResourceNotFoundException;
import com.matrix.SHOPPE.mapper.UserMapper;
import com.matrix.SHOPPE.model.DTO.UserAddRequestDTO;
import com.matrix.SHOPPE.model.DTO.UserDTO;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private  final UserMapper userMapper;

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Integer id) {
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("This user doesn't exist")));
    }

    @Override
    public UserDTO addUser(UserAddRequestDTO user) {
        //Some password encryption process
        User userEntity = userMapper.toUser(user);
        return userMapper.toUserDTO(userRepository.save(userEntity));
    }

    @Override
    public UserDTO updateUser(UserAddRequestDTO user) {
        //Some password decryption process
        User userEntity = userMapper.toUser(user);
        return userMapper.toUserDTO(userRepository.save(userEntity));
    }

    @Override
    public void deleteUser(Integer id) {

    }
}
