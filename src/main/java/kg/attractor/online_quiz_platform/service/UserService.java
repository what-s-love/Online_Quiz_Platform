package kg.attractor.online_quiz_platform.service;


import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


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
            throw new NoSuchElementException("Cannot find vacancy with this ID");
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
