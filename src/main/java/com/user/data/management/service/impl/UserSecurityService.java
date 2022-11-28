package com.user.data.management.service.impl;

import com.user.data.management.dto.response.UserDto;
import com.user.data.management.exception.NotFoundException;
import com.user.data.management.exception.Unauthorized;
import com.user.data.management.mapper.UserMapper;
import com.user.data.management.model.User;
import com.user.data.management.repository.UserRepository;
import com.user.data.management.security.CustomUserDetailsImp;
import com.user.data.management.service.IUserSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSecurityService implements IUserSecurity {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public boolean isJwtValid(String jwt) {
        return false;
    }

    @Override
    public UserDto getCurrentlyLoggedUser() {
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
            return userMapper.entityToUserDto(fetchUser);
        }else {
            throw new ClassCastException("Class cast exception");
        }
    }

}
