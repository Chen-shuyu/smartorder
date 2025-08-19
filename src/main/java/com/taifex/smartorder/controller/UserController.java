package com.taifex.smartorder.controller;


import com.taifex.smartorder.entity.User;
import com.taifex.smartorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /api/users - 取得所有使用者
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    // GET /api/users/{id} - 根據 ID 取得使用者
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST /api/users - 建立新使用者
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        if (userService.isEmailExists(user.getEmail())){
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // PUT /api/users/{id} - 更新使用者
    @PutMapping
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            User existingUser = user.get();
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setAge(userDetails.getAge());
            User updatesUser = userService.saveUser(existingUser);
            return ResponseEntity.ok(updatesUser);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/users/{id} - 刪除使用者
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if (userService.getUserById(id).isPresent()){
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
