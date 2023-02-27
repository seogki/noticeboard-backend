package skh.noticeboard.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import skh.noticeboard.dto.User;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user")
    public void getUser() {

    }

    @PostMapping("user")
    public User postUser(String userName, String userEmail, String password) {
        return userService.postUser();
    }
}
