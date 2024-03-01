package com.example.tripon.service;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.ImageDTO;
import com.example.tripon.repository.CommunityBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommunityBoardService {
    private final CommunityBoardRepository communityBoardRepository;

    // cateId가 1이고 페이징된 게시글 목록 조회
    public List<BoardDTO> getBoardsByCateIdWithPaging(int cateId, int startIndex, int pageSize) {
        return communityBoardRepository.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);
    }

    // cateId가 1인 게시글 목록 조회
    public int getBoardCountByCateId(int cateId) {
        return communityBoardRepository.getBoardCountByCateId(cateId);
    }

    // 게시글 상세페이지 조회
    public BoardDTO getBoardById(int postId) {
        return communityBoardRepository.getBoardById(postId);
    }

    // 게시글 조회수 증가
    public void increaseViews(int postId) {
        communityBoardRepository.increaseViews(postId);
    }

    // 게시글 삭제
    public void deletePostById(int postId) {
        communityBoardRepository.deletePostById(postId);
    }

    // 글저장
    public int addPost(BoardDTO boardDTO) {
        return communityBoardRepository.addPost(boardDTO);
    }

    // 게시글 수정
    public void editPost(BoardDTO boardDTO) {
        communityBoardRepository.editPost(boardDTO);
    }

    // 댓글 조회
    public List<CommentDTO> getCommentsByPostId(int postId) {
        return communityBoardRepository.getCommentsByPostId(postId);
    }

    // 댓글 등록
    public void addComment(CommentDTO comment) {
        communityBoardRepository.addComment(comment);
    }

    // 대댓글 등록
    public void add_comment(CommentDTO comment) {
        communityBoardRepository.add_comment(comment);
    }

    // 좋아요 수 조회
    public int getLikeCountByBoardId(int boardId) {
        return communityBoardRepository.getLikeCountByBoardId(boardId);
    }

    // 좋아요 추가
    public int addLike(int postId, String memId) {
        return communityBoardRepository.addLike(postId, memId);
    }

    // 좋아요 취소
    public int removeLike(int postId, String memId) {
        return communityBoardRepository.removeLike(postId, memId);
    }

    public int getIsLiked(int postId, String memId) {
       return communityBoardRepository.getIsLiked(postId, memId);
    }

   // 댓글 삭제
    public void deleteCommentById(int bcId) {
        communityBoardRepository.deleteCommentById(bcId);
    }

    // 댓글 삭제 업데이트
    public void updateDeleteComment(int bcId) {
        communityBoardRepository.updateDeleteComment(bcId);
    }

    public boolean searchParentId(int bcId) {
        return communityBoardRepository.searchParentId(bcId);
    }

    // 삭제 여부 확인
    public boolean deletedComment(int bcId) {
        return communityBoardRepository.deletedComment(bcId);
    }

    public Integer searchParentBcId(int bcId) {
        return communityBoardRepository.searchParentBcId(bcId);
    }

    // 댓글 수정
    public void editComment(CommentDTO commentDto) {
        communityBoardRepository.editComment(commentDto);
    }

    // 파일 저장
    public void saveFile(ImageDTO imageDTO) {
        communityBoardRepository.saveFile(imageDTO);
    }

    // 파일 조회
    public List<ImageDTO> viewImg(int postId) {
        return communityBoardRepository.viewImg(postId);
    }
}
