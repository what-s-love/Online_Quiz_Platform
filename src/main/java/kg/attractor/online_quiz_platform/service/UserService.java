package kg.attractor.online_quiz_platform.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public int createUserAndReturnId(UserDto userDto) {
        User user = dtoToModel(userDto);
        return userDao.createUserAndReturnId(user);
    }
}
