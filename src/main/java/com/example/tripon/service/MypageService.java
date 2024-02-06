package com.example.tripon.service;


import com.example.tripon.domain.Member;
import com.example.tripon.dto.BoardDTO;
import com.example.tripon.dto.CommentDTO;
import com.example.tripon.dto.MemberDTO;
import com.example.tripon.repository.MypageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final MypageRepository repo;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원 정보 조회
    public MemberDTO getUser(String memId) {
        return repo.getUser(memId);
    }

    //회원 정보 수정
    public int infoUpdate(MemberDTO dto) {
        return repo.infoUpdate(dto);
    }

    // 비밀번호 확인
    // memId에 해당하는 비밀번호 입력시 암호화된 비밀번호와 매치되는지 확인 방법2 -> 방법1은 MemberService 구현
    public boolean pwChecked(String memId, String pw) {
        Optional<Member> memberOptional = memberService.findOne(memId);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            String hashedPassword = member.getPw();

            // 입력된 비밀번호와 db에 저장된 비밀번호와 비교하여 true 또는 false 리턴
            return passwordEncoder.matches(pw, hashedPassword);
        }
        return false;
    }

    //비밀번호 변경
    public int pwUpdate(String memId, String changePw) {
        String encodedPassword  = passwordEncoder.encode(changePw);

        // DTO를 도메인 모델로 변환
        Member member = new Member();
        member.setMemId(memId);
        member.setPw(encodedPassword);

        return repo.pwUpdate(member);
    }

    // 작성한 게시글 목록 조회
    public List<BoardDTO> getMyList(String memId, int startIndex, int pageSize) {
        return repo.getMyList(memId, startIndex, pageSize);
    }

    // 작성한 게시글 수 조회
    public int getMyListCount(String memId) {
        return repo.getMyListCount(memId);
    }

    // 작성한 댓글 목록 조회
    public List<CommentDTO> getMyReply(String nick, int startIndex, int pageSize) {
        return repo.getMyReply(nick, startIndex, pageSize);
    }

    // memId를 이용하여 nick 조회
    public String getNick(String memId) {
        return repo.getNick(memId);
    }

    // 작성한 댓글 수 조회
    public int getMyReplyCount(String nick) {
        return repo.getMyReplyCount(nick);
    }
}
