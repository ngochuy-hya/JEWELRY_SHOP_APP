package com.example.API.Controller;

import com.example.API.DTO.Reponse.UserResponse;
import com.example.API.DTO.Request.ChangePasswordRequest;
import com.example.API.DTO.Request.UserUpdateRequest;
import com.example.API.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Integer userId) {
        return userService.getUserById(userId);
    }
    @PutMapping("/{id}")
    public String updateUserInfo(@PathVariable Integer id, @RequestBody UserUpdateRequest request) {
        userService.updateUserInfo(id, request);
        return "User info updated";
    }

    @PutMapping("/{id}/change-password")
    public String changePassword(@PathVariable Integer id, @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return "Password changed successfully";
    }
}