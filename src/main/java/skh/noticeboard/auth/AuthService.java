package skh.noticeboard.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.MemberSignupRequestDto;
import skh.noticeboard.member.MemberRepository;
import skh.noticeboard.security.JwtTokenProvider;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional

    public JwtToken login(String memberEmail, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberEmail,
                password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Transactional
    public Boolean signup(MemberSignupRequestDto memberSignupRequestDto) {
        if (authRepository.existsByMemberEmail(memberSignupRequestDto.getMemberEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        String encodePwd = passwordEncoder.encode(memberSignupRequestDto.getMemberPassword());
        Member member = Member.builder()
                .memberEmail(memberSignupRequestDto.getMemberEmail())
                .memberName(memberSignupRequestDto.getMemberName())
                .memberNickname(memberSignupRequestDto.getMemberNickname())
                .memberPassword(encodePwd)
                .createDate(new Date())
                .updateDate(new Date())
                .build();
        member.getMemberRole().add("USER");
        authRepository.save(member);
        return true;
    }
}
