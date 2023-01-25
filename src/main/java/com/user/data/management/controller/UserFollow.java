package com.user.data.management.controller;

import com.user.data.management.security.JwtTokenUtil;
import com.user.data.management.service.user.IUserFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user/like-dislike")
@RequiredArgsConstructor
public class UserFollow {

    private final IUserFollow iUserFollow;

    @GetMapping("/{id}/followers")
    public ResponseEntity<?> getUserFollowersByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(iUserFollow.getAllUserFollowersByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<?> getUserFollowingByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(iUserFollow.getAllUserFollowingByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/follow-user/{id}")
    public ResponseEntity<?> followUnfollowUser(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(iUserFollow.addFollower(id, JwtTokenUtil.parseJwt(request)), HttpStatus.OK);
    }

}
