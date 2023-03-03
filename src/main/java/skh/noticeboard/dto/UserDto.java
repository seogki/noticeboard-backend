package skh.noticeboard.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDto extends User {

    private Member member;

    public UserDto(Member member, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
