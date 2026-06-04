package inhatc.project.tap.repository;

import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity,Long>, QuerydslPredicateExecutor<ShopEntity> {
    Optional<ShopEntity> findBySid(Long sid);

    List<ShopEntity> findByMemberEntity(MemberEntity member);
    @Query(value="select * from shop s where s.shop_name like :shop_name",nativeQuery = true )
    Optional<ShopEntity> findBySName(@Param("shop_name")String sName);

    @Query(value="select shop_id from shop s where s.shop_category = :shop_category",nativeQuery = true )
    List<Long> findByCat(@Param("shop_category")String cat);

    @Query(value="select shop_name from shop s where s.shop_id in :shop_id ORDER BY s.shop_id ASC",nativeQuery = true )
    List<String> findSNameBySid(@Param("shop_id")List<Long> sid);

    @Query(value="select shop_id from shop s ORDER BY shop_id ASC",nativeQuery = true )
    List<Long> findShopIdByAll();

    @Query(value="select shop_id from shop s where s.shop_name = :shop_name",nativeQuery = true )
    List<Long> findShopIdByShopName(@Param("shop_name")String shopName);
}
