package com.example.tripon.service;

import com.example.tripon.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {
    private final MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findOne = memberService.findOne(username);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다"));

        String role = member.isRole() ? "ADMIN" : "USER";

        return User.builder()
                .username(username)
                .password(member.getPw())
                .roles(role)
                .build();
    }
}
