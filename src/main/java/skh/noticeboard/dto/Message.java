package skh.noticeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skh.noticeboard.enums.StatusEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private StatusEnum status;
    private String message;
    private Object data;
}
