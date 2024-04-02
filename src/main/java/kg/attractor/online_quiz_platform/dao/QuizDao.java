package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Quiz;
import kg.attractor.online_quiz_platform.model.QuizReviews;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate parametrizedTemplate;

    public List<Quiz> getAllQuizzes() {
        String sql = """
                select * from quizzes;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Quiz.class));
    }


    public Optional<Quiz> getQuizById(int id) {
        String sql = """
                select * from quizzes where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Quiz.class), id)
                )
        );
    }


    public Integer createQuizAndReturnId(Quiz quiz) {
        String sql = """
                INSERT INTO quizzes (title, description, creatorId, categoryId)
                values (:title, :description, :creatorId, :categoryId);
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", quiz.getTitle())
                .addValue("description", quiz.getDescription())
                .addValue("creatorId", quiz.getCreatorId())
                .addValue("categoryId", quiz.getCategoryId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        parametrizedTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void addVote(QuizReviews quizReviews) {
        String sql = """
                INSERT INTO QuizReviews (QUIZID, USERID, ESTIMATION)
                VALUES (:quizId, :userId, :estimation)
                 """;
        parametrizedTemplate.update(sql, new MapSqlParameterSource()
                .addValue("quizId", quizReviews.getQuizId())
                .addValue("userId", quizReviews.getUserId())
                .addValue("estimation", quizReviews.getEstimation()));
    }

}
