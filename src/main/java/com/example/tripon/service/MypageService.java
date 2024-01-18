package com.example.tripon.service;


import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.repository.MypageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final MypageRepository repo;

    // 회원 정보 조회
    public MemberDTO getUser(String memId) {
        return repo.getUser(memId);
    }

    // 작성한 게시글 목록 조회
    public List<BoardDTO> getMyList(String memId, int startIndex, int pageSize) {
        return repo.getMyList(memId, startIndex, pageSize);
    }

    // 작성한 게시글 수 조회
    public int getMyListCount(String memId) {
        return repo.getMyListCount(memId);
    }

    // memId를 이용하여 nick 조회
    public String getNick(String memId) {
        return repo.getNick(memId);
    }
    // 작성한 댓글 목록 조회
    public List<CommentDTO> getMyReply(String nick, int startIndex, int pageSize) {
        return repo.getMyReply(nick, startIndex, pageSize);
    }

    // 작성한 댓글 수 조회
    public int getMyReplyCount(String nick) {
        return repo.getMyReplyCount(nick);
    }
}
