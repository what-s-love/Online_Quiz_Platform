package kg.attractor.online_quiz_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizShowListDto {
    private String title;
    private String category;
    private Integer quantity;
}
