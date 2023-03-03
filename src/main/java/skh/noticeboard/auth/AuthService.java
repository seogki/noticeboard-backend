package skh.noticeboard.auth;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.MemberSignupRequestDto;
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

    public JwtToken login(String memberEmail, String memberPassword) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberEmail,
                memberPassword);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Transactional
    public String signup(MemberSignupRequestDto memberSignupRequestDto) {
        if (authRepository.existsByMemberEmail(memberSignupRequestDto.getMemberEmail())) {
            return "이미 가입되어 있는 유저입니다.";
        }
        if (authRepository.existsByMemberNickname(memberSignupRequestDto.getMemberNickname())) {
            return "이미 존재하는 닉네임입니다.";
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
        return "success";
    }
}
