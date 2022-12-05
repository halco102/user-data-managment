package com.user.data.management.controller;

import com.user.data.management.dto.request.role.AddRoleRequest;
import com.user.data.management.service.role.IRole;
import com.user.data.management.service.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {

    private final IRole iRole;

    private final IUser iUser;

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest request) {
        return new ResponseEntity<>(iRole.addRole(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        return new ResponseEntity<>(iRole.getRoleById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getRoleByName(@PathVariable String name) {
        return new ResponseEntity<>(iRole.getRoleByName(name), HttpStatus.OK);
    }

    @PostMapping("/{roleName}/add/{userId}")
    public ResponseEntity<?> addRoleToUser(@PathVariable("roleName") String roleName, @PathVariable("userId") Long userId) {
        iUser.addRoleToUser(roleName, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

