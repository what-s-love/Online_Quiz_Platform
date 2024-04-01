package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResultDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate parametrizedTemplate;

    public void create(QuizResult result) {
        String sql = "INSERT INTO quizResults (userId, quizId, score) VALUES (:userId, :quizId, :score)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", result.getUserId());
        parameters.put("quizId", result.getQuizId());
        parameters.put("score", result.getScore());

        parametrizedTemplate.update(sql, new MapSqlParameterSource(parameters));
    }
}
