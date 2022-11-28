package com.user.data.management.service;

import com.user.data.management.dto.response.UserDto;
import com.user.data.management.model.User;

public interface IUserSecurity {

    boolean isJwtValid(String jwt);

    UserDto getCurrentlyLoggedUser();


}
