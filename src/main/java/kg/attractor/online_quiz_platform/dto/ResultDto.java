package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {
    private Integer id;
    private Integer userId;
    private Integer quizId;
    private double score;
}
