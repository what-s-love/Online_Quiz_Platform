package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Boolean enabled;
}
