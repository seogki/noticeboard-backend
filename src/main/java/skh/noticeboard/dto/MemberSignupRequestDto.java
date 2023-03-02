package skh.noticeboard.dto;

import lombok.Data;

@Data
public class MemberSignupRequestDto {

    private String memberName;
    private String memberEmail;
    private String memberNickname;
    private String memberPassword;
}
