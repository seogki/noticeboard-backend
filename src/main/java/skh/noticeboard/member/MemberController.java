package skh.noticeboard.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.MemberLoginRequestDto;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
