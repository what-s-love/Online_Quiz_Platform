package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz {
    private int id;
    private String title;
    private String description;
    private int creatorId;
    private int categoryId;
}
