package com.user.data.management.dto.response.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import message.PostedBy;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserProfile extends UserDto {

    Set<PostedBy> following;

    Set<PostedBy> followers;


}
