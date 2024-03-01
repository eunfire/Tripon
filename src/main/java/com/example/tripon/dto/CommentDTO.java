package com.example.tripon.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("commentDto")
@Getter
@Setter
public class CommentDTO {
    private int bcId, boardId;
    private String nick;
    private LocalDateTime bcDate, bcUpdate;
    private String bcContent;
    private int parentId;
    private boolean deleted;
}
