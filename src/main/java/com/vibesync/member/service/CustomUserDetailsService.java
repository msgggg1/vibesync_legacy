package com.vibesync.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vibesync.member.domain.Member;
import com.vibesync.member.mapper.MemberMapper;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 스프링 시큐리티가 로그인 요청 시 호출하는 핵심 메서드입니다.
     * @param username 사용자가 로그인 폼에 입력한 아이디 (여기서는 이메일)
     * @return UserDetails 객체 (스프링 시큐리티가 사용하는 사용자 정보 객체)
     * @throws UsernameNotFoundException 해당 사용자가 DB에 없을 경우 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Load User By Username : " + username);

        // 1. DB에서 사용자 정보를 조회
        Member member = memberMapper.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // 3. 조회된 사용자 정보를 바탕으로 Spring Security가 사용하는 UserDetails 객체를 생성하여 반환합니다.
        return User.builder()
                .username(member.getEmail()) // 로그인 아이디
                .password(member.getPw()) // DB에 저장된 '암호화된' 비밀번호
                .roles("USER") // 사용자의 권한 (예: "USER", "ADMIN")
                .build();
    }
}
