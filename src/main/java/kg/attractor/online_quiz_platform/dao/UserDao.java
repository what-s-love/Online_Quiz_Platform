package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate parametrizedTemplate;

    public int createUserAndReturnId(User user) {
        String sql = """
                INSERT INTO users (name, email, password, enabled, type)
                values (:name, :email, :password, :enabled, :type);
                """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("enabled", user.getEnabled())
                .addValue("type", user.getType());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        parametrizedTemplate.update(sql, params, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
