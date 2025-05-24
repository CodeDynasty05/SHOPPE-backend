package com.matrix.SHOPPE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrix.SHOPPE.model.dto.UserAddRequestDto;
import com.matrix.SHOPPE.model.dto.UserDto;
import com.matrix.SHOPPE.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private UserDto userDTO;
    private UserAddRequestDto userAddRequestDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDto();
        userDTO.setId(25);
        userDTO.setUsername("test");
        userDTO.setPhone("123456789");
        userDTO.setEmail("test@test.com");

        userAddRequestDTO = new UserAddRequestDto();
        userAddRequestDTO.setEmail("test2@test.com");
        userAddRequestDTO.setPhone("235253");
        userAddRequestDTO.setUsername("test2");
        userAddRequestDTO.setPassword("hardToFind");
    }

    @AfterEach
    void tearDown() {
        userDTO = null;
        userAddRequestDTO = null;
    }

    @Test
    void getUser() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(userDTO);

        mvc.perform(get("/users/{id}", userDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(25))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andDo(print());

        verify(userService,times(1)).getUserById(anyInt());
    }

    @Test
    void addUser() throws Exception {
        when(userService.createUser(any(UserAddRequestDto.class))).thenReturn(userDTO);

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userAddRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andDo(print());

        verify(userService,times(1)).createUser(any(UserAddRequestDto.class));
    }
}