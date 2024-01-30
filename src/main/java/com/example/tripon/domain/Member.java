package com.example.tripon.domain;

import com.example.tripon.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//Spring Security의 UserDetails의 커스텀파일
@Alias("member")
@Getter
@Setter
public class Member implements UserDetails {

    private String memId, pw, name, nick, email;
    private boolean role;

    // 계정이 갖고있는 권한 목록을 리턴한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = role ? "ROLE_ADMIN" : "ROLE_USER";
        return List.of(new SimpleGrantedAuthority(roles ));
    }

    // 계정의 비밀번호를 리턴한다.
    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {  //계정의 아이디를 리턴한다.
        return memId;
    }

    @Override
    public boolean isAccountNonExpired() {  //계정이 만료되지 않았는 지 리턴한다. (true: 만료안됨)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  //계정이 잠겨있지 않았는 지 리턴한다. (true: 잠기지 않음)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //비밀번호가 만료되지 않았는 지 리턴한다. (true: 만료안됨)
        return true;
    }

    @Override
    public boolean isEnabled() {  //계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)
        return true;
    }
}
