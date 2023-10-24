package com.example.tripon.repository;

import com.example.tripon.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Mapper
public interface MemberRepository {

    Optional<Member> findByUserid(String memId);
}
