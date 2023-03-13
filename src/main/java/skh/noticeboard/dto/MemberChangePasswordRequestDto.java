package skh.noticeboard.dto;

import lombok.Data;

@Data
public class MemberChangePasswordRequestDto {
    
    private String memberId;
    private String memberEmail;
    private String memberOldPassword;
    private String memberNewPassword;
    private String memberCheckPassword;
}
