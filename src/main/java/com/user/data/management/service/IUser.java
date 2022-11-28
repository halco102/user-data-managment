package com.user.data.management.service;

import com.user.data.management.dto.request.EditUserRequest;
import com.user.data.management.dto.request.UserLoginRequest;
import com.user.data.management.dto.request.UserSignupRequest;
import message.PostedBy;
import com.user.data.management.dto.response.UserDto;
import com.user.data.management.dto.response.UserLoginResponse;
import com.user.data.management.model.User;

public interface IUser {

    User createUser(UserSignupRequest request);

    UserLoginResponse userLogin(UserLoginRequest request);

    UserDto getUserDtoById(Long id);

    void deleteUserById(Long id);

    UserDto updateUserById(Long id);

    PostedBy getPostedByDtoById(Long id);

    PostedBy editUser(EditUserRequest request);
}
