package com.jungsuk_2_1.postory.security;

import com.jungsuk_2_1.postory.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9cAOIAifu3fykvhkHpbuhbvtH807Z2rI1FS3vX1XMjE";

    public String create(UserDto userDto) {
        //기한 지금으로부터 1일로 설정
        Date expiryData = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(userDto.getUserId()) // sub
                .setIssuer("user")
                .setIssuedAt(new Date())
                .setExpiration(expiryData)
                .compact();
    }

    public String create(final Authentication authentication){
        ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User) authentication.getPrincipal();

        Date expiryData = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(userPrincipal.getName()) // sub
                .setIssuer("user")
                .setIssuedAt(new Date())
                .setExpiration(expiryData)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        //parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱
        //즉, 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교
        //위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        //그 중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return  claims.getSubject();
    }
}
