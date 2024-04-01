package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate template;

    public List<Question> getQuestionsByQuizId(int quizId) {
        String sql = """
                select * from questions where quizId = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Question.class), quizId);
    }

    public Integer getAmountById(Integer id) {
        String sql = """
                select count(*) from questions
                where quizId = ?
                """;
        return template.queryForObject(sql, Integer.class, id);
    }
}
