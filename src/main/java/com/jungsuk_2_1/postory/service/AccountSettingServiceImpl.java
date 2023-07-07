package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.AccountSettingDao;
import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class AccountSettingServiceImpl implements AccountSettingService {
    AccountSettingDao accountSettingDao;
    private static final String UPLOAD_DIRECTORY = "static/img/user";

    public AccountSettingServiceImpl(AccountSettingDao accountSettingDao) {
        this.accountSettingDao = accountSettingDao;
    }

    @Override
    public void changeUserProfile(UserDto userInfo) throws Exception {

        try {
            if (userInfo == null || userInfo.getNic() == null) {
                throw new RuntimeException("Invalid arguments");
            }
            //유저 정보를 update
            accountSettingDao.updateUserProfile(userInfo);

        } catch (DuplicateKeyException e) {
            //nic은 uniqe 이므로 중복되면 DuplicateKeyException 예외를 발생시킴.
            SQLException sqlException = (SQLException) e.getCause();
            String errorMessage = sqlException.getMessage();

            String pattern = ".*'(.*)'";

            String shortErrorReason = extractValue(errorMessage, pattern);
            if (Objects.equals(shortErrorReason, "user.NIC_UNIQUE")) {
                throw new Exception("이미 존재하는 닉네임입니다");
            }
        } catch (DataIntegrityViolationException e) {
            //nic이 40자 이상이거나 자기소개가 255자 이상이면 발생하는 예외
            SQLException sqlException = (SQLException) e.getCause();
            String errorMessage = sqlException.getMessage();
            log.warn("errorMessage = {}", errorMessage);

            String pattern = ".*'(.*)'";
            String shortErrorReason = extractValue(errorMessage, pattern);
            if (Objects.equals(shortErrorReason, "NIC")) {
                throw new Exception("닉네임은 40자 이하로 작성해주세요");
            }
            if (Objects.equals(shortErrorReason, "USER_INTRO")) {
                throw new Exception("자기소개는 255자 이하로 작성해주세요");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProfileUserDto findUserByUserId(String userId) {
        return accountSettingDao.selectUserByUserId(userId);
    }

    @Override
    public String saveImage(MultipartFile userImgFile, String userId) throws Exception {

        try (InputStream inputStream = userImgFile.getInputStream()) {
            // 저장할 디렉토리 경로
            Path uploadPath = Path.of(UPLOAD_DIRECTORY);
            log.warn("uploadPath = {}", uploadPath);
            // 디렉토리가 존재하지 않을 경우 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // 원본 파일명과 확장자 분리
            String originalFilename = userImgFile.getOriginalFilename();
            String fileExtension = StringUtils.getFilenameExtension(originalFilename);

            // 저장할 파일명 생성 (예: UUID 또는 고유한 파일명)
            String filename = userId + "." + fileExtension;
            log.warn("filename = {}", filename);
            // 저장할 파일 경로
            Path filePath = uploadPath.resolve(filename);
            log.warn("filePath = {}", filePath);
            // 파일 저장
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            log.warn("save File name = {}", filename);
            // 저장된 파일명 반환
            return filename;
        } catch (IOException e) {
            // 파일 저장 중에 예외 발생
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    @Override
    public void changePwd(Map<String,String> userPwdMap) {
        accountSettingDao.updateUserPwd(userPwdMap);
    }

    private static String extractValue(String errorMessage, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
