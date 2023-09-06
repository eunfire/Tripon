package com.example.tripon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class NoticeBoardController {

    @GetMapping("/notice-board")
    public String notice() {
        return "/notice-board";
    }

    @GetMapping("/support-board")
    public String support() {
        return "/support-board";
    }
}
