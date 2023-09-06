package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class LocalBoardController {

    @GetMapping("/local-information-board")
    public String information() {
        return "/local-information-board";
    }

    @GetMapping("/local-gyotong-board")
    public String gyotong() {
        return "/local-gyotong-board";
    }

    @GetMapping("/local-friends-board")
    public String friends() {
        return "/local-friends-board";
    }

    @GetMapping("/local-review-board")
    public String review() {
        return "/local-review-board";
    }
}
