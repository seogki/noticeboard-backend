package skh.noticeboard.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    // private final Key key;

    // public TokenProvider(@Value("${jw.secret}") String secretKey) {
    // byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    // this.key = Keys.hmacShaKeyFor(keybytes);
    // }
}
