package com.example.tripon.repository;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.ImageDTO;
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
    public  int addPost(BoardDTO boardDTO) {
        sql.insert("free.addPost", boardDTO);
        return boardDTO.getBoardId();
    }

    // 게시글 수정
    public void editPost(BoardDTO boardDTO) {
        sql.update("free.editPost", boardDTO);
    }

    // 댓글 조회
    public List<CommentDTO> getCommentsByPostId(int postId) {
        return sql.selectList("free.getCommentsByPostId", postId);
    }

    // 댓글 등록
    public void addComment(CommentDTO comment) {
        sql.insert("free.addComment", comment);
    }

    // 대댓글 등록
    public void add_comment(CommentDTO comment) {
        sql.insert("free.add_comment", comment);
    }

    // 좋아요 수 조회
    public int getLikeCountByBoardId(int boardId) {
        return sql.selectOne("free.getLikeCountByBoardId", boardId);
    }

    // 좋아요 추가
    public int addLike(int postId, String memId) {
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("memId", memId);
        return sql.insert("free.addLike", params);
    }

    // 좋아요 취소
    public int removeLike(int postId, String memId) {
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("memId", memId);
        return sql.delete("free.removeLike", params);
    }

    public int getIsLiked(int postId, String memId) {
        // MyBatis의 SqlSession을 사용하여 쿼리를 실행하고 결과를 반환한다.
        // 쿼리를 실행할 때는 Map을 사용하여 파라미터를 전달한다.
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("memId", memId);

        // sqlSession.selectOne() 메서드를 사용하여 한 개의 결과만 반환한다.
        // 결과가 없을 경우 null을 반환하므로, null이 아닐 때는 true를 반환하도록 한다.
        return sql.selectOne("free.getIsLiked", params);
    }

    // 댓글 삭제
    public void deleteCommentById(int bcId) {
        sql.delete("free.deleteCommentById", bcId);
    }

    //댓글 삭제 업데이트
    public void updateDeleteComment(int bcId) {
        sql.update("free.updateDeleteComment", bcId);
    }

    public boolean searchParentId(int bcId) {
        List<Object> result = sql.selectList("free.searchParentId", bcId);
        return !result.isEmpty();
    }

    // 삭제 여부 확인
    public boolean deletedComment(int bcId) {
        return sql.selectOne("free.deletedComment", bcId);
    }

    public Integer searchParentBcId(int bcId) {
        return sql.selectOne("free.searchParentBcId", bcId);
    }

    // 댓글 수정
    public void editComment(CommentDTO commentDto) {
        sql.update("free.editComment", commentDto);
    }

    // 파일 저장
    public void saveFile(ImageDTO imageDTO) {
        sql.insert("free.saveFile", imageDTO);
    }

    // 파일 조회
    public List<ImageDTO> viewImg(int postId) {
        return sql.selectList("free.viewImg", postId);
    }

}
