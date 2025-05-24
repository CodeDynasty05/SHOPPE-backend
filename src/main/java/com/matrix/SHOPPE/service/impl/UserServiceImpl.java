package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.BadRequest;
import com.matrix.SHOPPE.exception.ForbiddenException;
import com.matrix.SHOPPE.exception.ResourceNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.jwt.JwtService;
import com.matrix.SHOPPE.mapper.UserMapper;
import com.matrix.SHOPPE.model.dto.RegisterRequestDto;
import com.matrix.SHOPPE.model.dto.RegisterResponseDto;
import com.matrix.SHOPPE.model.dto.UserAddRequestDto;
import com.matrix.SHOPPE.model.dto.UserDto;
import com.matrix.SHOPPE.model.entity.Authority;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private  final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id) {
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(()->new UserNotFoundException("This user doesn't exist")));
    }

    @Override
    public UserDto createUser(UserAddRequestDto user) {
        User userEntity = userMapper.toUser(user);
        userEntity.setPassword(encoder.encode(user.getPassword()));

        if (userEntity.getAuthorities() == null) {
            userEntity.setAuthorities(new HashSet<>());
        }
        
        Authority authority = new Authority();
        authority.setAuthority("USER");
        userEntity.getAuthorities().add(authority);
        authority.setUser(userEntity);
        return userMapper.toUserDTO(userRepository.save(userEntity));
    }

    @Override
    public UserDto updateUser(UserAddRequestDto user, Integer id) {
        User userEntity = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("This user doesn't exist"));
        User newUser = userMapper.updateUser(user,userEntity);
        return userMapper.toUserDTO(userRepository.save(newUser));
    }

    @Override
    public void deleteUser(Integer id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("This user doesn't exist");
        }
    }

    @Override
    public RegisterResponseDto login(RegisterRequestDto registerRequestDto) {
        User user = userRepository.findByUsernameOrEmail(registerRequestDto.getUsernameOrEmail(),registerRequestDto.getUsernameOrEmail())
                .orElseThrow(()->new UserNotFoundException("This user doesn't exist"));
        if(!encoder.matches(registerRequestDto.getPassword(),user.getPassword())){
            throw new BadRequest("Incorrect password");
        }

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();
        registerResponseDto.setJwtToken(jwtService.issueToken(user));
        return registerResponseDto;
    }

    @Override
    public void updateRole(Integer userId, String role,Integer senderId) {
        User sender = userRepository.findById(senderId).orElseThrow(()->new UserNotFoundException("This user doesn't exist"));
        User userEntity = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("This user doesn't exist"));
        sender.getAuthorities().stream().filter(authority -> authority.getAuthority().equals(role)).findAny().orElseThrow(()->new ForbiddenException("Only admin can change role of user."));
        Authority authority = new Authority();
        authority.setAuthority(role);
        authority.setUser(userEntity);

        if (userEntity.getAuthorities() == null) {
            userEntity.setAuthorities(new HashSet<>());
        }
        
        userEntity.getAuthorities().add(authority);
        userRepository.save(userEntity);
    }
}