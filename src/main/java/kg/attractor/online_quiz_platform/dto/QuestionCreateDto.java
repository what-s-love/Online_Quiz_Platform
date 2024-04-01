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
public class QuestionCreateDto {
    @NotBlank(message = "Question's text cannot be null or blank")
    private String questionText;

    @Valid
    @Size(min = 4, max = 4)
    private List<OptionCreateDto> optionCreateDtoList;
}
