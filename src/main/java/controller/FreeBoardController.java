package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class FreeBoardController {

    @GetMapping("/free-board")
    public String free() {
        return "/free-board";
    }

    @GetMapping("/qna-board")
    public String qna() {
        return "/qna-board";
    }
}
