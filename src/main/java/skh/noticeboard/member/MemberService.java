package skh.noticeboard.member;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import skh.noticeboard.dto.JwtToken;
import skh.noticeboard.dto.Member;
import skh.noticeboard.security.JwtTokenProvider;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    // @Autowired
    // MemberRepository userRepository;

    // public UserService(BCryptPasswordEncoder encoder,
    // AuthenticationManagerBuilder authenticationManagerBuilder,
    // JwtTokenProvider jwtTokenProvider) {
    // this.encoder = encoder;
    // this.authenticationManagerBuilder = authenticationManagerBuilder;
    // this.jwtTokenProvider = jwtTokenProvider;
    // }

    public Member postUser() {
        return new Member();
    }

    @Transactional
    public JwtToken login(String memberId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId,
                password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}