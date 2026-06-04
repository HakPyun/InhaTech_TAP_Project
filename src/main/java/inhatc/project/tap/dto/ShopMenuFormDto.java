package inhatc.project.tap.dto;

import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.entity.ShopEntity;
//import inhatc.project.tap.entity.ShopMenuEntity;
import inhatc.project.tap.entity.ShopMenuEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShopMenuFormDto {
    private String member_id;
    private Long sid;
    private String sAddrMain;
    private String sAddrSub;
    private String sName;
    private String cat;
    private String sTell;
    private String bhSignificant;   //영업시간 특이사항
    private String breakSTime;
    private String breakETime;
    private String likeCount;
    private String tips;
    private String sexRecommendation;
    private String ceoIntroduction;

    private String menu;
    private String shop_price;

    //memberEntity member_id매핑
    public MemberEntity getMemberEntity() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMember_id(this.member_id);
        return memberEntity;
    }

    public ShopEntity createItem() {
        ShopEntity shopEntity = modelMapper.map(this, ShopEntity.class);
        shopEntity.setMemberEntity(this.getMemberEntity()); // memberEntity 설정
        return shopEntity;
    }

    private List<ShopImgDto> shopImgDtoList = new ArrayList<>(); // 1. 가게 이미지 dto리스트

    private List<Long> shopImgIds = new ArrayList<>();  		 // 2.

    private static ModelMapper modelMapper = new ModelMapper();

//    public ShopEntity createItem() {
//        return modelMapper.map(this, ShopEntity.class);				 // 3.
//    }

    public static ShopMenuFormDto of(ShopEntity shopEntity) {
        return modelMapper.map(shopEntity, ShopMenuFormDto.class);         // 4.
    }


    private List<ShopMenuDto> shopMenuDtoList = new ArrayList<>(); // 1. 가게 이미지 dto리스트

    private List<Long> shopMenuIds = new ArrayList<>();  		 // 2.
    public ShopMenuEntity createMenu() {
        return modelMapper.map(this, ShopMenuEntity.class);
    }

    public static ShopMenuEntity toShopmenuEntity(ShopMenuFormDto ShopMenuFormDto) {
        ShopMenuEntity shopMenuEntity = new ShopMenuEntity();
        shopMenuEntity.setMenu(ShopMenuFormDto.getMenu());
        shopMenuEntity.setMenu_price(ShopMenuFormDto.getShop_price());
        return shopMenuEntity;
    }
    public static ShopMenuDto of(ShopMenuEntity shopMenuEntity) {
        return modelMapper.map(shopMenuEntity, ShopMenuDto.class);
    }
}
