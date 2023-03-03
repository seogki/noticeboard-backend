package skh.noticeboard.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skh.noticeboard.dto.Message;
import skh.noticeboard.dto.TrackDto;
import skh.noticeboard.enums.StatusEnum;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/track")
public class TrackController {

    @Autowired
    TrackService trackService;

    @PostMapping(value = "item")
    public ResponseEntity<Message> setItem(@RequestBody TrackDto trackDto) {
        Boolean result = trackService.setItem(trackDto);
        Message message = null;
        if (result) {
            message = Message.builder().status(StatusEnum.OK).message("성공").build();
        } else {
            message = Message.builder().status(StatusEnum.INTERNAL_SERVER_ERROR).message("에러").build();
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "item")
    public ResponseEntity<Message> getItem() {
        return null;
    }

    @DeleteMapping(value = "item/{taskId}")
    public ResponseEntity<Message> deleteItem(@PathVariable String taskId) {
        return null;
    }

    @PutMapping(value = "item/{taskId}")
    public ResponseEntity<Message> putItem(@PathVariable String taskId, @RequestBody TrackDto trackDto) {

        return null;
    }
}
