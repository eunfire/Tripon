package com.example.tripon.controller;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.ImageDTO;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.service.CommunityBoardService;
import com.example.tripon.service.MypageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RequestMapping("/Community")
@RequiredArgsConstructor
@Controller
public class CommunityBoardController {
    private final CommunityBoardService communityBoardService;
    private final MypageService mypageService;

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
                                 @RequestParam LocalDate tEnd, @RequestParam("images") List<MultipartFile> files) {

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setMemId(memId);
        boardDTO.setCateId(cateId);
        boardDTO.setTitle(title);
        boardDTO.setContent(content);
        boardDTO.setTStart(tStart);
        boardDTO.setTEnd(tEnd);

        System.out.println(boardDTO);

        int boardId = communityBoardService.addPost(boardDTO);

        //파일저장 :DB와 서버에 저장
        String savePath = "c:/kdt/upload/tripon/"; //파일저장경로.

        int sequence = 0;


        // 업로드된 각 파일에 대해 처리
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
            if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
                continue;
            }

            // 파일 정보 생성
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setBoardId(boardId); // 해당 파일이 속한 게시글 ID 설정

            // 파일 이름 중복을 방지하기 위한 UUID 생성 및 저장 이름 설정
            String originalFilenameWithoutExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
            String saveName = originalFilenameWithoutExtension + "_" + UUID.randomUUID().toString() + "." + fileExtension;
            imageDTO.setSaveName(saveName);
            imageDTO.setPath(savePath);

            //파일 뒤에 _로 끝나는거 에러 방지.
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                if (originalFilename.endsWith("_")) {
                    String[] parts = originalFilename.split("_");
                    if (parts.length >= 2) {
                        imageDTO.setOrginName(parts[0]);
                    }
                } else {
                    imageDTO.setOrginName(originalFilename);
                }
            }

            // 이미지 순서 증가
            imageDTO.setSequence(sequence++);

            // DB에 파일 정보 저장
            communityBoardService.saveFile(imageDTO);

            // 파일을 서버에 저장
            Path filePath = Paths.get(savePath, saveName);
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // 파일 저장 중 에러 발생 시 처리
            }
        }

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
    public String editPost(@PathVariable int postId, @ModelAttribute BoardDTO boardDTO) {
        communityBoardService.editPost(boardDTO);
        return "redirect:/Community/post/" + postId;
    }

    /* 댓글 목록 조회
    @GetMapping("/post/{postId}/comments")
    public String getCommentsForPost(@PathVariable int postId, Model model) {
        List<CommentDTO> comments = communityBoardService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "post-detail";
    } */

    // 댓글 등록
    @PostMapping("/post/{postId}/comments")
    public String addCommentToPost(@PathVariable int postId, @RequestParam("nick") String nick,
                                   @RequestParam("bcContent") String bcContent,
                                   @RequestParam(value = "parentId", required = false) Integer parentId) {

        CommentDTO commentDto = new CommentDTO();
        commentDto.setBoardId(postId);
        commentDto.setNick(nick);
        commentDto.setBcContent(bcContent);

        if (parentId != null) {
            commentDto.setParentId(parentId);
            communityBoardService.add_comment(commentDto);
        }
        else {
             communityBoardService.addComment(commentDto);
        }

        return "redirect:/Community/post/" + postId;
    }

    // 좋아요 추가
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<String> addLikeToPost(@PathVariable int postId, Principal principal, @RequestParam int isLiked) {
        if (principal != null) {
            String memId = principal.getName();
            if (isLiked == 0) {
                communityBoardService.addLike(postId, memId);
            } else if (isLiked == 1) {
                communityBoardService.removeLike(postId, memId);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok("Login required");
        }
    }

    /*// 댓글 삭제
    @PostMapping("/post/{postId}/comments/delete")
    public String deleteComment(@PathVariable int postId, @RequestParam int bcId) {
        communityBoardService.deleteCommentById(bcId);
        return "redirect:/Community/post/" + postId;
    }*/

    // 댓글 삭제
    @PostMapping("/post/{postId}/comments/delete")
    public String deleteComment(@PathVariable int postId, @RequestParam int bcId) {
        boolean ParentId = communityBoardService.searchParentId(bcId);// 자식댓글 여부 확인
        Integer parentBcId = communityBoardService.searchParentBcId(bcId); // 부모bcId 받아오기 없으면 null
        if (ParentId) { //자식댓글이 있을때 부모댓글 삭제
            communityBoardService.updateDeleteComment(bcId);
        }
        else { // 자식댓글이 없을 때 부모댓글 삭제 또는 자식댓글 삭제
            if (parentBcId != null) { // 자식댓글일때
                communityBoardService.deleteCommentById(bcId);//자식댓글 삭제
                boolean deleted = communityBoardService.deletedComment(parentBcId); // 부모 댓글의 삭제 여부 확인
                boolean Parent = communityBoardService.searchParentId(parentBcId); // 부모 댓글에 자식댓글이 있는지 확인
                if (!Parent && deleted) { // 부모댓글이 삭제된 상태에서 자식댓글이 전부 삭제 되었을 때
                    communityBoardService.deleteCommentById(parentBcId); // 부모 댓글 삭제
                }
            }
            communityBoardService.deleteCommentById(bcId); // 부모댓글이고 자식댓글이 없을 때
        }
        return "redirect:/Community/post/" + postId;
    }

    // 댓글 수정 기능 구현
    @PostMapping("/post/{postId}/comments/edit")
    public String editComment(@PathVariable int postId, @RequestParam int bcId, @RequestParam String bcContent) {
        CommentDTO commentDto = new CommentDTO();
        commentDto.setBcId(bcId);
        commentDto.setBcContent(bcContent);
        communityBoardService.editComment(commentDto);
        return "redirect:/Community/post/" + postId;
    }

    // 파일 저장



}