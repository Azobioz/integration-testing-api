package com.azobioz.api.controller;

import com.azobioz.api.dto.UpdateUserResponse;
import com.azobioz.api.dto.UserResponse;
import com.azobioz.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserResponse request) {
       return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User has been deleted");
    }
}
