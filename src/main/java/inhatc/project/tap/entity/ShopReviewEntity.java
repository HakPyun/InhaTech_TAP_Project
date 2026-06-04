package inhatc.project.tap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity(name="review")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopReviewEntity {
    //Fk member id
    //기본키 물어봐야함 한 멤버당 하나만 쓰게 할 것 인가??
    //아니면 여러번 쓰게 할 것인가
    @ManyToOne
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long review_id;
    //Fk shop id
    @ManyToOne
    @JoinColumn(name="shop_id")
    private ShopEntity shopEntity;


    @Column(nullable = false,name="shop_user_score")
    private double uScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="shop_user_review")
    private String uReview;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
