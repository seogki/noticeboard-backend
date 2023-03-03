package skh.noticeboard.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;
import skh.noticeboard.dto.Member;
import skh.noticeboard.dto.UserDto;

@Slf4j
public class SecurityUtil {

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Member getCurrentMemberItem() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        UserDto userDto = (UserDto) authentication.getPrincipal();

        return userDto.getMember();
    }
}
