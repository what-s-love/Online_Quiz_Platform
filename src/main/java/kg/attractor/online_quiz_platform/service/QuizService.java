package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dao.QuizDao;
import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizDao quizDao;
    private final UserDao userDao;

    public int createQuizAndReturnId(QuizDto quizDto, Authentication auth) {
        User user = getUserByAuth(auth);
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setCreatorId(user.getId());
        quiz.setCategoryId(quizDto.getCategoryId());
        return quizDao.createQuizAndReturnId(quiz);
    }

    ///////////////////////////////utilMethods//////////////////////////////////

    @SneakyThrows
    public User getUserByAuth(Authentication auth) {
        String authStr = auth.getPrincipal().toString();
        int x = authStr.indexOf("=");
        int y = authStr.indexOf(",");
        String email = authStr.substring(x + 1, y);
        return userDao.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Can not find user"));
    }
}
