package kg.attractor.online_quiz_platform.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate template;

    public List<Option> getOptionsByQuestionId(int questionId) {
        String sql = """
            select * from questions where quizId = ?;
            """;
        return template.query(sql, new BeanPropertyRowMapper<>(Option.class), questionId);
    }
}