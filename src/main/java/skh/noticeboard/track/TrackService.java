package skh.noticeboard.track;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skh.noticeboard.dto.Track;
import skh.noticeboard.dto.TrackDto;
import skh.noticeboard.utils.SecurityUtil;

@Service
public class TrackService {

    @Autowired
    TrackRepository trackRepository;

    public Boolean setItem(TrackDto trackDto) {
        Long memberId = SecurityUtil.getCurrentMemberItem().getMemberId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date dayDate = null;
        try {
            dayDate = format.parse(trackDto.getDayDate());
        } catch (ParseException e) {
            throw new RuntimeException("날짜 포맷이 잘못되었습니다.");
        }
        Track track = Track.builder()
                .memberId(memberId)
                .trackTitle(trackDto.getTrackTitle())
                .trackDesc(trackDto.getTrackDesc())
                .trackColor(trackDto.getTrackColor())
                .dayDate(dayDate).build();

        Track result = trackRepository.save(track);
        if (result == null) {
            return false;
        }

        return true;
    }

}
