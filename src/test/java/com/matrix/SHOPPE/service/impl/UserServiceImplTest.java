package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.BadRequest;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.jwt.JwtService;
import com.matrix.SHOPPE.mapper.UserMapper;
import com.matrix.SHOPPE.model.dto.*;
import com.matrix.SHOPPE.model.entity.User;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDto testUserDto;
    private UserAddRequestDto testUserAddRequestDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");

        testUserDto = new UserDto();
        testUserDto.setId(1);
        testUserDto.setUsername("testUser");
        testUserDto.setEmail("test@example.com");

        testUserAddRequestDto = new UserAddRequestDto();
        testUserAddRequestDto.setUsername("testUser");
        testUserAddRequestDto.setEmail("test@example.com");
        testUserAddRequestDto.setPassword("password");
    }

    @Test
    void getUsers_ShouldReturnListOfUsers() {
        List<User> users = Collections.singletonList(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUserDTO(any(User.class))).thenReturn(testUserDto);

        List<UserDto> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDto);

        UserDto result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(testUserDto.getId(), result.getId());
    }

    @Test
    void createUser_ShouldCreateNewUser() {
        when(userMapper.toUser(testUserAddRequestDto)).thenReturn(testUser);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDto);

        UserDto result = userService.createUser(testUserAddRequestDto);

        assertNotNull(result);
        assertEquals(testUserDto.getId(), result.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_ShouldReturnJwtToken() {
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setUsernameOrEmail("testUser");
        requestDto.setPassword("password");

        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(testUser));
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.issueToken(any(User.class))).thenReturn("token");

        RegisterResponseDto result = userService.login(requestDto);

        assertNotNull(result);
        assertEquals("token", result.getJwtToken());
    }

    @Test
    void login_ShouldThrowExceptionWhenUserNotFound() {
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setUsernameOrEmail("testUser");
        requestDto.setPassword("password");

        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.login(requestDto)
        );
    }

    @Test
    void forgotPassword_ShouldGenerateValidationCode() {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("HTML Content");

        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDto);

        UserDto result = userService.forgotPassword("testUser");

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        verify(javaMailSender).send(any(MimeMessage.class));
    }


    @Test
    void resetPassword_ShouldUpdatePassword() {
        ResetPasswordDto resetDto = new ResetPasswordDto();
        resetDto.setUsernameOrEmail("testUser");
        resetDto.setValidationCode(123456);
        resetDto.setNewPassword("newPassword");

        testUser.setValidationCode(123456);
        testUser.setValidationCodeTimestamp(LocalDateTime.now());

        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(testUser));
        when(encoder.encode(anyString())).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toUserDTO(testUser)).thenReturn(testUserDto);

        UserDto result = userService.resetPassword(resetDto);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void resetPassword_ShouldThrowExceptionWhenValidationCodeExpired() {
        ResetPasswordDto resetDto = new ResetPasswordDto();
        resetDto.setUsernameOrEmail("testUser");
        resetDto.setValidationCode(123456);

        testUser.setValidationCode(123456);
        testUser.setValidationCodeTimestamp(LocalDateTime.now().minusHours(1));

        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(testUser));

        assertThrows(BadRequest.class, () ->
                userService.resetPassword(resetDto)
        );
    }
}