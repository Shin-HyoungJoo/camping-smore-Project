package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.redis.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider PROVIDER;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 추출한 토큰을 가져온다.
        String token = PROVIDER.resolveToken(req, PROVIDER.TOKEN_TYPE); 
        log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 시작");
        // 토큰 유효한지 체크
        if(token != null && PROVIDER.isValidateToken(token, PROVIDER.ACCESS_KEY)) { 
            // 로그인시 Redis에 토큰을 저장함
            // Redis에 AccessToken이 저장되어있다는 것은 블랙리스트에 올려져 있다는 것이고 , 사용하면 안됨.
            // 해당 access token이 redis에 있다면 만료된 토큰인것이죠.
            // 로그아웃 시 Access Token blacklist에 등록하여 만료시키기
            //로그아웃을 실제 진행을 하게 되는데 Redis에 저장된 RefreshToken을 삭제하고
            //Blacklist에 Access Token을 등록하게 됩니다.
            //Blacklist 존재하는지 확인 (로그아웃 된 토큰인지)
            // 그래서 isLogout처럼 null이 나와야지 블랙리스트에 없으니까 쓸 수 있다는 거임
            String isLogout = redisService.getValues(token);
            // isLogout이 null이면 액세스 토큰이 블랙리스트에도 없고 만료된 것도 아닌것임
            if(ObjectUtils.isEmpty(isLogout)) { //로그아웃이 없으면 
                Authentication auth = PROVIDER.getAuthentication(token); // 얘가 로그인을 하고 토큰 인증 완료되면 값이 날라옴
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("JwtAuthenticationFilter - doFilterInternal: 토큰 유효성 체크 완료");
            }
        }
        filterChain.doFilter(req, res);

    }
}