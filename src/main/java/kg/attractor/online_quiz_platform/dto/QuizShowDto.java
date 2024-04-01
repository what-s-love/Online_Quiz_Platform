package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class QuizShowDto {
    private Integer id;
    private String title;
    private String description;
    private String creatorName;
    private String categoryName;
    private Integer questionsCount;
    private Map<QuestionShowDto, List<OptionShowDto>> questions;
}
