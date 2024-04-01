package kg.attractor.online_quiz_platform.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionCreateDto {
    @NotBlank(message = "Option's text cannot be null or blank")
    private String optionText;
    @NotNull
    private Boolean isCorrect;
}
