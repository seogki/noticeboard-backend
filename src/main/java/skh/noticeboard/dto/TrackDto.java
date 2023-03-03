package skh.noticeboard.dto;

import java.util.Date;

import lombok.Data;

@Data
public class TrackDto {

    private Long trackId;
    private String trackTitle;
    private String trackDesc;
    private String trackColor;
    private String dayDate;

}
