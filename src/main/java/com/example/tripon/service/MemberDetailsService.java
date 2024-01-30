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
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        String role = member.isRole() ? "ADMIN" : "USER";

        // MemberDetails를 사용하여 UserDetails 객체 생성
        Member memberDetails = new Member();
        memberDetails.setMemId(username);
        memberDetails.setPw(member.getPw());
        memberDetails.setName(member.getName());
        memberDetails.setNick(member.getNick());
        memberDetails.setEmail(member.getEmail());
        memberDetails.setRole(member.isRole());

        return memberDetails;
    }
}
