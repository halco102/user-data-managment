package com.user.data.management.mapper;

import com.user.data.management.dto.request.user.UserSignupRequest;
import com.user.data.management.dto.response.user.UserProfile;
import com.user.data.management.dto.response.user.UserSecurityDto;
import message.PostedBy;
import com.user.data.management.dto.response.user.UserDto;
import com.user.data.management.dto.response.user.UserLoginResponse;
import com.user.data.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User signupToEntity(UserSignupRequest signupRequest);

    UserLoginResponse userLoginResponse(String jwt);

    UserDto entityToUserDto(User user);

    PostedBy fromEntityToPostedByDto(User user);

    @Mapping(source = "roles", target = "userRoles")
    UserSecurityDto fromEntityToSecurityDto(User user);



    UserProfile fromEntityToUserProfile(User user);
}
