package inhatc.project.tap.service;

import inhatc.project.tap.dto.ShopMenuDto;
import inhatc.project.tap.dto.ShopMenuFormDto;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.entity.ShopMenuEntity;
import inhatc.project.tap.repository.ShopImgRepository;
import inhatc.project.tap.repository.ShopMenuRepository;
import inhatc.project.tap.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Component
public class ShopMenuService {

    private final ShopRepository shopRepository;
    @Autowired
    private  ShopMenuRepository shopMenuRepository;

    private final ShopImgService shopImgService;
    private final ShopImgRepository shopImgRepository;

    public List<ShopMenuDto> find_menus(long sId) {
        List<ShopMenuEntity> menuEntities = shopMenuRepository.findByShopEntity_Sid(sId);
        log.info("sid-----------<<<DS<D<S<D<"+String.valueOf(menuEntities));
        return ShopMenuEntity.toShopMenuFormDtoList(menuEntities);
    }


//    public void save(ShopMenuFormDto shopMenuFormDto){
//       ShopMenuEntity shopMenuEntity = ShopMenuEntity.toShopMenuFormDto(shopMenuFormDto);
//        shopMenuRepository.save(shopMenuEntity);
//    }
}