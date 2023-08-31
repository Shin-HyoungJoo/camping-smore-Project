package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//spring security 5.7.0부터 WebSecurityConfigurerAdapter deprecated 됨
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    //webSecurityCustomizer를 제외한 모든 것, 시큐리티를 거친다. 보안과 연관
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authz ->
                                authz.requestMatchers(
                                                "/favicon.ico", "/js/**", "/img/**", "/css/**", "/static/**", "/", "/index.html",
                                                "/swagger.html"
                                                , "/swagger-ui/**"
                                                , "/v3/api-docs/**"

                                                ,"/api/oauth/authorize" //로그인
                                                , "/api/oauth/logout" // 로그아웃
                                                , "/api/user" // 회원가입
                                                , "/api/search/id" // 아이디 찾기
                                                , "/api/search/pw"  // 비밀번호 찾기
                                                ,"/api/oauth/token" // 리프레쉬 토큰...
                                                ,"/api/dataset/naver" // 네이버 검색 api
                                                ,"/api/dataset/kakao" // 카카오 맵
                                                ,"/api/community/title" // 게시판
                                                ,"/api/community/icategory" // 게시판
                                                ,"/api/community/iboard" // 게시판
                                                ,"/api/community/comunity" // 게시판
                                                ,"/api/community/boardDetail/**" // 게시판
                                                ,"/api/item/bestitem" // 아이템
                                                ,"/api/item/search" // 아이템
                                                ,"/api/item/category" // 아이템
                                                ,"/api/item/detail/**" // 아이템
                                                ,"/api/review/**/detail" // 리뷰 리스트
                                                ,"/api/review/**/**" // 리뷰 리스트
                                                , "/api/exception"
                                        ).permitAll()
//                            .requestMatchers(HttpMethod.GET, "").permitAll()
                                        .requestMatchers("**exception**").permitAll()
                                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                        .anyRequest().hasAnyRole("USER", "ADMIN")
                ) //사용 권한 체크
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용 X
                .httpBasic(http -> http.disable()) //UI 있는 시큐리티 설정을 비활성화
                .csrf(csrf -> csrf.disable()) //CSRF 보안이 필요 X, 쿠키와 세션을 이용해서 인증을 하고 있기 때문에 발생하는 일, https://kchanguk.tistory.com/197
                .exceptionHandling(except -> {
                    except.accessDeniedHandler(new CustomAccessDeniedHandler());
                    except.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class)
                .anonymous(anony -> anony.disable()); // 익명 사용자 disable();

        return httpSecurity.build();
    }
}