package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.*;

import java.util.List;
import java.util.Map;

public interface ProfileService {
    UserDto getUserByNickname(String nic);

    List<ProfileChannelDto> getProfileChannel(Map<String,String> isSubsedMap);

    ProfileUserDto getProfileUser(String nic);

    List<ProfilePostsDto> getProfilePosts(Map<String,Object> postInfoMap);
    List<ProfileSeriseDto> getProfileSerise(Map<String,Object> seriesInfoMap);
}
