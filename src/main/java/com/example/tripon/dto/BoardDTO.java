package com.example.tripon.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("boardDTO")
@Getter
@Setter
public class BoardDTO {
    private int boardId;
    private String memId;
    private int cateId;
    private String title;
    private LocalDateTime tStart;
    private LocalDateTime tEnd;
    private int tMember;
    private String content;
    private LocalDateTime createdTime;
    private int views;
    private LocalDateTime editTime;
}

