package com.example.tripon.controller;

import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // 로그인 프로세스
    @PostMapping("/signinProcess")
    public String signin(MemberDTO dto) {
        boolean isValidMember = memberService.isValidMember(dto.getMemId(), dto.getPw());
        if (isValidMember)
            return "main";
        return "login";
    }

    // 회원가입
    @GetMapping("/signup")
    public String signuppage() {
        return "signup";
    }

    // 회원가입 프로세스
    @PostMapping("/signupProcess")
    public String signup(MemberDTO dto) {
        if (dto == null) {
            return "signup";
        } else {
            memberService.signup(dto);
            return "signin";
        }
    }

    // 아이디 중복 확인
    @PostMapping("/checkid")
    @ResponseBody
    public int checkId(@RequestParam("memId") String memId) {
        if(memId.equals(memberService.checkId(memId))) {
            return 1;
        }return 0;
    }

    // 닉네임 중복 확인
    @PostMapping("/checknick")
    @ResponseBody
    public int checkNick(@RequestParam("nick") String nick) {
        if(nick.equals(memberService.checkNick(nick))) {
            return 1;
        }return 0;
    }
}
