package com.user.data.management.controller;

import com.user.data.management.dto.request.user.EditUserRequest;
import com.user.data.management.dto.request.user.UserLoginRequest;
import com.user.data.management.dto.request.user.UserSignupRequest;
import com.user.data.management.service.user.IUser;
import com.user.data.management.service.user.IUserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUser iUser;

    private final IUserSecurity iUserSecurity;


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserSignupRequest request) {
        iUser.createUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
        return new ResponseEntity<>(iUser.userLogin(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserDtoById(@PathVariable Long id) {
        return new ResponseEntity<>(iUser.getUserDtoById(id), HttpStatus.OK);
    }

    @GetMapping("/postedBy/{id}")
    public ResponseEntity<?> getPostedByDtoByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(iUser.getPostedByDtoById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody EditUserRequest request) {
        return new ResponseEntity<>(iUser.editUser(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        iUser.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/security/find-by-username")
    public ResponseEntity<?> findUserByUsername(@RequestParam String username) {
        return new ResponseEntity<>(iUserSecurity.getUserByUsername(username), HttpStatus.OK);
    }


    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfileById(@PathVariable Long id) {
        return new ResponseEntity<>(iUser.getUserProfileById(id), HttpStatus.OK);
    }
}
