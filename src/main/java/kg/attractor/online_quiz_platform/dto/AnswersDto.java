package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswersDto {
    private Integer questionId;
    @NotBlank(message = "answer cannot be blank")
    private String answer;
}
