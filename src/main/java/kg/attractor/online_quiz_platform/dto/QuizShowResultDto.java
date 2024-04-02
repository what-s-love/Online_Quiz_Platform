package kg.attractor.online_quiz_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizShowResultDto {
    private String title;
    private List<ResultQuestionsDto> resultQuestionsDto;
    private Integer questionsCount;
    private double score;
}
