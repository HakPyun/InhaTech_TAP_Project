package inhatc.project.tap.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class Review {
    private Long sId;
    private Long rId;//리뷰 아이디
    private Long imgId;
    private String memberId;
    private Double userScore;
    private String userReview;
    private LocalDateTime created_at;
    private String ImgName;
}
