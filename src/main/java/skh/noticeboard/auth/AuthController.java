package skh.noticeboard.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender.Response;
import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.MemberLoginRequestDto;
import skh.noticeboard.dto.MemberSignupRequestDto;
import skh.noticeboard.dto.Message;
import skh.noticeboard.enums.StatusEnum;
import skh.noticeboard.member.MemberService;
import skh.noticeboard.dto.Member;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("login")
    public ResponseEntity<Message> loginSuccess(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberEmail = memberLoginRequestDto.getMemberEmail();
        String memberPassword = memberLoginRequestDto.getMemberPassword();
        JwtToken token = authService.login(memberEmail, memberPassword);
        Message message = null;
        if (token != null) {
            message = Message.builder().status(StatusEnum.OK).message("성공").data(token).build();
        } else {
            message = Message.builder().status(StatusEnum.INTERNAL_SERVER_ERROR).message("에러").build();
        }
        return ResponseEntity.ok(message);
    }

    @PostMapping("signup")
    public ResponseEntity<Message> signup(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        String result = authService.signup(memberSignupRequestDto);
        Message message = null;
        if (result == "success") {
            message = Message.builder().status(StatusEnum.OK).message("성공").build();
        } else {
            message = Message.builder().status(StatusEnum.INTERNAL_SERVER_ERROR).message(result).build();
        }
        return ResponseEntity.ok(message);
    }
}
