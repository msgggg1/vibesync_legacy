package com.vibesync.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; // 반환 타입은 UserDetails 인터페이스 유지
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vibesync.member.domain.Member;
import com.vibesync.member.mapper.MemberMapper;
import com.vibesync.security.domain.CustomUser;


@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberMapper.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("Cannot find user with that email: " + username);
        }

     // ✨ 디버깅을 위한 임시 출력 코드
        // 이 코드를 통해 실제로 필드에 값이 들어왔는지 확인합니다.
        System.out.println("-----------[MyBatis 조회 결과]-----------");
        System.out.println("조회된 Member 객체: " + member);
        System.out.println("Email 필드 값: " + member.getEmail());
        System.out.println("Role 필드 값: " + member.getRole());
        System.out.println("ac_idx 필드 값: " + member.getAc_idx());
        System.out.println("----------------------------------------");
        
        // 반환 객체만 CustomUser로 변경
        return new CustomUser(member);
    }
}
