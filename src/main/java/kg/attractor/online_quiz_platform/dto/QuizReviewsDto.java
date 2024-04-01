package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizReviewsDto {
    private Long id;
    private Long quizId;
    private int estimation;
    private int numberOfVotes;
}
