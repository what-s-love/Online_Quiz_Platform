package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizDto {
    private Integer id;
    private String title;
    private String description;
    private Integer creatorId;
    private Integer categoryId;
}
