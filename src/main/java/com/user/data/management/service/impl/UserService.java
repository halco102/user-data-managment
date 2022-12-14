package com.user.data.management.service.impl;

import com.user.data.management.dto.request.EditUserRequest;
import com.user.data.management.dto.request.UserLoginRequest;
import com.user.data.management.dto.request.UserSignupRequest;
import message.PostedBy;
import com.user.data.management.dto.response.UserDto;
import com.user.data.management.dto.response.UserLoginResponse;
import com.user.data.management.event.constants.MessageTopic;
import com.user.data.management.event.service.NotificationContext;
import com.user.data.management.exception.BadRequestException;
import com.user.data.management.exception.DuplicateException;
import com.user.data.management.exception.NotFoundException;
import com.user.data.management.exception.Unauthorized;
import com.user.data.management.mapper.UserMapper;
import com.user.data.management.model.User;
import com.user.data.management.model.UserRole;
import com.user.data.management.repository.UserRepository;
import com.user.data.management.security.JwtTokenUtil;
import com.user.data.management.service.IUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUser {

    private final UserRepository userRepository;

    private static final String RANDOM_AVATAR_URL="https://avatars.dicebear.com/api/bottts/";

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final NotificationContext notificationContext;

    @Override
    @Transactional
    public User createUser(UserSignupRequest signupRequest) {

        var user = userMapper.signupToEntity(signupRequest);
        user.setCreatedAt(LocalDate.now());
        user.setImageUrl(RANDOM_AVATAR_URL + UUID.randomUUID() + ".svg");
        user.setUserRole(UserRole.ROLE_USER);
        user.setVerificationCode(String.valueOf(UUID.randomUUID()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userRepository.getUserByUsername(signupRequest.getUsername()).isPresent()) {
            throw new DuplicateException("Username is already taken");
        }else if (userRepository.getUserByEmail(signupRequest.getEmail()).isPresent()) {
            throw new DuplicateException("Email is already taken");
        }

        return userRepository.save(user);
    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest request) {

        Optional<User> getUser = null;

        if (checkIfEmailOrUsername(request.getEmailOrUsername()))
            getUser = this.userRepository.getUserByEmail(request.getEmailOrUsername().trim());
        else
            getUser = this.userRepository.getUserByUsername(request.getEmailOrUsername().trim());

        if (getUser.isEmpty())
            throw new NotFoundException("The user was not found");

        if (passwordEncoder.matches(request.getPassword(), getUser.get().getPassword())) {

            if (!getUser.get().isVerified())
                throw new BadRequestException("Verify email to login");

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(getUser.get().getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);


            return userMapper.userLoginResponse(jwtTokenUtil.generateJwtToken(auth));
        }else
            throw new Unauthorized("Wrong email/username or password");
    }

    public boolean checkIfEmailOrUsername(String emailOrUsername) {
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher matcher = pattern.matcher(emailOrUsername);

        return matcher.matches();
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        var getEntityById = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User was not found"));
        return userMapper.entityToUserDto(getEntityById);
    }

    @Override
    public void deleteUserById(Long id) {
        var findUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("The user was not found!"));
        userRepository.deleteById(id);
        log.info("Delete user by ID " + id);

        notificationContext.sendMessage(MessageTopic.USER_DELETE_EVENT, id, MessageTopic.USER_DELETE_EVENT.name());
    }

    @Override
    public UserDto updateUserById(Long id) {

        return null;
    }

    @Override
    public PostedBy getPostedByDtoById(Long id) {

        if (id == null) {
            throw new BadRequestException("Id is null");
        }

        var fetchUserById = userRepository.findById(id).orElseThrow(() -> new NotFoundException("The user was not found"));
        return userMapper.fromEntityToPostedByDto(fetchUserById);
    }

    @Override
    @Transactional
    public PostedBy editUser(EditUserRequest request) {

        var fetchUserById = userRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("The user was not found"));

        fetchUserById.setUsername(request.getUsername() != null ? request.getUsername() : fetchUserById.getUsername());
        fetchUserById.setEmail(request.getEmail() != null ? request.getEmail() : fetchUserById.getEmail());
        fetchUserById.setImageUrl(request.getImageUrl() != null ? request.getImageUrl() : fetchUserById.getImageUrl());

        var saveUser = userRepository.save(fetchUserById);

        var toDto = userMapper.fromEntityToPostedByDto(saveUser);

        notificationContext.sendMessage(MessageTopic.USER_UPDATE_EVENT, toDto, MessageTopic.USER_UPDATE_EVENT.name());

        return toDto;
    }
}
