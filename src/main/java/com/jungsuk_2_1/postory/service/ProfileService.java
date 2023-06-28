package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.*;

import java.util.List;

public interface ProfileService {
    UserDto getUserByNickname(String nic);

    List<ProfileChannelDto> getProfileChannel(String userId);

    ProfileUserDto getProfileUser(String nic);

    List<ProfilePostsDto> getProfilePosts(String userId);
    List<ProfileSeriseDto> getProfileSerise(String userId);
}
