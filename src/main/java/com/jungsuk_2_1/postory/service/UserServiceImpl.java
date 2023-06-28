package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.HeaderChannelDto;
import com.jungsuk_2_1.postory.dto.HeaderUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    //생성자로 객체 주입받는 방법(Autowired 생략 가능)
    UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(final UserDto userDto) {

        try {
            //추가정보까지 더한 요청받은 유저 객체 정보의 EmaiId 유효성 검사
            if (userDto == null || userDto.getEid() == null) {
                throw new RuntimeException("Invalid arguments");
            }
            //유저 객체 정보의 EmailId를 꺼내서 문자열로 저장
            final String userEmail = userDto.getEid();
            final String userNic = userDto.getNic();

            //DB에 중복되는 ID가 없으면 DB에 저장하는 Dao의 메서드를 호출 -> mapper에서 SQL쿼리 실행
            userDao.save(userDto);

            //유저 상태코드를 신규(ST00110)로 DB에 저장
            userDao.statusSave(userDto.getUserId());

            //유저 이메일 인증 정보에 Default 값 저장
            userDao.emailAuthSave(userDto);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException) e.getCause();
            String errorMessage = sqlException.getMessage();

            String pattern = ".*'(.*)'";

            String realErrorReason = extractValue(errorMessage, pattern);
            if (Objects.equals(realErrorReason, "user.EID_UNIQUE")) {
                throw new RuntimeException("이미 존재하는 이메일입니다");
            }
            if (Objects.equals(realErrorReason, "user.NIC_UNIQUE")) {
                throw new RuntimeException("이미 존재하는 닉네임입니다");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    private static String extractValue(String errorMessage, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    @Override
    public UserDto getByCredentials(final String userEmail, final String password, final PasswordEncoder encoder) {
        final UserDto originalUser = userDao.findByUserEmail(userEmail);

        //matches 메서드를 이용해 패스워드가 같은지 확인
        if (originalUser != null && encoder.matches(password, originalUser.getPwd())) {
            return originalUser;
        }
        return null;
    }

    @Override
    public String checkUserStatus(UserDto userDto) {
        return userDao.findStatusByUserId(userDto.getUserId());
    }

    @Override
    public List<HeaderChannelDto> getHeaderInfo(String userId) {
        return userDao.findHeaderInfoByUserId(userId);
    }

    @Override
    public HeaderUserDto getHeaderUserInfo(String userId) {
        return userDao.findHeaderUserInfoByUserId(userId);
    }
}
