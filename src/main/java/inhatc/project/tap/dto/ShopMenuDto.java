package inhatc.project.tap.dto;

import inhatc.project.tap.entity.ShopMenuEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopMenuDto {
    private String menu;
    private String menu_price;

    private static ModelMapper modelMapper = new ModelMapper();

    //멤버 변수가 많아지면 상당한 시간을 소모하게 된다.
    //이를 도와주는 라이브러리로 modelmapper
    public static ShopMenuDto of(ShopMenuEntity shopMenuEntity) {
        return modelMapper.map(shopMenuEntity, ShopMenuDto.class);
    }
}
