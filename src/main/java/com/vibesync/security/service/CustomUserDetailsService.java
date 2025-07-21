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
        
        // 반환 객체만 CustomUser로 변경
        return new CustomUser(member);
    }
}
