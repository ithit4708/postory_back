package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AccountSettingDao {
    void updateUserProfile(UserDto userInfo);

    ProfileUserDto selectUserByUserId(String userId);
    void updateUserPwd(Map<String,String> userPwdMap);
}
