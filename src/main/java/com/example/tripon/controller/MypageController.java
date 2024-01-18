package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/myinfo")
    public String myInfo() {
        return "mypage";
    }

    @GetMapping("/mypage-checked")
    public String myPageChecked() {
        return "mypage-checked";
    }

    @GetMapping("/myinfochange")
    public String myInfoChange() {
        return "mypage-information-change";
    }

    // 회원 정보 조회
    @GetMapping("/myinfomation")
    public String myInfomation(Principal principal, Model model) {
        String memId = principal.getName();
        MemberDTO user = mypageService.getUser(memId);
        model.addAttribute("user", user);
        return "mypage-infomation";
    }

    private static final Logger logger = LoggerFactory.getLogger(MypageController.class);
    //내가 작성한 게시글 목록 조회
    @GetMapping("/myinfowrite")
    public String getMyList(Principal principal, Model model, @RequestParam(defaultValue="1") int page) {
        String memId = principal.getName();
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // 작성한 게시글 목록을 조회
        List<BoardDTO> myList = mypageService.getMyList(memId, startIndex, pageSize);
        model.addAttribute("mylist", myList);

        // 총 페이지 수와 현재 페이지 번호 모델에 추가
        int totalPosts = mypageService.getMyListCount(memId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        logger.info("Pages: {}", page);
        logger.info("TotalPages: {}", totalPages);
        return "mypage-writing";
    }

    @GetMapping("/myinforeply")
    public String getMyReply(Principal principal, Model model, @RequestParam(defaultValue="1") int page) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // memId를 이용하여 nick 찾기
        String nick = mypageService.getNick(memId);

        // 페이지당 게시글 수
        int pageSize = 10;

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // 작성한 게시글 목록을 조회
        List<CommentDTO> myReply = mypageService.getMyReply(nick, startIndex, pageSize);
        model.addAttribute("myreply", myReply);

        // 총 페이지 수와 현재 페이지 번호 모델에 추가
        int totalPosts = mypageService.getMyReplyCount(nick);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        logger.info("TotalPages: {}", page);
        return "mypage-reply";
    }
}
