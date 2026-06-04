package inhatc.project.tap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewDto {
    private long sId;
    private String mId;
    @NotNull(message = "별점은 필수입니다.")
    private double uScore;
    private String uReview;

}
