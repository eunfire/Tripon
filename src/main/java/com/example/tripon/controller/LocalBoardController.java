package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.ImageDTO;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.CommunityBoardService;
import com.example.tripon.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@RequestMapping("/Local")
@RequiredArgsConstructor
@Controller
public class LocalBoardController {
    private final CommunityBoardService communityBoardService;
    private final MypageService mypageService;

    // cateId가 3인 게시글 목록 조회
    @GetMapping("/Information")
    public String getCate3Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 3;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 3이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = communityBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = communityBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "local-information-board";
    }

    // cateId가 4인 게시글 목록 조회
    @GetMapping("/Gyotong")
    public String getCate4Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 4;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 4이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = communityBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = communityBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "local-gyotong-board";
    }

    // cateId가 5인 게시글 목록 조회
    @GetMapping("/Friends")
    public String getCate5Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 5;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 5이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = communityBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = communityBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "local-friends-board";
    }

    // cateId가 6인 게시글 목록 조회
    @GetMapping("/Review")
    public String getCate2Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 6;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 6이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = communityBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = communityBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "local-review-board";
    }

    // 게시글 등록 조회
    @GetMapping("/write")
    public String boardWriteForm() {
        return "write-post";
    }

    // 게시글 등록 조회2
    @GetMapping("/writer")
    public String boardWriterForm() {
        return "writer-post";
    }

    // 게시글 상세페이지 조회 (여행후기)
    @GetMapping("/reviewPost/{postId}")
    public String showPostDetail(@PathVariable int postId, Principal principal, Model model) {
        // 게시글 조회수 증가
        communityBoardService.increaseViews(postId);
        BoardDTO post = communityBoardService.getBoardById(postId);
        model.addAttribute("post", post);

        // 이미지 파일
        List<ImageDTO> images = communityBoardService.viewImg(postId);
        model.addAttribute("images", images);


        // 댓글 조회
        List<CommentDTO> comments = communityBoardService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);

        // 좋아요 여부 확인
        model.addAttribute("postId", postId);
        int isLiked = 0;
        int confirm = 0;

        if(principal != null) {
            // 사용자 아이디 가져오기
            String memId = principal.getName();
            model.addAttribute("memId", memId);

            // 회원 정보 조회
            MemberDTO user = mypageService.getUser(memId);
            model.addAttribute("user", user);

            // 좋아요 확인
            isLiked = communityBoardService.getIsLiked(postId, memId);

            if(isLiked == 0) {
                model.addAttribute("isliked", isLiked);
            } else {
                model.addAttribute("isliked", isLiked);
            }
        } else {
            model.addAttribute("isliked", isLiked);
        }

        // 좋아요수 조회
        int likeCount = communityBoardService.getLikeCountByBoardId(postId);
        model.addAttribute("likeCount", likeCount);

        return "reviewPost-detail";
    }

    // 게시글 상세페이지 조회 (일행구하기)
    @GetMapping("/friendsPost/{postId}")
    public String showPostDetail2(@PathVariable int postId, Principal principal, Model model) {
        // 게시글 조회수 증가
        communityBoardService.increaseViews(postId);
        BoardDTO post = communityBoardService.getBoardById(postId);
        model.addAttribute("post", post);

        // 이미지 파일
        List<ImageDTO> images = communityBoardService.viewImg(postId);
        model.addAttribute("images", images);


        // 댓글 조회
        List<CommentDTO> comments = communityBoardService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);

        // 좋아요 여부 확인
        model.addAttribute("postId", postId);
        int isLiked = 0;
        int confirm = 0;

        if(principal != null) {
            // 사용자 아이디 가져오기
            String memId = principal.getName();
            model.addAttribute("memId", memId);

            // 회원 정보 조회
            MemberDTO user = mypageService.getUser(memId);
            model.addAttribute("user", user);

            // 좋아요 확인
            isLiked = communityBoardService.getIsLiked(postId, memId);

            if(isLiked == 0) {
                model.addAttribute("isliked", isLiked);
            } else {
                model.addAttribute("isliked", isLiked);
            }
        } else {
            model.addAttribute("isliked", isLiked);
        }

        // 좋아요수 조회
        int likeCount = communityBoardService.getLikeCountByBoardId(postId);
        model.addAttribute("likeCount", likeCount);
        return "friendsPost-detail";
    }
}