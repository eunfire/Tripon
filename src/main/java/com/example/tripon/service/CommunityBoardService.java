package com.example.tripon.service;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.LikeDTO;
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
    public void addPost(BoardDTO boardDTO) {
        communityBoardRepository.addPost(boardDTO);
    }

    // 게시글 수정
    public void editPost(BoardDTO updatedPost) {
        communityBoardRepository.editPost(updatedPost);
    }

    public List<CommentDTO> getCommentsByPostId(int postId) {
        return communityBoardRepository.getCommentsByPostId(postId);
    }

    public void addComment(CommentDTO comment) {
        communityBoardRepository.addComment(comment);
    }

    // 좋아요 수 조회
    public int getLikeCountByBoardId(int boardId) {
        return communityBoardRepository.getLikeCountByBoardId(boardId);
    }

    // 좋아요 추가
    public void addLike(LikeDTO likeDTO) {
        communityBoardRepository.addLike(likeDTO);
    }
}
