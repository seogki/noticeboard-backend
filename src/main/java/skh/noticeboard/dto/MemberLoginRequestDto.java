package skh.noticeboard.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String memberEmail;
    private String memberPassword;
}