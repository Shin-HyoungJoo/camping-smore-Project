package com.green.campingsmore.config.security;

import com.green.campingsmore.config.properties.CorsProperties;
import com.green.campingsmore.config.security.handler.OAuth2AuthenticationFailureHandler;
import com.green.campingsmore.config.security.handler.OAuth2AuthenticationSuccessHandler;
import com.green.campingsmore.config.security.handler.TokenAccessDeniedHandler;
import com.green.campingsmore.config.security.oauth.CustomOAuth2UserService;
import com.green.campingsmore.config.security.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.green.campingsmore.config.security.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


//spring security 5.7.0부터 WebSecurityConfigurerAdapter deprecated 됨
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CorsProperties corsProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    //webSecurityCustomizer를 제외한 모든 것, 시큐리티를 거친다. 보안과 연관
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authz ->
                                authz.requestMatchers(
                                                "/favicon.ico", "/js/**", "/img/**", "/css/**", "/static/**", "/", "/index.html",
                                                "/swagger.html"
                                                , "/swagger-ui/**"
                                                , "/v3/api-docs/**"
                                        ,"/**"

                                                , "/*/oauth2/code/*"
                                                , "/oauth2/**"
                                                , "/oauth/**"

                                                ,"/api/notuser" // 비회원 방문 카운트 증가
                                                ,"/api/oauth/authorize" //로그인
                                                , "/api/oauth/logout" // 로그아웃
                                                , "/api/user" // 회원가입
                                                , "/api/search/id" // 아이디 찾기
                                                , "/api/search/pw"  // 비밀번호 찾기
                                                ,"/api/oauth/token" // 리프레쉬 토큰...
                                                ,"/api/admin/oauth/authorize" // 관리자 로그인
                                                ,"/api/dataset/naver" // 네이버 검색 api
                                                ,"/api/dataset/kakao" // 카카오 맵
                                                ,"/api/community/title" // 게시판
                                                ,"/api/community/icategory" // 게시판
                                                ,"/api/community/iboard" // 게시판
                                                ,"/api/community/comunity" // 게시판
                                                ,"/api/community/boardDetail/**" // 게시판
                                                ,"/api/item/**" // 아이템
                                                ,"/api/review/**/detail" // 리뷰 리스트
                                                , "/api/exception"
                                        ).permitAll()
//                            .requestMatchers(HttpMethod.GET, "").permitAll()
                                        .requestMatchers("**exception**").permitAll()
                                        .requestMatchers("/api/admin/oauth/authorize").permitAll()
                                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                        .anyRequest().hasAnyRole("USER") // 로그인한 사람만 수락
//                                        .anyRequest().permitAll()// 모든 권한 수락
                ) //사용 권한 체크
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 사용 X
                .httpBasic(http -> http.disable()) //UI 있는 시큐리티 설정을 비활성화
                .csrf(csrf -> csrf.disable()) //CSRF 보안이 필요 X, 쿠키와 세션을 이용해서 인증을 하고 있기 때문에 발생하는 일, https://kchanguk.tistory.com/197
                .exceptionHandling(except -> {
//                    except.accessDeniedHandler(new CustomAccessDeniedHandler());
                    except.accessDeniedHandler(tokenAccessDeniedHandler);
                    except.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
                })
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization  -> authorization.baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/*/oauth2/code/*"))
                        .userInfoEndpoint(userInfo  -> userInfo.userService(oAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler())
                )
                // 검증이 정상적으로 통과되었다면 인증된 authentication 객체를 기반으로 JWT 토큰을 생성합니다  ==> UsernamePasswordAuthenticationFilter가 하는 일
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class)
                .anonymous(anony -> anony.disable()); // 익명 사용자 disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean

    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository);
    }

    /*
     * Cors 설정
     * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }

}