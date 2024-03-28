package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionShowDto {
    private int id;
    private int quizId;
    private String questionText;
}
