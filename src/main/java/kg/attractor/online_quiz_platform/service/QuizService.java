package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dao.CategoryDao;
import kg.attractor.online_quiz_platform.dao.OptionDao;
import kg.attractor.online_quiz_platform.dao.QuestionDao;
import kg.attractor.online_quiz_platform.dao.QuizDao;
import kg.attractor.online_quiz_platform.dao.UserDao;
import kg.attractor.online_quiz_platform.dto.OptionShowDto;
import kg.attractor.online_quiz_platform.dto.QuestionShowDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizShowDto;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;
import kg.attractor.online_quiz_platform.model.Category;
import kg.attractor.online_quiz_platform.model.Opt;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizDao quizDao;
    private final UserDao userDao;
    private final QuestionDao questionDao;
    private final CategoryDao categoryDao;
    private final OptionDao optionDao;

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

    @SneakyThrows
    public String getUsernameById(int userId) {
        User user = userDao.getUserById(userId).orElseThrow(() ->new UserNotFoundException("Can not find user with this ID"));
        return user.getName();
    }

    public String getCategoryById(int categoryId) {
        Category category = categoryDao.getCategoryById(categoryId).orElseThrow(() -> new NoSuchElementException("Category with this ID not found"));
        return category.getName();
    }

    public int getQuestionsCountByQuizId(int quizId) {
        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);
        return questions.size();
    }
    @SneakyThrows
    public List<QuizShowDto> getQuizzes() {
        return modelsToDtos(quizDao.getQuizzes());
    }

    @SneakyThrows
    public QuizShowDto getQuizById(int id, Authentication auth) {
        if (auth != null) {
            Quiz quiz = quizDao.getQuizById(id).orElseThrow(() -> new NoSuchElementException("Cannot find quiz"));
            return modelToDto(quiz);
        } else {
            log.error("User not found");
            throw new UserNotFoundException("Cannot find your authentication");
        }
    }


    public Map<QuestionShowDto, List<OptionShowDto>> getQuestionsByQuizId(int quizId) {
        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);
        Map<QuestionShowDto, List<OptionShowDto>> map = new HashMap<>();

        for (Question question : questions) {
            QuestionShowDto questionDto = QuestionShowDto.builder()
                    .id(question.getId())
                    .quizId(question.getQuizId())
                    .questionText(question.getQuestionText())
                    .build();

            List<Opt> options = optionDao.getOptionsByQuestionId(question.getId());
            List<OptionShowDto> optionDtos = new ArrayList<>();
            for (Opt option : options) {
                OptionShowDto optionDto = OptionShowDto.builder()
                        .id(option.getId())
                        .questionId(option.getQuestionId())
                        .optionText(option.getOptionText())
                        .build();
                optionDtos.add(optionDto);
            }

            map.put(questionDto, optionDtos);
        }

        return map;
    }


    public List<QuizShowDto> modelsToDtos(List<Quiz> quizzes) {
        List<QuizShowDto> dtos = new ArrayList<>();
        quizzes.forEach(e -> dtos.add(QuizShowDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .creatorName(getUsernameById(e.getCreatorId()))
                .categoryName(getCategoryById(e.getCategoryId()))
                .questionsCount(getQuestionsCountByQuizId(e.getId()))
                .questions(getQuestionsByQuizId(e.getId()))
                .build()));
        return dtos;
    }

    public QuizShowDto modelToDto(Quiz quiz) {
        QuizShowDto dto;
        dto = QuizShowDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .creatorName(getUsernameById(quiz.getCreatorId()))
                .categoryName(getCategoryById(quiz.getCategoryId()))
                .questionsCount(getQuestionsCountByQuizId(quiz.getId()))
                .questions(getQuestionsByQuizId(quiz.getId()))
                .build();
        return dto;
    }
}
