package com.green.campingsmore.config.security;

import com.green.campingsmore.config.security.model.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUserDetails getLoginUser() {
        // auth = null (로그인 안하면 null)
        //검증이 정상적으로 통과되었다면 인증된 authentication 객체를 기반으로 JWT 토큰을 생성합니다
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        return userDetails;
    }

    public Long getLoginUserPk() {
        return getLoginUser().getIuser();
    }

    public boolean isLogin(){
        // 검증이 정상적으로 통과되었다면 인증된 authentication 객체를 기반으로 JWT 토큰을 생성합니다
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        System.out.println("황주은 - AuthenticationFacade.getPrincipal() = "+auth.getPrincipal());
        System.out.println("황주은 - AuthenticationFacade.getPrincipal()의 MyUserDetails = "+userDetails);

        if(auth != null){ // 로그인 했으면 트루
            return true;
        }
        return false;  // 로그인 안했으면 auth == null임
    }
}