package com.example.tripon.repository;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.LikeDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommunityBoardRepository {
    private final SqlSessionTemplate sql;

    // cateId가 1이고 페이징된 게시글 목록 조회
    public List<BoardDTO> getBoardsByCateIdWithPaging(int cateId, int startIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        return sql.selectList("free.getBoardsByCateIdWithPaging", params);
    }

    // cateId가 1인 게시글 목록 조회
    public int getBoardCountByCateId(int cateId) {
        return sql.selectOne("free.getBoardCountByCateId", cateId);
    }

    // 게시글 상세페이지 조회
    public BoardDTO getBoardById(int postId) {
        return sql.selectOne("free.getBoardById", postId);
    }

    // 게시글 조회수 증가
    public void increaseViews(int postId) {
        sql.update("free.increaseViews", postId);
    }

    // 게시글 삭제
    public void deletePostById(int postId) {
        sql.delete("free.deletePostById", postId);
    }

    // 글쓰기저장
    public  void addPost(BoardDTO boardDTO) {
        sql.insert("free.addPost", boardDTO);
    }

    // 게시글 수정
    public void editPost(BoardDTO updatedPost) {
        sql.update("free.editPost", updatedPost);
    }

    public List<CommentDTO> getCommentsByPostId(int postId) {
        return sql.selectList("free.getCommentsByPostId", postId);
    }

    public void addComment(CommentDTO comment) {
        sql.insert("free.addComment", comment);
    }

    // 좋아요 수 조회
    public int getLikeCountByBoardId(int boardId) {
        return sql.selectOne("free.getLikeCountByBoardId", boardId);
    }

    // 좋아요 추가
    public void addLike(LikeDTO likeDTO) {
        sql.insert("free.addLike", likeDTO);
    }
}
