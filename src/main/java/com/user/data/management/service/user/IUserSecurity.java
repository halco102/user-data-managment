package com.user.data.management.service.user;

import com.user.data.management.dto.response.user.UserSecurityDto;

public interface IUserSecurity {

/*    boolean isJwtValid(String jwt);

    PostedBy getCurrentlyLoggedUser();

    PostedBy getUserByJwt(String token);*/

    UserSecurityDto getUserByUsername(String username);
}
