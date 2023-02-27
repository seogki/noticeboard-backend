package skh.noticeboard.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skh.noticeboard.dto.User;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User postUser() {
        return new User();
    }
}
