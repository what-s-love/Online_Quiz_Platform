package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

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

    public Integer createQuestion(Question question) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Создание PreparedStatementCreator для вставки вопроса с возвращением сгенерированного ключа
        PreparedStatementCreator psc = (Connection connection) -> {
            String sql = "insert into questions (quizId, questionText) values (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, question.getQuizId());
            ps.setString(2, question.getQuestionText());
            return ps;
        };

        // Выполнение запроса и получение сгенерированного ключа
        template.update(psc, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
