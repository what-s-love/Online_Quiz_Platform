package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionShowDto {
    private Integer id;
    private Integer questionId;
    private String optionText;
    private Boolean isCorrect;
}
