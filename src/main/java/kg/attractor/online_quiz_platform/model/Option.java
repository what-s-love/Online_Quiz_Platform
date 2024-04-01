package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Option {
    private Integer id;
    private Integer questionId;
    private String optionText;
    private Boolean isCorrect;
}
