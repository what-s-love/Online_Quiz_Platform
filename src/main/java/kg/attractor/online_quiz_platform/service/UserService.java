package kg.attractor.online_quiz_platform.service;


import kg.attractor.online_quiz_platform.dao.ResultDao;
import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.RatingDto;
import kg.attractor.online_quiz_platform.dto.UserCreateDto;
import kg.attractor.online_quiz_platform.model.Rating;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final ResultDao resultDao;
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

    public List<RatingDto> getRating(){
        log.info("Getting rating");
        List<Rating> ratings = resultDao.getRating();
        List<RatingDto> dtos = new ArrayList<>();
        ratings.forEach(e -> dtos.add(RatingDto.builder()
                .name(e.getName())
                .score(e.getScore())
                .build()));
        return dtos;
    }
}
