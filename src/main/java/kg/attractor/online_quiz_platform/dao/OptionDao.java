package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate template;

    public List<Question> getOptionsByQuestionId(int questionId) {
        String sql = """
            select * from questions where quizId = ?;
            """;
        return template.query(sql, new BeanPropertyRowMapper<>(Question.class), questionId);
    }
}