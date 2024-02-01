package com.example.tripon.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("imageDTO")
@Getter
@Setter
public class ImageDTO {
    private int imgId;
    private int boardId;
    private String orginName;
    private String saveName;
    private String path;
}
