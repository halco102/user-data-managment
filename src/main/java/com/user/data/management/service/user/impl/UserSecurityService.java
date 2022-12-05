package com.user.data.management.service.user.impl;

import com.user.data.management.dto.response.user.UserSecurityDto;
import com.user.data.management.exception.NotFoundException;
import com.user.data.management.mapper.UserMapper;
import com.user.data.management.repository.UserRepository;
import com.user.data.management.service.user.IUserSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSecurityService implements IUserSecurity {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
/*
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean isJwtValid(String jwt) {
        return false;
    }

    @Override
    public PostedBy getCurrentlyLoggedUser() {
        User user = null;
        String username = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            throw new Unauthorized("Not authorized");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetailsImp) {
            username = ((CustomUserDetailsImp) principal).getUsername();
            var fetchUser = this.userRepository.getUserByUsername(username).orElseThrow(() -> new NotFoundException("User not found!"));
            return userMapper.fromEntityToPostedByDto(fetchUser);
        }else {
            throw new ClassCastException("Class cast exception");
        }
    }

    @Override
    public PostedBy getUserByJwt(String token) {

        Optional<User> user = null;

        if (!jwtTokenUtil.validateToken(token)) {
            throw new IllegalArgumentException("Error");
        }

        var extractUsernameFromToken = jwtTokenUtil.getUsernameByJwt(token);
        user = userRepository.getUserByUsername(extractUsernameFromToken);

        if (user.isEmpty())
            throw new NotFoundException("The user was not found");

        return userMapper.fromEntityToPostedByDto(user.get());
    }*/


    @Override
    public UserSecurityDto getUserByUsername(String username) {
        var findUser = userRepository.getUserByUsername(username).orElseThrow(() -> new NotFoundException("User was not found"));
        return userMapper.fromEntityToSecurityDto(findUser);
    }

}
