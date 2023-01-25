package com.user.data.management.service.user;

import message.PostedBy;

import java.util.Set;

public interface IUserFollow {

    Set<PostedBy> getAllUserFollowersByUserId(Long id);

    Set<PostedBy> getAllUserFollowingByUserId(Long id);

    Set<PostedBy> addFollower(Long userId, String token);

}
