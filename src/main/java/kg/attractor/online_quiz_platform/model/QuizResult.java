package kg.attractor.online_quiz_platform.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class QuizResult {
    private Integer id;
    private Integer userId;
    private Integer quizId;
    //0-100
    private double score;
}
