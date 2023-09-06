package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SignupController {

    @GetMapping("/join")
    public String join() {
        return "/join";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
