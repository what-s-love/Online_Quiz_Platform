package kg.attractor.online_quiz_platform.model;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    private Integer id;
    private Integer userId;
    private Integer quizId;
    //0-100
    private Double score;
}
