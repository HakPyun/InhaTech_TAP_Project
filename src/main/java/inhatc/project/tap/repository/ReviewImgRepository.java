package inhatc.project.tap.repository;

import inhatc.project.tap.entity.ShopReviewImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewImgRepository extends JpaRepository<ShopReviewImgEntity,Long> {

    @Query(value="select img_id from review_img r where r.shop_id = :shop_id",nativeQuery = true )
    List<Long> findImgIdByShopId(@Param("shop_id")List<Long> sid);

    @Query(value="select review_img_name from review_img r where r.img_id in :img_id",nativeQuery = true )
    List<String> findImgNameByImgId(@Param("img_id")List<Long> imgId);
}
