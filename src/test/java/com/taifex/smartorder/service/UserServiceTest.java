package com.taifex.smartorder.service;

import com.taifex.smartorder.dto.UserDTO;
import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository; // 模擬資料庫

    @InjectMocks
    private UserService userService; // 測試的目標

    @Test
    void saveUser_shouldReturnSavedUser(){
        UserDTO dto = new UserDTO();
        dto.setName("Alice");
        dto.setEmail("alice@gmail.com");
        dto.setAge(25);

        User fakeUser = User.builder()
                .id(1L)
                .name("Alice")
                .email("alice@gmail.com")
                .age(25)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(fakeUser);

        User result = userService.saveUser(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Alice");
    }

    @Test
    void getUserById_shouldReturnUserIfExists() {
        User fakeUser = User.builder()
                .id(1L)
                .name("Bob")
                .email("bob@example.com")
                .age(30)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(fakeUser));

        Optional<User> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Bob");
    }

    @Test
    void getUserById_shouldReturnEmptyIfNotExists() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(99L);

        assertThat(result).isEmpty();
    }
}
