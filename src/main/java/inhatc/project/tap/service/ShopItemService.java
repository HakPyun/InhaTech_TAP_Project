package inhatc.project.tap.service;

import inhatc.project.tap.dto.ShopBusinessHourDto;
import inhatc.project.tap.dto.ShopMenuDto;
import inhatc.project.tap.dto.ShopMenuFormDto;
import inhatc.project.tap.entity.BusinessHourEntity;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.entity.ShopImgEntity;
import inhatc.project.tap.entity.ShopMenuEntity;
import inhatc.project.tap.repository.BusinessHourRepository;
import inhatc.project.tap.repository.ShopImgRepository;
import inhatc.project.tap.repository.ShopMenuRepository;
import inhatc.project.tap.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static inhatc.project.tap.entity.QShopEntity.shopEntity;

@Service
@RequiredArgsConstructor
public class ShopItemService {

    private final ShopRepository shopRepository;
    private final ShopMenuRepository shopMenuRepository;
    private final ShopImgService shopImgService;
    private final ShopImgRepository shopImgRepository;
private final BusinessHourRepository businessHourRepository;
private final MemberService memberService;
    public ShopEntity saveShopImage(ShopMenuFormDto shopMenuFormDto, List<MultipartFile> imgFileList) throws Exception {

        //상품 등록

        ShopEntity shopEntity = shopMenuFormDto.createItem();
        shopRepository.save(shopEntity);

        //이미지 등록
        for (int i = 0; i < imgFileList.size(); i++) {
            ShopImgEntity shopImgEntity = new ShopImgEntity();
            shopImgEntity.setShopEntity(shopEntity);

            if (i == 1)
                shopImgEntity.setShop_repImgYn("Y");
            else
                shopImgEntity.setShop_repImgYn("N");

            shopImgService.saveShopItemImg(shopImgEntity, imgFileList.get(i));
        }

        return shopEntity;
    }

    public void saveMenu(List<String> menu,List<String> menu_price,ShopEntity shopEntity) {

        for (int i=0; i<menu.size();i++) {
            ShopMenuEntity menuEntity = new ShopMenuEntity();
            menuEntity.setShopEntity(shopEntity);
            menuEntity.setMenu(menu.get(i));
            menuEntity.setMenu_price(menu_price.get(i));
            shopMenuRepository.save(menuEntity);
        }
    }

    public void saveBusiness(ShopBusinessHourDto shopBusinessHourDto, List<String> dayOff, ShopEntity shopEntity) {
        boolean isWeekend = dayOff.contains("토요일") && dayOff.contains("일요일");
        String closeDays="";
        // 주말인 경우
        if (isWeekend) {
             closeDays="주말";
        } else {
            // 주말이 아닌 경우 문자열 합치기
            closeDays = String.join(",", dayOff);
        }
        BusinessHourEntity businessHour=new BusinessHourEntity();
        businessHour.setShopEntity(shopEntity);
        businessHour.setSTime(shopBusinessHourDto.getShop_start_time());
        businessHour.setETime(shopBusinessHourDto.getShop_end_time());
        businessHour.setSDay(closeDays);
        businessHourRepository.save(businessHour);

    }

    public ShopEntity updateShopImage(ShopMenuFormDto shopMenuFormDto, List<MultipartFile> imgFileList) throws Exception {
        Optional<ShopEntity> bySName = shopRepository.findBySName(shopMenuFormDto.getSName());
        if(bySName.isPresent()) {
            ShopEntity shopEntity = bySName.get();
            shopEntity.setSAddrMain(shopMenuFormDto.getSAddrMain());
            shopEntity.setSAddrSub(shopMenuFormDto.getSAddrSub());
            shopEntity.setCat(shopMenuFormDto.getCat());
            shopEntity.setBhSignificant(shopMenuFormDto.getBhSignificant());
            shopEntity.setSTell(shopMenuFormDto.getSTell());
            shopEntity.setBreakSTime(shopMenuFormDto.getBreakSTime());
            shopEntity.setBreakETime(shopMenuFormDto.getBreakSTime());
            shopEntity.setTips(shopMenuFormDto.getTips());
            shopEntity.setSexRecommendation(shopMenuFormDto.getSexRecommendation());
            shopEntity.setCeoIntroduction(shopMenuFormDto.getCeoIntroduction());
            shopRepository.save(shopEntity);

        //이미지 등록
        for (int i = 0; i < imgFileList.size(); i++) {
            ShopImgEntity shopImgEntity = new ShopImgEntity();
            shopImgEntity.setShopEntity(shopEntity);

            if (i == 1)
                shopImgEntity.setShop_repImgYn("Y");
            else
                shopImgEntity.setShop_repImgYn("N");

            shopImgService.saveShopItemImg(shopImgEntity, imgFileList.get(i));
        }

        return shopEntity;}
        else{
            return null;
        }
    }

//    public void save(ShopMenuFormDto shopMenuFormDto, List<MultipartFile> MenuList)throws Exception {
//        ShopEntity shopEntity = shopMenuFormDto.createMenu();
//        shopRepository.save(shopEntity);
//
//        for (int i = 0; i < MenuList.size(); i++) {
//            ShopMenuEntity shopMenuEntity = new ShopMenuEntity();
//            shopMenuEntity.setShopEntity(shopEntity);
//
//        }
//    }
}