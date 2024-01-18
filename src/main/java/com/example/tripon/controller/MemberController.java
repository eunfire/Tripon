package com.example.tripon.controller;

import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sign")
public class MemberController {
    private final MemberService memberService;

    //로그인
    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @PostMapping("/signinProcess")
    public String signin(MemberDTO dto) {
        boolean isValidMember = memberService.isValidMember(dto.getMemId(), dto.getPw());
        if (isValidMember)
            return "Main/board";
        return "login";
    }

    @GetMapping("/signup")
    public String signuppage() {
        return "signup";
    }

    @PostMapping("/signupProcess")
    public String signup(MemberDTO dto) {
        memberService.signup(dto);
        return "signin";
    }
}
