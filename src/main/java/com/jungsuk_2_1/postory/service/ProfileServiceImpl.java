package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.ProfileDao;
import com.jungsuk_2_1.postory.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {
    private ProfileDao profileDao;

    ProfileServiceImpl(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public UserDto getUserByNickname(String nic) {
        return profileDao.findUserByNickname(nic);
    }

    @Override
    public List<ProfileChannelDto> getProfileChannel(Map<String, String> isSubsedMap) {
        return profileDao.findProfileChannelByNic(isSubsedMap);
    }

    @Override
    public ProfileUserDto getProfileUser(String nic) {
        ProfileUserDto profileUser = profileDao.findProfileUserByNic(nic);
        UserDto user = profileDao.findUserByNickname(profileUser.getNic());

        profileUser.setHasChannel(profileDao.existsChannelByUserId(user.getUserId()));

        return profileUser;
    }

    @Override
    public List<ProfilePostsDto> getProfilePosts(Map<String, Object> postInfoMap) {
        return profileDao.findProfilePostsByUserId(postInfoMap);
    }

    @Override
    public List<ProfileSeriseDto> getProfileSerise(Map<String, Object> seriesInfoMap) {
        return profileDao.findProfileSeriesByUserId(seriesInfoMap);
    }
}
