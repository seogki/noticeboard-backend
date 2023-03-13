package skh.noticeboard.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.MemberLoginRequestDto;
import skh.noticeboard.dto.Message;
import skh.noticeboard.dto.UserDto;
import skh.noticeboard.enums.StatusEnum;
import skh.noticeboard.utils.SecurityUtil;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/test")
    public String test(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        return "success";
    }

    @GetMapping("/member")
    public ResponseEntity<Message> getMember() {
        Member member = SecurityUtil.getCurrentMemberItem();
        Message message = null;
        if(member == null) {
            message = Message.builder().status(StatusEnum.INTERNAL_SERVER_ERROR).message("해당하는 유저가 존재하지않습니다.").build();
        } else {
            member.setMemberPassword(null);
            message = Message.builder().status(StatusEnum.OK).message("성공").data(member).build();
        } 
        return ResponseEntity.ok(message);
    }
}
