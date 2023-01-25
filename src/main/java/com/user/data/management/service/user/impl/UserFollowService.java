package com.user.data.management.service.user.impl;

import com.user.data.management.exception.BadRequestException;
import com.user.data.management.exception.NotFoundException;
import com.user.data.management.mapper.UserMapper;
import com.user.data.management.model.User;
import com.user.data.management.repository.UserRepository;
import com.user.data.management.security.JwtTokenUtil;
import com.user.data.management.service.user.IUserFollow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import message.PostedBy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFollowService implements IUserFollow {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Set<PostedBy> getAllUserFollowersByUserId(Long id) {
        var fetchUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User was not found"));
        return fetchUser.getFollowers().stream().map(m -> userMapper.fromEntityToPostedByDto(m)).collect(Collectors.toSet());
    }

    @Override
    public Set<PostedBy> getAllUserFollowingByUserId(Long id) {
        var fetchUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User was not found"));
        return fetchUser.getFollowing().stream().map(m -> userMapper.fromEntityToPostedByDto(m)).collect(Collectors.toSet());
    }

    /*
    * Returns Set of followers back to FE
    * so that this info is set into the state
    * */
    @Override
    @Transactional
    public Set<PostedBy> addFollower(Long userId, String token) {

        var fetchCurrentUser = userRepository.getUserByUsername(jwtTokenUtil.getUsernameByJwt(token)).orElseThrow(() -> new NotFoundException("User was not found"));

        if (fetchCurrentUser.getId() == userId)
            throw new BadRequestException("Cant follow yourself");

        if (fetchCurrentUser.getFollowing().stream().anyMatch(u -> u.getId() == userId))
             deleteIfClickedOnSameUser(fetchCurrentUser, userId);
        else
             addFollowerIfHesNotInSet(fetchCurrentUser, userId);

        return fetchCurrentUser.getFollowing().stream().map(i -> userMapper.fromEntityToPostedByDto(i)).collect(Collectors.toSet());
    }

    private PostedBy deleteIfClickedOnSameUser(User user, Long followId) {

        user.getFollowing().removeIf(u -> {
            if (u.getId() == followId)
                return true;
            else
                return false;
        });
        var saveUser = userRepository.save(user);

        return userMapper.fromEntityToPostedByDto(saveUser);
    }

    private PostedBy addFollowerIfHesNotInSet(User user, Long followId) {
        var fetchUserToFollow = userRepository.findById(followId).orElseThrow(() -> new NotFoundException("User does not exist"));
        user.getFollowing().add(fetchUserToFollow);
        userRepository.save(user);
        return userMapper.fromEntityToPostedByDto(fetchUserToFollow);
    }

}
