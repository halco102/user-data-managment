package com.user.data.management.service.user;

import com.user.data.management.dto.request.user.EditUserRequest;
import com.user.data.management.dto.request.user.UserLoginRequest;
import com.user.data.management.dto.request.user.UserSignupRequest;
import com.user.data.management.dto.response.user.UserDto;
import com.user.data.management.dto.response.user.UserSecurityDto;
import message.PostedBy;

public interface IUser {

    void createUser(UserSignupRequest request);

    UserSecurityDto userLogin(UserLoginRequest request);

    UserDto getUserDtoById(Long id);

    void deleteUserById(Long id);

    UserDto updateUserById(Long id);

    PostedBy getPostedByDtoById(Long id);

    PostedBy editUser(EditUserRequest request);

    void addRoleToUser(String roleName, Long userId);
}
