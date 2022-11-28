package com.user.data.management.service;

import com.user.data.management.dto.request.UserLoginRequest;
import com.user.data.management.dto.request.UserSignupRequest;
import com.user.data.management.dto.response.UserDto;
import com.user.data.management.dto.response.UserLoginResponse;
import com.user.data.management.exception.BadRequestException;
import com.user.data.management.exception.DuplicateException;
import com.user.data.management.exception.NotFoundException;
import com.user.data.management.exception.Unauthorized;
import com.user.data.management.mapper.UserMapper;
import com.user.data.management.model.User;
import com.user.data.management.model.UserRole;
import com.user.data.management.repository.UserRepository;
import com.user.data.management.security.JwtTokenUtil;
import com.user.data.management.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    UserService userService;

    private UserSignupRequest userSignupRequest;

    private User user;



    @BeforeEach
    public void beforeEachSetup() {
        userSignupRequest = new UserSignupRequest("halco", "password123", "email@email.com");
        user = new User(1L, "halco", "encryptedPassword",
                "email@email.com", "image1",
                "verificationCode", true,
                LocalDate.now(), UserRole.ROLE_USER,
                new HashSet<>(), new HashSet<>());


        Mockito.when(userMapper.signupToEntity(Mockito.any(UserSignupRequest.class))).
                thenReturn(new User(userSignupRequest.getUsername(), userSignupRequest.getPassword(), userSignupRequest.getEmail()));
    }

    @Test
    void createUser() {

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn("encryptedPassword");

        var toTest = userService.createUser(userSignupRequest);

        Assertions.assertEquals(toTest.getId(), user.getId());
        Assertions.assertEquals(toTest.getUsername(), userSignupRequest.getUsername());
        Assertions.assertEquals(toTest.getEmail(), userSignupRequest.getEmail());
        Assertions.assertEquals(toTest.getUserRole().name(), user.getUserRole().name());
        Assertions.assertEquals(toTest.getPassword(), user.getPassword());
        Assertions.assertEquals(toTest.isVerified(), true);
    }

    @Test
    public void onCreateMethodThrowDuplicateExceptionIfEmailIsTaken() {
        Mockito.when(userRepository.getUserByEmail(Mockito.any(String.class))).thenReturn(Optional.of(new User(userSignupRequest.getUsername(), userSignupRequest.getEmail(), userSignupRequest.getPassword())));
        Assertions.assertThrows(DuplicateException.class, () -> userService.createUser(userSignupRequest));
    }

    @Test
    public void onCreateMethodThrowDuplicateExceptionIfUsernameIsTaken() {
        Mockito.when(userRepository.getUserByUsername(Mockito.any(String.class))).
                thenReturn(Optional.of(new User(userSignupRequest.getUsername(), userSignupRequest.getEmail(), userSignupRequest.getPassword())));
        Assertions.assertThrows(DuplicateException.class, () -> userService.createUser(userSignupRequest));
    }

    @Test
    public void userLoginTestWhenLoginByEmail() {
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(userRepository.getUserByEmail(Mockito.any(String.class))).thenReturn(Optional.of(user));
        Mockito.when(jwtTokenUtil.generateJwtToken(Mockito.any())).thenReturn("JWT");
        Mockito.when(userMapper.userLoginResponse(Mockito.any(String.class))).thenReturn(new UserLoginResponse("JWT"));
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(new UsernamePasswordAuthenticationToken(user.getUsername(), "password123"));

        var testValue = userService.userLogin(new UserLoginRequest("email@email.com", "password123"));

        Assertions.assertEquals(testValue.getJwt(), "JWT");

    }

    @Test
    public void userLoginTestWhenLoginByUsername() {
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(userRepository.getUserByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));
        Mockito.when(jwtTokenUtil.generateJwtToken(Mockito.any())).thenReturn("JWT");
        Mockito.when(userMapper.userLoginResponse(Mockito.any(String.class))).thenReturn(new UserLoginResponse("JWT"));
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(new UsernamePasswordAuthenticationToken(user.getUsername(), "password123"));

        var testValue = userService.userLogin(new UserLoginRequest("halco", "password123"));

        Assertions.assertEquals(testValue.getJwt(), "JWT");
    }

    @Test
    public void userLoginThrowNotFoundExceptionWhenUserWasNotFound() {
        Mockito.when(userRepository.getUserByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.getUserByUsername(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.userLogin(new UserLoginRequest("email2@email.com", "password123")));
        Assertions.assertThrows(NotFoundException.class, () -> userService.userLogin(new UserLoginRequest("temp123", "password123")));
    }

    @Test
    public void userLoginPasswordDoesNotMatchError() {
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(userRepository.getUserByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));

        Assertions.assertThrows(Unauthorized.class, () -> userService.userLogin(new UserLoginRequest("halco", "password123")));
    }

    @Test
    public void userLoginThrowBadRequestIfUserDidNotVerifyAccount() {
        user.setVerified(false);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        Mockito.when(userRepository.getUserByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));

        Assertions.assertThrows(BadRequestException.class, () -> userService.userLogin(new UserLoginRequest("halco", "password123")));
    }

    @Test
    public void getUserDtoById() {

        UserDto toReturn = new UserDto(1L, "halco", "email@email.com", "image1", LocalDate.now());

        Mockito.when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));
        Mockito.when(userMapper.entityToUserDto(Mockito.any(User.class))).thenReturn(toReturn);

        var toTest = userService.getUserDtoById(1L);

        Assertions.assertEquals(toTest.getId(), user.getId());
        Assertions.assertEquals(toTest.getUsername(), user.getUsername());
        Assertions.assertEquals(toTest.getEmail(), user.getEmail());
        Assertions.assertEquals(toTest.getImageUrl(), user.getImageUrl());
    }
}