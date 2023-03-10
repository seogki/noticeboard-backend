package skh.noticeboard.auth;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.MemberMailDto;
import skh.noticeboard.dto.MemberResetPasswordRequestDto;
import skh.noticeboard.dto.MemberSignupRequestDto;
import skh.noticeboard.security.JwtTokenProvider;
import skh.noticeboard.utils.StringUtil;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    
    private final JavaMailSender mailSender;
    private final AuthRepository authRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
 
    private static final String FROM_ADDRESS = "skhmailweb@gmail.com";
 
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

    @Transactional
    public String resetPassword(MemberResetPasswordRequestDto memberResetPasswordRequestDto) {

        if(!authRepository.existsByMemberEmail(memberResetPasswordRequestDto.getMemberEmail())) {
            return "존재하지않은 계정입니다.";
        }

        return authRepository.findByMemberEmail(memberResetPasswordRequestDto.getMemberEmail())
                .map(this::changePassword)
                .map(this::sendMail)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

    } 

    private MemberMailDto changePassword(Member member) {
        String generatedString = StringUtil.generateRandomString(7);
        String encodePwd = passwordEncoder.encode(generatedString);
        member.setMemberPassword(encodePwd);  

        MemberMailDto memberMailDto = new MemberMailDto();
        memberMailDto.setMemberEmail(member.getMemberEmail());
        memberMailDto.setMemberTempPassword(generatedString);
        authRepository.save(member);
        return memberMailDto;
    }

    private String sendMail(MemberMailDto memberMailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(memberMailDto.getMemberEmail());
        message.setFrom(FROM_ADDRESS);
        message.setSubject("[SCalendar] 계정 임시변경 패스워드");
        message.setText("테스트 임시 비밀번호 " + memberMailDto.getMemberTempPassword());
        mailSender.send(message);
  
        return "success";
    } 
} 
