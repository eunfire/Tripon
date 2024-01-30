package com.example.tripon.service;

import com.example.tripon.domain.Member;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository repo;
    private final PasswordEncoder passwordEncoder;

    // memId에 해당하는 회원 정보 조회
    public Optional<Member> findOne(String memId) {  //Optional<Member>는 값이 null일 때 null로 표시하지 않고 비어있다 라는 객체를 생성하여 명시적으로 표현할 수 있음
        return repo.findByUserid(memId);
    }

    // memId에 해당하는 비밀번호 입력시 암호화된 비밀번호와 매치되는지 확인 방법1 -> 방법2는 MypageService에 구현
    public boolean isValidMember(String memId, String pw) {
        Optional<Member> member = findOne(memId);
        /* member의 값이 있을때 .map()메소드 실행 - value이 value.getPw().equals(pw)의 조건에 맞으면 true 아니면 false
        member의 값이 없을때 .orElse()메소드 실행 - other은 값이 없을때 반환할 값을 나타냄 */
        return member.map(value -> passwordEncoder.matches(pw, value.getPw())).orElse(false);
    }

    // 회원가입
    public void signup(MemberDTO dto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPw());

        // DTO를 도메인 모델로 변환
        Member member = new Member();
        member.setMemId(dto.getMemId());
        member.setPw(encodedPassword);
        member.setName(dto.getName());
        member.setNick(dto.getNick());
        member.setEmail(dto.getEmail());

        // 도메인 모델을 저장
        repo.addMember(member);
    }

    // 아이디 중복 확인
    public String checkId(String memId) {
        return repo.checkId(memId);
    }

    // 닉네임 중복 확인
    public String checkNick(String nick) {
        return repo.checkNick(nick);
    }
}
