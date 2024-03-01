package com.example.tripon.controller;

import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sign")
public class MemberController {
    private final MemberService memberService;
    private final HttpSession session;

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
        if (memId.equals(memberService.checkId(memId))) {
            return 1;
        }
        return 0;
    }

    // 닉네임 중복 확인
    @PostMapping("/checknick")
    @ResponseBody
    public int checkNick(@RequestParam("nick") String nick) {
        if (nick.equals(memberService.checkNick(nick))) {
            return 1;
        }
        return 0;
    }

    // 이메일 중복 확인
    @PostMapping("/checkemail")
    @ResponseBody
    public int checkEmail(@RequestParam("email") String email) {
        if (email.equals(memberService.checkEmail(email))) {
            return 1;
        }
        return 0;
    }

    // 이메일 인증코드 보내기
    @PostMapping("/mail")
    public String sendEmail(@RequestParam("email") String email) {
        // 인증 코드 생성
        String authCode = memberService.createCode();
        // 세션에 인증 코드 및 생성 시간 저장
        session.setAttribute("authCode", authCode);
        session.setAttribute("authCodeTime", System.currentTimeMillis());
        // 인증 코드 메일 보내기
        memberService.createMail(email, authCode);

        return "signup";
    }

    // 이메일 인증 코드 검증
    @PostMapping("/checkcode")
    public @ResponseBody ResponseEntity<String> checkCode(@RequestParam("code") String code) {
        // 세션에 저장된 인증코드와 생성시간 받아오기
        String authCode = (String) session.getAttribute("authCode");
        Long authCodeTime = (Long) session.getAttribute("authCodeTime");
        // 세션에 인증코드와 코드생성시간 있으면
        if (authCode != null && authCodeTime != null) {
            // 코드 만료 기간 확인 후 이메일 인증코드 검증
            String result = memberService.checkCode(authCode, authCodeTime, code);
            return ResponseEntity.ok(result);
        } else {
            // 세션에 인증코드와 코드생성시간이 없으면
            return ResponseEntity.badRequest().body("세션에 인증 코드가 없습니다.");
        }
    }
}
