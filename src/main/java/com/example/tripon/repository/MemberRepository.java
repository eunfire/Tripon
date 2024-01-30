package com.example.tripon.repository;

import com.example.tripon.domain.Member;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final SqlSessionTemplate sql;

    public Optional<Member> findByUserid(String memId) {
        Member member = sql.selectOne("member.findByUserid", memId);
        return Optional.ofNullable(member);
    }
    public void addMember(Member member) {
        sql.insert("member.addMember", member);
    }

    public String checkId(String memId) {
        return sql.selectOne("member.checkId", memId);
    }
    public String checkNick(String nick) {
        return sql.selectOne("member.checkNick", nick);
    }

}