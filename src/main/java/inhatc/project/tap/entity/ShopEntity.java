package inhatc.project.tap.entity;

//import inhatc.project.tap.dto.ShopDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="shop")
@Setter
@Getter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopEntity {
    //Fk member id
    @ManyToOne
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    //Pk shop id
    @Id
    @Column(name="shop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sid;

    @Column(unique = true,
            nullable = false,
            name="shop_name")
    private String sName;

    @Column(
            nullable = false,
            name="shop_address_main")
    private String sAddrMain;

    @Column(nullable = false,
            name="shop_address_sub")
    private String sAddrSub;

    @Column(nullable = false,
            name="shop_category")
    private String cat;

    @Column(nullable = false,
            name="shop_bh_significant")
    private String bhSignificant;   //영업시간 특이사항

    @Column(nullable = false,
            name="shop_tell")
    private String sTell;

    @Column(name="shop_breakSTime")
    private String breakSTime;

    @Column(name="shop_breakETime")
    private String breakETime;

    @Column(name="shop_likeCount")
    private String likeCount;

    @Column(name="shop_tips")
    private String tips;

    @Column(name="shop_sexRecommendation")
    private String sexRecommendation;

    @Column(name="shop_ceoIntroduction")
    private String ceoIntroduction;

    @OneToMany(mappedBy = "shopEntity",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private final List<ShopImgEntity> shopImgEntities=new ArrayList<>();

    @OneToMany(mappedBy = "shopEntity",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private final List<ShopLikeEntity> shopLikeEntities=new ArrayList<>();

    @OneToMany(mappedBy = "shopEntity",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private final List<ShopMenuEntity> shopMenuEntityList=new ArrayList<>();

    @OneToMany(mappedBy = "shopEntity",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private final List<ShopReviewEntity> shopReviewEntities=new ArrayList<>();

    @OneToOne(mappedBy = "shopEntity",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private  BusinessHourEntity businessHourEntities;
    @OneToMany(mappedBy = "shopEntity",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private  final List<ShopReviewImgEntity> shopReviewImgEntity=new ArrayList<>();
//    public static ShopEntity toShop(ShopDTO shopDTO){
//        ShopEntity shopEntity=new ShopEntity();
//        shopEntity.setSName(shopDTO.getSName());
//        shopEntity.setSAddrMain(shopDTO.getSAddrMain());
//        shopEntity.setSAddrSub(shopDTO.getSAddrSub());
//        shopEntity.setCat(shopDTO.getCat());
//        shopEntity.setBhSignificant(shopDTO.getBhSignificant());
//        shopEntity.setSTell(shopDTO.getSTell());
//        shopEntity.setBreakSTime(shopDTO.getBreakSTime());
//        shopEntity.setBreakETime(shopDTO.getBreakETime());
//        shopEntity.setLikeCount(shopDTO.getLikeCount());
//        shopEntity.setTips(shopDTO.getTips());
//        shopEntity.setSexRecommendation(shopDTO.getSexRecommendation());
//        shopEntity.setCeoIntroduction(shopDTO.getCeoIntroduction());
//        return shopEntity;
//    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
