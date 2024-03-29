package com.example.tripon.service;

import com.example.tripon.domain.Member;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.repository.MemberRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private static final long CODE_EXPIRATION_TIME = 5 * 60 * 1000; // 5분

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

    // 이메일 중복 확인
    public String checkEmail(String email) {
        return repo.checkEmail(email);
    }

    // 인증 코드 생성
    public String createCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomAuthCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            // secureRandom.nextInt(3)는 0~2 까지 정수 중 랜덤
            int randomNum = secureRandom.nextInt(3);

            if (randomNum == 0) {
                // 무작위 대문자
                randomAuthCode.append((char) (secureRandom.nextInt(26) + 'A'));
            } else if (randomNum == 1) {
                // 무작위 소문자
                randomAuthCode.append((char) (secureRandom.nextInt(26) + 'a'));
            } else {
                // 무작위 숫자
                randomAuthCode.append(secureRandom.nextInt(10));
            }
        }
        return randomAuthCode.toString();
    }

    // 인증 코드 이메일로 보내기
    public void createMail(String email, String authCode) {
        try {
            // 메일 내용 및 구조 생성
            MimeMessage message = javaMailSender.createMimeMessage();
            // 메일 내용 및 구조 커스텀
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 발신자 주소 설정
            String mail = "TripOn@tripon.com";
            helper.setFrom(mail);
            // 수신자 주소 설정
            helper.setTo(email);
            // 메일 제목 설정
            helper.setSubject("TripOn 회원가입 인증 이메일 입니다.");
            // 메일 내용 설정
            String mailContent = "인증 코드: " + authCode;
            helper.setText(mailContent);
            // 메일 발송
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String checkCode(String authCode, Long authCodeTime, String code) {
        // 현재 시간 확인
        long currentTime = System.currentTimeMillis();
        if (currentTime - authCodeTime <= CODE_EXPIRATION_TIME) {
            if (authCode.equals(code)) {
                // 인증 성공
                return "success";
            } else {
                // 인증 실패 (인증 코드 불일치)
                return "failure";
            }
        } else {
            // 인증 코드 만료
            return "expiration";
        }
    }
}
