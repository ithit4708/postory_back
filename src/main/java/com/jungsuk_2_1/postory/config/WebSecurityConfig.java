package com.jungsuk_2_1.postory.config;

import com.jungsuk_2_1.postory.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@Slf4j
//DispatcherServlet이 실행되기 전에 가장 먼저 실행 됨
//Spring Security의 초기 설정을 구성하기 위함
public class WebSecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // "/" 와 " /auth/** " 경로는 인증 안 해도 됨.
                .antMatchers("/", "/auth/**").permitAll()
                .anyRequest() // "/" 와 " /auth/** " 경로 이외에는 인증 해야 됨.
                .authenticated();

        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다.
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
        return http.build();
    }
}
//WebSecurityConfigurerAdapter - Deprecated
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //http 시큐리티 필터
//        http.cors()//WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
//                .and()
//                .csrf() //csrf는 현재 사용하지 않으므로 disable
//                .disable()
//                .httpBasic() //token을 사용하므로 basic 인증 disable
//                .disable()
//                .sessionManagement() //session 기반이 아님을 선언
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests() // "/" 와 " /auth/** " 경로는 인증 안 해도 됨.
//                .antMatchers("/", "/auth/**").permitAll()
//                .anyRequest() // "/" 와 " /auth/** " 경로 이외에는 인증 해야 됨.
//                .authenticated();
//
//        // filter 등록
//        // 매 요청마다
//        // CorsFilter 실행한 후에
//        // jwtAuthenticationFilter 실행한다.
//        http.addFilterAfter(
//                jwtAuthenticationFilter,
//                CorsFilter.class
//        );
//    }
//}
