package com.example.tripon.service;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.repository.WorldBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldBoardService {
    private final WorldBoardRepository worldBoardRepository;

    // cateId가 1이고 페이징된 게시글 목록 조회
    public List<BoardDTO> getBoardsByCateIdWithPaging(int cateId, int startIndex, int pageSize) {
        return worldBoardRepository.getBoardsByCateIdWithPaging(cateId, startIndex, pageSize);
    }

    // cateId가 1인 게시글 목록 조회
    public int getBoardCountByCateId(int cateId) {
        return worldBoardRepository.getBoardCountByCateId(cateId);
    }

    // 게시글 상세페이지 조회
    public BoardDTO getBoardById(int postId) {
        return worldBoardRepository.getBoardById(postId);
    }

    // 게시글 조회수 증가
    public void increaseViews(int postId) {
        worldBoardRepository.increaseViews(postId);
    }
}