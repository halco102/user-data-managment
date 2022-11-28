package com.user.data.management.mapper;

import com.user.data.management.dto.request.UserSignupRequest;
import message.PostedBy;
import com.user.data.management.dto.response.UserDto;
import com.user.data.management.dto.response.UserLoginResponse;
import com.user.data.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User signupToEntity(UserSignupRequest signupRequest);

    UserLoginResponse userLoginResponse(String jwt);

    UserDto entityToUserDto(User user);

    PostedBy fromEntityToPostedByDto(User user);
}
