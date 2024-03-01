package com.example.tripon.service;

import com.example.tripon.dto.BoardDTO;
import com.example.tripon.repository.MainBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBoardService {
    private final MainBoardRepository mainBoardRepository;

    // 전체 게시글 목록 조회
    public List<BoardDTO> getAllPosts(int startIndex, int pageSize) {
        return mainBoardRepository.getAllPosts(startIndex, pageSize);
    }

    // 전체 게시글 수 조회
    public int getAllPostCount() {
        return mainBoardRepository.getAllPostCount();
    }

    public List<BoardDTO> searchPostsByTitle(String title, int startIndex, int pageSize) {
        return mainBoardRepository.searchPostsByTitle(title, startIndex, pageSize);
    }

    public List<BoardDTO> likeBoard(int startIndex, int pageSize) {
        return mainBoardRepository.likeBoard(startIndex, pageSize);
    }
}
