package inhatc.project.tap.repository;

import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.entity.ShopMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopMenuRepository extends JpaRepository<ShopMenuEntity,Long>{

//    @Query(value="select * from shop_menu s where s.shop_id = %:s_id%",nativeQuery = true )
//    List<ShopMenuEntity> findByMenu_All(@Param("s_id")Long sId);
//    List<ShopMenuEntity> findByShopEntity(ShopEntity shopEntity);


    List<ShopMenuEntity> findByShopEntity_Sid(long sid);


}
