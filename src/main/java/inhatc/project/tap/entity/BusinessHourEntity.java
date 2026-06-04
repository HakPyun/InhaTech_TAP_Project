package inhatc.project.tap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Entity(name="hour")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessHourEntity {
    //Fk shop id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private ShopEntity shopEntity;

    @Id
    @Column(name="hour_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hId;

    @Column(nullable = false,name="shop_start_time")
    private String sTime;

    //휴무일
    @Column(nullable = true,name="shop_closed_day")
    private String sDay;

    @Column(nullable = false,name="shop_end_time")
    private String eTime;



}
