package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.dao.*;
import kg.attractor.online_quiz_platform.dto.*;
import kg.attractor.online_quiz_platform.exception.CategoryNotFoundException;
import kg.attractor.online_quiz_platform.exception.QuizNotFoundException;
import kg.attractor.online_quiz_platform.exception.UserNotFoundException;
import kg.attractor.online_quiz_platform.model.*;
import kg.attractor.online_quiz_platform.model.Category;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizReviews;
import kg.attractor.online_quiz_platform.model.Result;
import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizDao quizDao;
    private final UserDao userDao;
    private final QuestionDao questionDao;
    private final CategoryDao categoryDao;
    private final OptionDao optionDao;
    private final ResultDao resultDao;
    @SneakyThrows
    public List<QuizShowListDto> getAllQuizzes() {
        log.info("Got all quizzes!");
        List<QuizShowListDto> quizShowListDtoList = new ArrayList<>();
        List<Quiz> quizzes = quizDao.getAllQuizzes();

        for (Quiz quiz : quizzes) {
            QuizShowListDto quizShowListDto = QuizShowListDto.builder()
                    .title(quiz.getTitle())
                    .category(categoryDao.getCategoryById(quiz.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Can't find quiz with this category")).getName())
                    .quantity(questionDao.getAmountById(quiz.getId()))
                    .build();
            quizShowListDtoList.add(quizShowListDto);

        }
        return quizShowListDtoList;
    }

    @SneakyThrows
    public void createQuiz(QuizCreateDto quizCreateDto, Authentication authentication) {
        User mayBeUser = getUserFromAuth(authentication.getPrincipal().toString());
    }
    public String getResultByUserId(Long userId){
        List<Result> results = resultDao.getResultsByUserId(userId);
        List<ResultDto> dtos = new ArrayList<>();
        List<Integer> scores = new ArrayList<>(); // Создаем список для хранения баллов

        results.forEach(e -> {
            dtos.add(ResultDto.builder()
                    .quizId(e.getQuizId())
                    .build());
            scores.add((int) e.getScore()); // Добавляем балл в список
        });

        double average = scores.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        String msg = String.format("Общее количество пройденных тестов: %s%n" +
                "Среднее количество баллов:%s%n", dtos.size(),average);

        return msg;

    }

    public void addVoteToQuiz(QuizReviewsDto quizReviewsDto, Long quizId){
        QuizReviews quizReviews = new QuizReviews();
        quizReviews.setEstimation(quizReviewsDto.getEstimation());
        quizReviews.setNumberOfVotes(quizReviewsDto.getNumberOfVotes() + 1);
        quizReviews.setQuizId(quizId);
        quizDao.addVote(quizReviews);
    }
    @SneakyThrows
    public QuizSingleShowDto getQuizById(Integer quizId) {
        Quiz quiz = quizDao.getQuizById(quizId).orElseThrow(() -> new QuizNotFoundException("Can't find quiz with this id: " + quizId));

        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);
        List<QuestionShowDto> questionShowDtoList = new ArrayList<>();
        for (Question question : questions) {
            List<Option> options = optionDao.getOptionsByQuestionId(question.getId());
            List<OptionShowDto> optionShowDtoList = new ArrayList<>();
            for (Option option : options) {
                OptionShowDto optionShowDto = OptionShowDto.builder()
                        .optionText(option.getOptionText())
                        .build();
                optionShowDtoList.add(optionShowDto);
            }

            QuestionShowDto questionShowDto = QuestionShowDto.builder()
                    .questionText(question.getQuestionText())
                    .optionShowDtoList(optionShowDtoList)
                    .build();
            questionShowDtoList.add(questionShowDto);
        }

        return QuizSingleShowDto.builder()
                .title(quiz.getTitle())
                .questionShowDtoList(questionShowDtoList)
                .build();
    }


    @SneakyThrows
    public User getUserFromAuth(String auth) {
        int x = auth.indexOf("=");
        int y = auth.indexOf(",");
        String email = auth.substring(x + 1, y);
        return userDao.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("can't find user with this email"));
    }


    @SneakyThrows
    public void solveQuiz(Integer quizId, AnswerListDto answerListDto, Authentication authentication) {
        log.info("Solving quiz with id: " + quizId);
        int totalQuestions = 0;
        int correctAnswers = 0;

        if (answerListDto.getAnswers().size() != questionDao.getQuestionsByQuizId(quizId).size()) {
            throw new QuizNotFoundException("You should answer to all questions from this quiz");
        }

        for (AnswersDto answerDto : answerListDto.getAnswers()) {
            totalQuestions++;
            List<Option> options = optionDao.getOptionsByQuestionId(answerDto.getQuestionId());
            String selectedAnswer = answerDto.getAnswer();
            boolean isCorrectAnswer = false;

            for (Option option : options) {
                if (option.getIsCorrect() && option.getOptionText().equalsIgnoreCase(selectedAnswer)) {
                    isCorrectAnswer = true;
                    break;
                }
            }
            if (isCorrectAnswer) {
                correctAnswers++;
            }
        }

        double percentageCorrect = ((double) correctAnswers / totalQuestions) * 100;

        User mayBeUser = getUserFromAuth(authentication.getPrincipal().toString());

        Result quizResult = Result.builder()
                .quizId(quizId)
                .userId(mayBeUser.getId())
                .score(percentageCorrect)
                .build();

        resultDao.create(quizResult);
    }
}
