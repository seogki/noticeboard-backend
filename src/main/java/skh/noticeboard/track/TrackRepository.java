package skh.noticeboard.track;

import org.springframework.data.jpa.repository.JpaRepository;

import skh.noticeboard.dto.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
