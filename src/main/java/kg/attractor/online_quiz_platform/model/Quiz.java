package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz {
    private Integer id;
    private String title;
    private String description;
    private Integer creatorId;
    private Integer categoryId;
}
