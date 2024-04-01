package kg.attractor.online_quiz_platform.dao;

import kg.attractor.online_quiz_platform.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate parametrizedTemplate;

    public void createUser(User user) {
        String sql = """
                insert into users(name, email, password, enabled, role_id)
                values (:name, :email, :password, :enabled, :role_id)
                """;
        parametrizedTemplate.update(sql, new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("enabled", true)
                .addValue("role_id", 1)
        );
    }

    public boolean isExists(String email) {
        String sql = """
                select case when exists(select * from users
                where email like ?)
                then true else false end;
                """;
        return Boolean.TRUE.equals(template.queryForObject(sql, Boolean.class, email));
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = """
                select * from users where email like ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), email)
                )
        );
    }

    public Optional<User> getUserById(int id) {
        String sql = """
                select * from users where id = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(User.class), id)
                )
        );
    }
}
