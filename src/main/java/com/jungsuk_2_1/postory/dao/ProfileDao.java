package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileDao {
    UserDto findUserByNickname(String nic);

    List<ProfileChannelDto> findProfileChannelByNic(String userId);

    ProfileUserDto findProfileUserByNic(String nic);

    List<ProfilePostsDto> findProfilePostsByUserId(String userId);
    List<ProfileSeriseDto> findProfileSeriesByUserId(String userId);
}
