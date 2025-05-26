package com.matrix.SHOPPE.service.impl;

import com.matrix.SHOPPE.Repository.UserRepository;
import com.matrix.SHOPPE.exception.BadRequest;
import com.matrix.SHOPPE.exception.ForbiddenException;
import com.matrix.SHOPPE.exception.ResourceNotFoundException;
import com.matrix.SHOPPE.exception.UserNotFoundException;
import com.matrix.SHOPPE.jwt.JwtService;
import com.matrix.SHOPPE.mapper.UserMapper;
import com.matrix.SHOPPE.model.dto.*;
import com.matrix.SHOPPE.model.entity.Authority;
import com.matrix.SHOPPE.model.entity.User;
import com.matrix.SHOPPE.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public List<UserDto> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id) {
        log.info("Fetching user by ID: {}", id);
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("This user doesn't exist")));
    }

    @Override
    public UserDto createUser(UserAddRequestDto user) {
        log.info("Creating new user with username: {}", user.getUsername());
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
    public UserDto updateUser(UserAddRequestDto user, String token) {
        User sender = getUserFromToken(token);
        log.info("Updating user with ID: {}", sender.getId());
        User newUser = userMapper.updateUser(user, sender);
        return userMapper.toUserDTO(userRepository.save(newUser));
    }

    @Override
    public void deleteUser(Integer id) {
        log.info("Deleting user with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("This user doesn't exist");
        }
    }

    @Override
    public RegisterResponseDto login(RegisterRequestDto registerRequestDto) {
        log.info("Attempting login for user: {}", registerRequestDto.getUsernameOrEmail());
        User user = userRepository.findByUsernameOrEmail(registerRequestDto.getUsernameOrEmail(), registerRequestDto.getUsernameOrEmail())
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist"));
        if (!encoder.matches(registerRequestDto.getPassword(), user.getPassword())) {
            throw new BadRequest("Incorrect password");
        }

        RegisterResponseDto registerResponseDto = new RegisterResponseDto();
        registerResponseDto.setJwtToken(jwtService.issueToken(user));
        return registerResponseDto;
    }

    @Override
    public void updateRole(Integer userId, String role, String token) {
        log.info("Updating role of user with ID: {} to {}", userId, role);
        Claims claims = jwtService.validateToken(token.substring("bearer".length()).trim());
        List<String> roles = claims.get("roles", List.class);
        User userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("This user doesn't exist"));
        if (!roles.contains("ADMIN")) {
            throw new ForbiddenException("You don't have permission to update role of this user");
        } else if (userEntity.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(role))) {
            throw new BadRequest("This role is already assigned to this user");
        }
        Authority authority = new Authority();
        authority.setAuthority(role);
        authority.setUser(userEntity);

        if (userEntity.getAuthorities() == null) {
            userEntity.setAuthorities(new HashSet<>());
        }

        userEntity.getAuthorities().add(authority);
        userRepository.save(userEntity);
    }

    @Override
    public UserDto forgotPassword(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist"));
        user.setValidationCode(generateValidationCode());
        user.setValidationCodeTimestamp(LocalDateTime.now());
        userRepository.save(user);
        sendEmailWithHtmlTemplate(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDto resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByUsernameOrEmail(resetPasswordDto.getUsernameOrEmail(), resetPasswordDto.getUsernameOrEmail())
                .orElseThrow(() -> new UserNotFoundException("This user doesn't exist"));

        if (!isValidationCodeValid(user)) {
            throw new BadRequest("Validation code has expired. Please request a new one.");
        }

        if (!Objects.equals(user.getValidationCode(), resetPasswordDto.getValidationCode())) {
            throw new BadRequest("Incorrect validation code");
        }

        user.setPassword(encoder.encode(resetPasswordDto.getNewPassword()));
        user.setValidationCode(null);
        user.setValidationCodeTimestamp(null);

        return userMapper.toUserDTO(userRepository.save(user));
    }


    private int generateValidationCode() {
        return 100000 + (int) (Math.random() * 900000);
    }


    private User getUserFromToken(String token) {
        Claims claims = jwtService.validateToken(token.substring("bearer".length()).trim());
        String username = claims.get("sub", String.class);
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void sendEmailWithHtmlTemplate(User user) {
        Context context = new Context();

        context.setVariable("username", user.getUsername());
        context.setVariable("validationCode", user.getValidationCode());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setFrom("nazimqenizade73@gmail.com");
            helper.setSubject("Password Reset Validation Code");

            String htmlContent = templateEngine.process("forgot-password", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

            log.info("Password reset validation code email sent successfully to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send validation code email to: {}", user.getEmail(), e);
            throw new RuntimeException("Failed to send validation code email", e);
        }
    }

    private boolean isValidationCodeValid(User user) {
        if (user.getValidationCode() == null || user.getValidationCodeTimestamp() == null) {
            return false;
        }

        LocalDateTime expirationTime = user.getValidationCodeTimestamp().plusMinutes(15);
        return LocalDateTime.now().isBefore(expirationTime);
    }
}