package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.service.WorldBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/World")
@RequiredArgsConstructor
@Controller
public class WorldBoardController {
    private final WorldBoardService worldBoardService;

    // cateId가 3인 게시글 목록 조회
    @GetMapping("/Information")
    public String getCate3Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 7;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 3이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = worldBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = worldBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "world-information-board";
    }

    // cateId가 4인 게시글 목록 조회
    @GetMapping("/Gyotong")
    public String getCate4Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 8;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 4이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = worldBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = worldBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "world-gyotong-board";
    }

    // cateId가 5인 게시글 목록 조회
    @GetMapping("/Friends")
    public String getCate5Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 9;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 5이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = worldBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = worldBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "local-friends-board";
    }

    // cateId가 6인 게시글 목록 조회
    @GetMapping("/Review")
    public String getCate2Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 10;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 6이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = worldBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = worldBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "local-review-board";
    }

    // 게시글 등록 조회
    @GetMapping("/writer")
    public String boardWriteForm() {
        return "write-post";
    }

    // 게시글 상세페이지 조회
    @GetMapping("/post/{postId}")
    public String showPostDetail(@PathVariable int postId, Model model) {
        // 게시글 조회수 증가
        worldBoardService.increaseViews(postId);
        BoardDTO post = worldBoardService.getBoardById(postId);
        model.addAttribute("post", post);
        return "post-detail";
    }
}