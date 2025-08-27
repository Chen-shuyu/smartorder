package com.taifex.smartorder.service;

import com.taifex.smartorder.dto.UserDTO;
import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        userDTO.setId(user.getId());
        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .age(userDTO.getAge())
                .build();
    }

    // 儲存使用者
    public UserDTO saveUser(UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    // 查找所有使用者
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // 根據 ID 查找使用者
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    // 根據 email 查找使用者
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapToDTO);
    }

    // 根據名字查找使用者
    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    // 更新使用者
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(existing -> {
            existing.setName(userDTO.getName());
            existing.setAge(userDTO.getAge());
            existing.setEmail(userDTO.getEmail());
           return mapToDTO(userRepository.save(existing));
        });
    }

    // 刪除使用者
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 檢查 email 是否已存在
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public void demoNPlusOne() {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            // ⚠️ 這行會觸發 N+1 問題
            System.out.println(u.getName() + " has " + u.getOrders().size() + " orders");
        }
    }

    public void demoNPlusOneSolved() {
        List<User> users = userRepository.findAllWithOrders();
        for (User u : users) {
            System.out.println(u.getName() + " has " + u.getOrders().size() + " orders");
        }
    }

    public void demoEntityGraph() {
        List<User> users = userRepository.findAllBy();
        for (User u : users) {
            System.out.println(u.getName() + " has " + u.getOrders().size() + " orders");
        }
    }

}
