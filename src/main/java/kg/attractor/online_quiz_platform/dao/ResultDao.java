package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResultDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate parametrizedTemplate;

    public void create(Result result) {
        String sql = """
                insert into quizResults (userId, quizId, score)
                values (:userId, :quizId, :score)
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", result.getUserId());
        params.put("quizId", result.getQuizId());
        params.put("score", result.getUserId());
    }
}
