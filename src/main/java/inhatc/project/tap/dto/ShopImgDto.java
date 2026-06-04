package inhatc.project.tap.dto;

import inhatc.project.tap.entity.ShopImgEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopImgDto {

    private Long shop_img_id;

    private String shop_imgName;

    private String shop_oriImgName;

    private String shop_imgUrl;

    private Long shop_id;

    private String shop_repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    //멤버 변수가 많아지면 상당한 시간을 소모하게 된다.
    //이를 도와주는 라이브러리로 modelmapper
    public static ShopImgDto of(ShopImgEntity shopImgEntity) {
        return modelMapper.map(shopImgEntity, ShopImgDto.class);
    }
}