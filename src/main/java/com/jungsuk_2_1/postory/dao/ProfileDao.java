package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProfileDao {
    UserDto findUserByNickname(String nic);

    List<ProfileChannelDto> findProfileChannelByNic(Map<String,String> isSubsedMap);

    ProfileUserDto findProfileUserByNic(String nic);

    List<ProfilePostsDto> findProfilePostsByUserId(Map<String,Object> postInfoMap);
    List<ProfileSeriseDto> findProfileSeriesByUserId(Map<String,Object> seriesInfoMap);
    Boolean existsChannelByUserId(String userId);
}
