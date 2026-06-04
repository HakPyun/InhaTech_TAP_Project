package inhatc.project.tap.entity;

import inhatc.project.tap.dto.ShopMenuDto;
import inhatc.project.tap.dto.ShopMenuFormDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="shop_menu")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopMenuEntity {

//    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private ShopEntity shopEntity;

    @Id
    @Column(name="menu")
    private String menu;

    @Column(name="price")
    private String menu_price;

    public static ShopMenuFormDto toShopMenuFormDto(ShopMenuEntity shopMenuEntity) {
        ShopMenuFormDto shopMenuFormDto = new ShopMenuFormDto();
        shopMenuFormDto.setMenu(shopMenuEntity.getMenu());
        shopMenuFormDto.setShop_price(shopMenuEntity.getMenu_price());
        return shopMenuFormDto;
    }
    public static List<ShopMenuDto> toShopMenuFormDtoList(List<ShopMenuEntity> shopMenuEntityList) {
            List<ShopMenuDto> shopMenuFormList=new ArrayList<>();
        for(int value=0;value<shopMenuEntityList.size(); value++) {
            ShopMenuDto shopMenuFormDto = new ShopMenuDto();
            shopMenuFormDto.setMenu(shopMenuEntityList.get(value).getMenu());
            shopMenuFormDto.setMenu_price(shopMenuEntityList.get(value).getMenu_price());
            shopMenuFormList.add(shopMenuFormDto);
        }
        return shopMenuFormList;
    }

}
