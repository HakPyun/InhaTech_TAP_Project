package inhatc.project.tap.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class ShopInfo {
    private Long shopId;
    private String shopName;
    private String shopImageName;
//    private String imageUrl;
}
