package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.service.MainBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainBoardService mainBoardService;
    @GetMapping("/main")
    public String getAllPosts(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 8; // 페이지당 게시글 수
        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // 전체 게시글 목록을 조회
        List<BoardDTO> allPosts = mainBoardService.getAllPosts(startIndex, pageSize);

        model.addAttribute("posts", allPosts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = mainBoardService.getAllPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "Main";
    }

    @GetMapping("/search-all")
    public String getAllPosts(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(name = "search", required = false) String search,
                              Model model) {
        int pageSize = 10; // 페이지당 게시글 수
        int startIndex = (page - 1) * pageSize;

        List<BoardDTO> allPosts;

        if (search != null && !search.isEmpty()) {
            // 검색어를 이용하여 게시글을 검색
            allPosts = mainBoardService.searchPostsByTitle(search, startIndex, pageSize);
        } else {
            // 전체 게시글 목록을 조회
            allPosts = mainBoardService.getAllPosts(startIndex, pageSize);
        }

        model.addAttribute("posts", allPosts);

        int totalPosts = mainBoardService.getAllPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // 페이징 숫자 범위 계산
        int pagesToShow = 10; // 10 페이지까지 표시
        int startPage = Math.max(1, page - (pagesToShow / 2));
        int endPage = Math.min(totalPages, startPage + pagesToShow - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "search-all";
    }

    @GetMapping("/newest")
    public String getAllPosts2(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10; // 페이지당 게시글 수
        int startIndex = (page - 1) * pageSize;

        List<BoardDTO> allPosts = mainBoardService.getAllPosts(startIndex, pageSize);
        model.addAttribute("posts", allPosts);

        int totalPosts = mainBoardService.getAllPostCount();
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // 페이징 숫자 범위 계산
        int pagesToShow = 10; // 10 페이지까지 표시
        int startPage = Math.max(1, page - (pagesToShow / 2));
        int endPage = Math.min(totalPages, startPage + pagesToShow - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "/newest-board";
    }
}