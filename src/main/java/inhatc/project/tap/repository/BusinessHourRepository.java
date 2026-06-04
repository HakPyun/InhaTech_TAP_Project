package inhatc.project.tap.repository;

import inhatc.project.tap.entity.BusinessHourEntity;
import inhatc.project.tap.entity.ShopMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessHourRepository extends JpaRepository<BusinessHourEntity,Long> {

    Optional<BusinessHourEntity> findByShopEntity_Sid(long sid);


}
