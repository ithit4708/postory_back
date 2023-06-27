package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.ProfileChannelDto;
import com.jungsuk_2_1.postory.dto.ProfilePostsDto;
import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;

import java.util.List;

public interface ProfileService {
    UserDto getUserByNickname(String nic);

    List<ProfileChannelDto> getProfileChannel(String userId);

    ProfileUserDto getProfileUser(String nic);

    List<ProfilePostsDto> getProfilePosts(String userId);
}
