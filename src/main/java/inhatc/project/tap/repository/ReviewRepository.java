package inhatc.project.tap.repository;

import inhatc.project.tap.entity.ShopReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ShopReviewEntity,Long> {

    List<ShopReviewEntity> findByMemberEntity_Id(String mId);

    @Query(value="select review_id from review r where r.shop_id in :shop_id ORDER BY r.shop_id ASC",nativeQuery = true )
    List<Long> findReviewIdByShopId(@Param("shop_id")List<Long> sid);

    @Query(value="select member_id from review r where r.review_id in :review_id ORDER BY r.review_id ASC",nativeQuery = true )
    List<String> findMemberIdByReviewId(@Param("review_id")List<Long> rid);

    @Query(value="select shop_user_score from review r where r.review_id in :review_id ORDER BY r.review_id ASC",nativeQuery = true )
    List<Double> findShopScoreByReviewId(@Param("review_id")List<Long> rid);

    @Query(value="select shop_user_review from review r where r.review_id in :review_id ORDER BY r.review_id ASC",nativeQuery = true )
    List<String> findShopReviewByReviewId(@Param("review_id")List<Long> rid);

    @Query(value="select created_at from review r where r.review_id in :review_id ORDER BY r.review_id ASC",nativeQuery = true )
    List<Timestamp> findReviewCreatedByReviewId(@Param("review_id")List<Long> rid);
}
