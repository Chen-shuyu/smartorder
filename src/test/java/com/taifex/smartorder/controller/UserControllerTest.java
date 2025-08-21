package com.taifex.smartorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taifex.smartorder.dto.UserDTO;
import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser_shouldReturnSavedUser() throws Exception{
        UserDTO dto = new UserDTO();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setAge(25);

        User fakeUser = User.builder()
                .id(1L)
                .name("Alice")
                .email("alice@example.com")
                .age(25)
                .build();

        when(userService.saveUser(any(UserDTO.class))).thenReturn(fakeUser);

        mockMvc.perform(post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void gerUserById_shouldReturnUserIfExists() throws Exception{
        User fakeUser = User.builder()
                .id(1L)
                .name("Bob")
                .email("bob@example.com")
                .age(30)
                .build();

        when(userService.getUserById(1L)).thenReturn(Optional.of(fakeUser));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.email").value("bob@example.com"));

    }

    @Test
    void getUserBtId_shouldReturn404IfNotExists() throws Exception{
        when(userService.getUserById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());

    }
}
