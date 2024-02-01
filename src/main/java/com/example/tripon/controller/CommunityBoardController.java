package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.LikeDTO;
import com.example.tripon.service.CommunityBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RequestMapping("/Community")
@RequiredArgsConstructor
@Controller
public class CommunityBoardController {
    private final CommunityBoardService communityBoardService;

    // cateId가 1인 게시글 목록 조회
    @GetMapping("/FreeBoard")
    public String getCate1Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 1;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 1이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = communityBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = communityBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // 페이징 숫자 범위 계산
        int pagesToShow = 10; // 10 페이지까지 표시
        int startPage = Math.max(1, page - (pagesToShow / 2));
        int endPage = Math.min(totalPages, startPage + pagesToShow - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "free-board";
    }

    // cateId가 2인 게시글 목록 조회
    @GetMapping("/QnaBoard")
    public String getCate2Posts(@RequestParam(defaultValue = "1") int page, Model model) {
        int cateId = 2;
        int pageSize = 10; // 페이지당 게시글 수

        // 해당 페이지의 시작 인덱스 계산
        int startIndex = (page - 1) * pageSize;

        // cateId가 2이고 페이징된 게시글 목록을 조회
        List<BoardDTO> cate1Posts = communityBoardService.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);

        model.addAttribute("posts", cate1Posts);

        // 현재 페이지 번호와 총 페이지 수 계산하여 모델에 추가
        int totalPosts = communityBoardService.getBoardCountByCateId(cateId);
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // 페이징 숫자 범위 계산
        int pagesToShow = 10; // 10 페이지까지 표시
        int startPage = Math.max(1, page - (pagesToShow / 2));
        int endPage = Math.min(totalPages, startPage + pagesToShow - 1);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "qna-board";
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
        communityBoardService.increaseViews(postId);
        BoardDTO post = communityBoardService.getBoardById(postId);
        model.addAttribute("post", post);
        return "post-detail";
    }

    // 게시글 삭제
    @PostMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable int postId) {
        communityBoardService.deletePostById(postId);
        return "redirect:/main";
    }

    // 글쓰기 저장
    @PostMapping("/write")
    public String boardWriteForm(@RequestParam String memId, @RequestParam int cateId, @RequestParam String title,
                                 @RequestParam String content, @RequestParam LocalDate tStart,
                                 @RequestParam LocalDate tEnd) {

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setMemId(memId);
        boardDTO.setCateId(cateId);
        boardDTO.setTitle(title);
        boardDTO.setContent(content);
        boardDTO.setTStart(tStart);
        boardDTO.setTEnd(tEnd);

        System.out.println(boardDTO);

        communityBoardService.addPost(boardDTO);

        return "redirect:/Local/Information";
    }

    // 수정할 게시글 정보 불러오기
    @GetMapping("/post/{postId}/edit")
    public String editPostForm(@PathVariable int postId, Model model) {
        BoardDTO post = communityBoardService.getBoardById(postId);
        model.addAttribute("post", post);
        return "edit-post";
    }

    // 게시글 수정 기능 구현
    @PostMapping("/post/{postId}/edit")
    public String editPost(@PathVariable int postId, @ModelAttribute BoardDTO updatedPost) {
        communityBoardService.editPost(updatedPost);
        return "redirect:/Community/post/" + postId;
    }

    // 댓글 목록 조회
    @GetMapping("/post/{postId}/comments")
    public String getCommentsForPost(@PathVariable int postId, Model model) {
        List<CommentDTO> comments = communityBoardService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "post-detail";
    }

    // 댓글 등록
    @PostMapping("/post/{postId}/comments")
    public String addCommentToPost(@PathVariable int postId, CommentDTO comment) {
        comment.setBoardId(postId);
        communityBoardService.addComment(comment);
        return "redirect:/Community/post/" + postId;
    }

    // 좋아요 수 조회
    @GetMapping("/post/{postId}/like")
    public String getLikeCountForPost(@PathVariable int postId, Model model) {
        int boardId = communityBoardService.getLikeCountByBoardId(postId);
        model.addAttribute("boardId", boardId);
        return "post-detail";
    }

    // 좋아요 추가
    @PostMapping("/post/{postId}/like")
    public String addLikeToPost(@PathVariable int postId, LikeDTO likeDTO) {
        likeDTO.setBoardId(postId);
        communityBoardService.addLike(likeDTO);
        return "redirect:/Community/post/" + postId;
    }
}