package com.taifex.smartorder.service;

import com.taifex.smartorder.dto.UserDTO;
import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 儲存使用者
    public User saveUser(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .age(userDTO.getAge())
                .build();
        return userRepository.save(user);
    }

    // 查找所有使用者
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 根據 ID 查找使用者
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 根據 email 查找使用者
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 根據名字查找使用者
    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    // 更新使用者
    public User updateUser(Long id, UserDTO userDTO) {

        User updateUser = userRepository.findById(id).get();
        updateUser.setName(userDTO.getName());
        updateUser.setEmail(userDTO.getEmail());
        updateUser.setAge(userDTO.getAge());
        return userRepository.save(updateUser);
    }

    // 刪除使用者
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 檢查 email 是否已存在
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
