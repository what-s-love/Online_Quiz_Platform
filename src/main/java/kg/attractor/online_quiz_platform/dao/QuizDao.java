package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate parametrizedTemplate;

    public int createQuizAndReturnId(Quiz quiz) {
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
}
