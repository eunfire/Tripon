package com.example.tripon.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("memberDto")
@Getter
@Setter
public class MemberDTO {
    private String memId, pw, name, nick, email;
    private boolean role;
}
