package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionShowDto {
    private Integer id;
    private Integer quizId;
    private String questionText;
}
