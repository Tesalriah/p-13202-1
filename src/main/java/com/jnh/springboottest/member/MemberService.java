package com.jnh.springboottest.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(String username, String password, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Member member = new Member(username, passwordEncoder.encode(password), email);

        return this.memberRepository.save(member);
    }

    public Member findByUsername(String username){
        return memberRepository.findByUsername(username).orElseThrow();
    }
}
