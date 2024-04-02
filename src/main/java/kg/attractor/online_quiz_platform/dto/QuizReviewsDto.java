package kg.attractor.online_quiz_platform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizReviewsDto {
    @Min(1)
    @Max(5)
    private Integer estimation;
}
