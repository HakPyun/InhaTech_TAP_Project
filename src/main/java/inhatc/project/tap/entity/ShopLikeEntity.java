package inhatc.project.tap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="shop_like")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopLikeEntity {

    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name="shop_id")
    private ShopEntity shopEntity;

}
