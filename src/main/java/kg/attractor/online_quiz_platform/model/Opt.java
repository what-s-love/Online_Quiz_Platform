package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Opt {
    private int id;
    private int questionId;
    private String optionText;
    private Boolean isCorrect;
}
