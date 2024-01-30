package com.example.tripon.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        String username = request.getParameter("memId");
        String password = request.getParameter("pw");

        if (username == null || username.isEmpty()) {
            errorMessage = "아이디를 입력하세요.";
        } else if(password == null || password.isEmpty()) {
            errorMessage = "비밀번호를 입력하세요.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호를 잘못 입력했습니다.<br>입력하신 내용을 다시 확인해주세요.";
        } else if (exception instanceof LockedException) {
            errorMessage = "계정이 잠겼습니다.<br>관리자에게 문의하세요.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화되었습니다.<br>관리자에게 문의하세요.";
        } else {
            errorMessage = "로그인에 실패하였습니다.<br>다시 시도해주세요.";
        }

        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect("/sign/signin?error"); // 로그인 실패 시 로그인 페이지로 이동
    }
}
