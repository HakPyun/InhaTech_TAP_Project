package inhatc.project.tap.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity(name="shop_img")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopImgEntity {
    //Fk shop id
    //복합키 shop_id와 자동 수 증가 img_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private ShopEntity shopEntity;


    @Id
    @Column(name="shop_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shop_img_id;

    @Column(nullable = false)
    private String shop_imgName;

    @Column(nullable = false)
    private String shop_oriImgName;


    @Column(nullable = false)
    private String shop_imgUrl;


    private String shop_repImgYn;


    public void ShopImg(String shop_oriImgName, String shop_imgName, String shop_imgUrl) {
        this.shop_oriImgName = shop_oriImgName;
        this.shop_imgName = shop_imgName;
        this.shop_imgUrl = shop_imgUrl;
    }

}
