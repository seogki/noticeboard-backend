package skh.noticeboard.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.UserDto;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        return authRepository.findByMemberEmail(memberEmail)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        // return member;
        // String[] roles = member.getMemberRole().toArray(new String[0]);
        // return User.builder()
        // .username(member.getUsername())
        // .password(member.getPassword())
        // .roles(roles)
        // .build();
        if (member == null) {
            throw new UsernameNotFoundException("유저 정보를 찾을수 없습니다");
        }
        // return member;
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : member.getMemberRole()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return new UserDto(member, member.getMemberEmail(),
                member.getPassword(), grantedAuthorities);
    }

}
