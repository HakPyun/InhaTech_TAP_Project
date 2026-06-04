package inhatc.project.tap.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class IndexDto {
    private List<ShopInfo> sakeShopInfo;
    private List<ShopInfo> foodShopInfo;
    private List<ShopInfo> coffeeShopInfo;
}
