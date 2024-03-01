package com.example.tripon.repository;

import com.example.tripon.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MainBoardRepository {
    private final SqlSessionTemplate sql;

    // 전체 게시글 목록 조회
    public List<BoardDTO> getAllPosts(int startIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        return sql.selectList("main.getAllPosts", params);
    }

    // 전체 게시글 수 조회
    public int getAllPostCount() {
        return sql.selectOne("main.getAllPostCount");
    }

    // 검색
    public List<BoardDTO> searchPostsByTitle(String title, int startIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        return sql.selectList("main.searchPostsByTitle", params);
    }

    // 좋아요
    public List<BoardDTO> likeBoard(int startIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        return sql.selectList("main.likeBoard", params);
    }
}
