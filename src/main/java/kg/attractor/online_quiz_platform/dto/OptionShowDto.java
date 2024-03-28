package kg.attractor.online_quiz_platform.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionShowDto {
    private int id;
    private int questionId;
    private String optionText;
}
