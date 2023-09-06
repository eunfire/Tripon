package com.example.tripon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class WorldBoardController {

    @GetMapping("/world-information-board")
    public String information() {
        return "/world-information-board";
    }

    @GetMapping("/world-gyotong-board")
    public String gyotong() {
        return "/world-gyotong-board";
    }

    @GetMapping("/world-friends-board")
    public String friends() {
        return "/world-friends-board";
    }

    @GetMapping("/world-review-board")
    public String review() {
        return "/world-review-board";
    }
}
