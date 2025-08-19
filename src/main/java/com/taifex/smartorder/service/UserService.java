package com.taifex.smartorder.service;

import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 儲存使用者
    public User saveUser(User user) {
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
    public User updateUser(User user) {
        return userRepository.save(user);
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
