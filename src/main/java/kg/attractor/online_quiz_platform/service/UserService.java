package kg.attractor.online_quiz_platform.service;


import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.exception.UserAlreadyExists;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @SneakyThrows
    public int createUserAndReturnId(UserDto userDto) {
        if (!userDao.getUserByEmail(userDto.getEmail()).isPresent()) {
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder().encode(userDto.getPassword()));
            user.setEnabled(true);
            user.setType("example");
            return userDao.createUserAndReturnId(user);
        } else {
            log.error("Cannot create user");
            throw new UserAlreadyExists("User with this email already exists");
        }
    }



    //////////////////////////////utilMethods//////////////////////////////

    public UserDto modelToDto(User user) {
        UserDto dto;
        dto = (UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.getEnabled())
                .type(user.getType())
                .build());
        return dto;
    }

    public User dtoToModel(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setType(userDto.getType());
        return user;
    }
}
