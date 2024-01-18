package com.example.tripon.repository;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MypageRepository {
    private final SqlSessionTemplate sql;

    // 회원 정보 조회
    public MemberDTO getUser(String memId) {
        return sql.selectOne("mypage.getUser", memId);
    }

    // 작성한 게시글 목록 조회
    public List<BoardDTO> getMyList(String memId, int startIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("memId", memId);
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        return sql.selectList("mypage.getMyList", params);
    }

    // 작성한 게시글 수 조회
    public int getMyListCount(String memId) {
        return sql.selectOne("mypage.getMyListCount", memId);
    }

    // memId를 이용하여 nick 조회
    public String getNick(String memId) {
        return sql.selectOne("mypage.getNick", memId);
    }

    // 작성한 댓글 목록 조회
    public List<CommentDTO> getMyReply(String nick, int startIndex, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("nick", nick);
        params.put("startIndex", startIndex);
        params.put("pageSize", pageSize);
        return sql.selectList("mypage.getMyReply", params);
    }

    // 작성한 댓글 수 조회
    public int getMyReplyCount(String nick) {
        return sql.selectOne("mypage.getMyReplyCount", nick);
    }
}
