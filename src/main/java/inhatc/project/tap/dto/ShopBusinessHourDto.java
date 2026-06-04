package inhatc.project.tap.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ShopBusinessHourDto {
    @NotBlank(message = "오픈 시간은 필수 입력 값입니다.")
    private String shop_start_time;

    @NotBlank(message = "마감시간은 필수 입력 값입니다.")
    private String Shop_end_time;
}
