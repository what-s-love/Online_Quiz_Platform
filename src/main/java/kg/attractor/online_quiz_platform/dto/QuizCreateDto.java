package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreateDto {
    @NotBlank(message = "Quiz's title cannot be null or blank")
    private String title;

    @NotBlank(message = "Quiz's description cannot be null or blank")
    private String description;

    @NotBlank(message = "Quiz's category cannot be null or blank")
    private String category;

    @Valid
    @Size(min = 1, max = 12)
    private List<QuestionCreateDto> questionCreateDtoList;
}
