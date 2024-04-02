package kg.attractor.online_quiz_platform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // запрос для выборки пользователя
        String fetchUsersQuery = "select email, password, enabled\n" +
                "from users\n" +
                "where email = ?;";

//         запрос для ролей пользователя
        String fetchRolesQuery = "select email, role\n" +
                "from users u,\n" +
                "     roles r\n" +
                "where u.email = ?\n" +
                "  and u.role_id = r.id;";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(fetchUsersQuery)
                .authoritiesByUsernameQuery(fetchRolesQuery)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy
                        .STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/quizzes").fullyAuthenticated()
                        .requestMatchers(HttpMethod.GET,"users/**").fullyAuthenticated()
                        .requestMatchers(HttpMethod.GET,"users/leaderboard").permitAll()

//                        .requestMatchers(HttpMethod.POST, "/accounts/avatar").fullyAuthenticated()
//
//                        .requestMatchers(HttpMethod.GET, "/resumes/applicant").hasAuthority("APPLICANT")
//                        .requestMatchers(HttpMethod.POST, "/resumes").hasAuthority("APPLICANT")
//                        .requestMatchers(HttpMethod.PUT, "/resumes/**").hasAuthority("APPLICANT")
//                        .requestMatchers(HttpMethod.GET, "/resumes/**").hasAuthority("EMPLOYER")
//                        .requestMatchers(HttpMethod.DELETE, "/resumes/**").hasAuthority("APPLICANT")
//
//                        .requestMatchers(HttpMethod.GET, "/vacancies/employer").hasAuthority("EMPLOYER")
//                        .requestMatchers(HttpMethod.GET, "/vacancies/{vacancyId}/respondents").hasAuthority("EMPLOYER")
//                        .requestMatchers(HttpMethod.GET, "/vacancies//{id}/responded-vacancies").hasAuthority("EMPLOYER")
//                        .requestMatchers(HttpMethod.POST, "/vacancies").hasAuthority("EMPLOYER")
//                        .requestMatchers(HttpMethod.PUT, "/vacancies/**").hasAuthority("EMPLOYER")
//                        .requestMatchers(HttpMethod.GET, "/vacancies/**").hasAuthority("APPLICANT")

                        .anyRequest().permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler()));
        ;
        return http.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");

            Map<String, Object> data = new HashMap<>();
            data.put("error", "Недостаточно прав для доступа к этому ресурсу");

            response.getWriter().write(new ObjectMapper().writeValueAsString(data));
        };
    }
}
