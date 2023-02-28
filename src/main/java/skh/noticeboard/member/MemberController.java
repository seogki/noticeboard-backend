package skh.noticeboard.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.MemberLoginRequestDto;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    // @GetMapping("user")
    // public void getUser() {

    // }

    // @PostMapping("user")
    // public MemberDto postUser(String userEmail, String userNickname, String
    // password) {
    // return userService.postUser();
    // }

    @PostMapping("login")
    public JwtToken loginSuccess(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        JwtToken token = memberService.login(memberId, password);
        return token;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
