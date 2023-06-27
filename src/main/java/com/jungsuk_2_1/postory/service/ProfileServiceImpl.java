package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.ProfileDao;
import com.jungsuk_2_1.postory.dto.ProfileChannelDto;
import com.jungsuk_2_1.postory.dto.ProfilePostsDto;
import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ProfileChannelDto> getProfileChannel(String userId) {
        return profileDao.findProfileChannelByNic(userId);
    }

    @Override
    public ProfileUserDto getProfileUser(String nic) {
        return profileDao.findProfileUserByNic(nic);
    }

    @Override
    public List<ProfilePostsDto> getProfilePosts(String userId) {
        return profileDao.findProfilePostsByUserId(userId);
    }
}
