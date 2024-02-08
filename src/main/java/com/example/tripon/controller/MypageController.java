package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;

    // 회원 정보 조회
    @GetMapping("")
    public String myInfomation(Principal principal, Model model) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // 회원 정보 조회
        MemberDTO user = mypageService.getUser(memId);
        model.addAttribute("user", user);

        return "mypage-infomation";
    }

    // 비밀번호 확인
    @GetMapping("/checkpw")
    public String checkPw() {
        return "mypage-checked";
    }

    // 비밀번호 확인 프로세스
    @PostMapping("/checkpwprocess")
    public ResponseEntity<String> checkPwProcess(Principal principal, String pw) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();
        // 현재 비밀번호 확인
        boolean isValidPw = mypageService.pwChecked(memId, pw);

        if (isValidPw) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("fail");
    }

    // 회원 정보 수정
    @GetMapping("/myinfoedit")
    public String myInfoedit(Principal principal, Model model) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // 회원 정보 조회
        MemberDTO user = mypageService.getUser(memId);
        model.addAttribute("user", user);

        return "mypage-information-change";
    }

    // 회원 정보 수정 프로세스
    @PostMapping("/editProcess")
    public String myInfoupdate(Principal principal, String name, String nick, String email) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // 회원 정보 수정
        MemberDTO updto = new MemberDTO();
        updto.setMemId(memId);
        updto.setName(name);
        updto.setNick(nick);
        updto.setEmail(email);
        mypageService.infoUpdate(updto);

        return "redirect:/mypage";
    }

    // 비밀번호 변경
    @GetMapping("/pwchange")
    public String myPagepw() {
        return "mypage-pw-change";
    }

    // 비밀번호 변경 프로세스
    @PostMapping("/pwchangeProcess")
    public ResponseEntity<String> myPagepwChange(Principal principal, String pw, String changePw, String checkPw) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // 현재 비밀번호 확인
        boolean isValidPw = mypageService.pwChecked(memId, pw);

        // 조건 만족시 비밀번호 변경 실행
        if (isValidPw) {
            if (changePw.equals(checkPw)) {
                mypageService.pwUpdate(memId, changePw);
                return ResponseEntity.ok("success"); // 비밀번호 변경 성공
            } else {
                return ResponseEntity.ok("pwMismatch"); // 새로운 비밀번호와 비밀번호 확인이 일치하지 않음
            }
        } else {
            return ResponseEntity.ok("invalidPw"); // 현재 비밀번호가 다름
        }
    }

    // 내가 작성한 게시글 목록 조회
    @GetMapping("/myinfowrite")
    public String getMyList(Principal principal, Model model, @RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(defaultValue = "1") int page) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // 페이지당 게시글 수
        int pageSize = 10;

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        List<BoardDTO> myList;

        // 검색 파라미터에 따라 다른 쿼리를 실행하고 결과를 보여주도록 로직 구성
        if (!search.isEmpty()) {
            myList = mypageService.searchMyList(search, memId, startIndex, pageSize);
            model.addAttribute("mylist", myList);

            // 총 페이지 수와 현재 페이지 번호 모델에 추가
            int totalPosts = mypageService.searchMyListCount(search, memId);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
        } else {
            // 검색 파라미터가 비어 있으면 전체 리스트를 가져오도록 처리
            myList = mypageService.getMyList(memId, startIndex, pageSize);
            model.addAttribute("mylist", myList);

            // 총 페이지 수와 현재 페이지 번호 모델에 추가
            int totalPosts = mypageService.getMyListCount(memId);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
        }
        return "mypage-writing";
    }


    // 내가 작성한 댓글 목록 조회
    @GetMapping("/myinforeply")
    public String getMyReply(Principal principal, Model model, @RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(defaultValue = "1") int page) {
        // 로그인한 사용자 아이디 가져오기
        String memId = principal.getName();

        // memId를 이용하여 nick 찾기
        String nick = mypageService.getNick(memId);

        // 페이지당 게시글 수
        int pageSize = 10;

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        List<CommentDTO> myReply;

        // 검색 파라미터에 따라 다른 쿼리를 실행하고 결과를 보여주도록 로직 구성
        if (!search.isEmpty()) {

        // 작성한 게시글 목록을 조회
        myReply = mypageService.searchMyReply(search, nick, startIndex, pageSize);
        model.addAttribute("myreply", myReply);

        // 총 페이지 수와 현재 페이지 번호 모델에 추가
        int totalPosts = mypageService.searchMyReplyCount(search, nick);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        } else {
            // 작성한 게시글 목록을 조회
            myReply = mypageService.getMyReply(nick, startIndex, pageSize);
            model.addAttribute("myreply", myReply);

            // 총 페이지 수와 현재 페이지 번호 모델에 추가
            int totalPosts = mypageService.getMyReplyCount(nick);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
        }

        return "mypage-reply";
    }
}
