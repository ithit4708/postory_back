package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private UserDao userDao;

    UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto create(final UserDto userDto) {
        //유효성 검사
        if (userDto == null || userDto.getEid() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String userEmail = userDto.getEid();
        System.out.println("userEmail" + userEmail);
        if (userDao.existsByUserEmail(userEmail)) {
            log.warn("userEmail already exists {}", userEmail);
            throw new RuntimeException("Username already exists");
        }
        userDao.save(userDto);

        return userDao.findByUserEmail(userEmail);
    }

    public UserDto getByCredentials(final String userEmail, final String password, final PasswordEncoder encoder) {
        final UserDto originalUser = userDao.findByUserEmail(userEmail);

        //matches 메서드를 이용해 패스워드가 같은지 확인
        if (originalUser != null && encoder.matches(password, originalUser.getPwd())) {
            return originalUser;
        }
        return null;
    }
}
