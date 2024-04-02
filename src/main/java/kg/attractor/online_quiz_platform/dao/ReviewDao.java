package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.QuizReviews;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewDao {
    private final JdbcTemplate template;

    public Optional<QuizReviews> getQuizReviewsByQuizIdAndUserId(Integer quizId, Integer userId) {
        String sql = """
                SELECT * FROM quizReviews
                WHERE quizId = ? AND userId = ?
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(QuizReviews.class), quizId, userId)
                )
        );
    }
}
