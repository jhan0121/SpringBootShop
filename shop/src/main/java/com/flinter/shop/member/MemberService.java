package com.flinter.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public void register(String username, String password, String displayName) throws Exception {

        Optional<Member> exist = memberRepository.findByUsername(username);

        if (exist.isPresent()) {
            throw new Exception("이미 사용 중인 아이디입니다.");
        }

        Member member = new Member();

        if (username.length() < 5) {
            throw new Exception("아이디의 길이가 5자 이상이어야 합니다.");
        }

        if (password.isEmpty()) {
            throw new Exception("비밀번호를 설정해 주세요");
        } else if (password.length() < 5) {
            throw new Exception("비밀번호 길이를 5자 이상이어야 합니다.");
        }

        if (displayName.isEmpty()) {
            throw new Exception("이름을 설정해 주세요");
        }

        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setDisplayName(displayName);

        memberRepository.save(member);
    }
}
