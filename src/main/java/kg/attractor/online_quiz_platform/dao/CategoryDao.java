package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate template;

    public Optional<Category> getCategoryById(Integer id) {
        String sql = """
                select * from categories where id = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Category.class), id)
                )
        );
    }

    public Optional<Category> getCategoryByName(String name) {
        String sql = """
                select * from categories
                where name like ?
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Category.class), name)
                )
        );
    }
}
