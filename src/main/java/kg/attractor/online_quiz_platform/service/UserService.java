package kg.attractor.online_quiz_platform.service;


import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.UserCreateDto;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @SneakyThrows
    public void createUser(UserCreateDto userCreateDto) {
        log.info("Creating user");
        // Проверка существования пользователя по email
        if (userDao.isExists(userCreateDto.getEmail())) {
            log.error("User with email " + userCreateDto.getEmail() + " already exists");
            throw new RuntimeException();
        }

        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        userDao.createUser(user);
    }
}
