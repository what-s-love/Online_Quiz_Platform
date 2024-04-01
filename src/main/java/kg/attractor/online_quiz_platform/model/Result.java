package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private Integer id;
    private Integer userId;
    private Integer quizId;
    //0-100
    private double score;
}
