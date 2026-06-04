package inhatc.project.tap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="review_img")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopReviewImgEntity {

    @Id
    @Column(name="img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long review_img_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private ShopEntity shopEntity;

    @ManyToOne
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    @Column(nullable = false)
    private String review_imgName;

    @Column(nullable = false)
    private String review_oriImgName;


    @Column(nullable = false)
    private String review_imgUrl;

    public void ReviewImg(String review_oriImgName,String review_imgName,String review_imgUrl){
        this.review_imgUrl=review_imgUrl;
        this.review_imgName=review_imgName;
        this.review_oriImgName=review_oriImgName;
    }

}
