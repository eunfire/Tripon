package com.example.tripon.service;

import com.example.tripon.domain.Member;
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

    public Optional<Member> findOne(String memId) {  //Optional<Member>는 값이 null일 때 null로 표시하지 않고 비어있다 라는 객체를 생성하여 명시적으로 표현할 수 있음
        return repo.findByUserid(memId);
    }

    public boolean isValidMember(String memId, String pw) {
        Optional<Member> member = findOne(memId);
        /* member의 값이 있을때 .map()메소드 실행 - value이 value.getPw().equals(pw)의 조건에 맞으면 true 아니면 false
        member의 값이 없을때 .orElse()메소드 실행 - other은 값이 없을때 반환할 값을 나타냄 */
        return member.map(value -> value.getPw().equals(pw)).orElse(false);
    }

    public boolean login(String memId, String password) {
        Optional<Member> memberOptional = findOne(memId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String storedPassword = member.getPw();

            // 입력된 비밀번호를 해싱하여 저장된 비밀번호와 비교
            boolean isPasswordMatch = passwordEncoder.matches(password, storedPassword);

            return isPasswordMatch;
        }

        return false;
    }
}
