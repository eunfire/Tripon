package com.example.tripon.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("likeDTO")
@Getter
@Setter
public class LikeDTO {
    private String memId;
    private int boardId;
}
