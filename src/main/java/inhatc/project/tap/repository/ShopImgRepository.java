package inhatc.project.tap.repository;

import inhatc.project.tap.entity.ShopImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopImgRepository extends JpaRepository<ShopImgEntity, Long> {

    List<ShopImgEntity> findByShopEntity_Sid(long sid);


    //문자열은 '(따옴표)로 감싸줘야 인식된다.
    @Query(value="select shop_img_url from shop_img s where s.shop_id in :shop_id and s.shop_rep_img_yn = 'Y'",nativeQuery = true )
    List<String> findShopImgUrlBySid(@Param("shop_id")List<Long> sid);

    @Query(value="select shop_img_name from shop_img s where s.shop_id in :shop_id and s.shop_rep_img_yn = 'Y' ORDER BY s.shop_id ASC",nativeQuery = true )
    List<String> findShopImgNameBySid(@Param("shop_id")List<Long> sid);

    @Query(value="select shop_img_name from shop_img s where s.shop_id = :shop_id",nativeQuery = true )
    List<String> findShopImgNameBySid2(@Param("shop_id")List<Long> sid);
}

