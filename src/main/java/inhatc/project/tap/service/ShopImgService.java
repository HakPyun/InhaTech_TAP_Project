package inhatc.project.tap.service;

import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.dto.ShopImgDto;
import inhatc.project.tap.dto.ShopMenuFormDto;
import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.entity.ShopImgEntity;
import inhatc.project.tap.repository.ShopImgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopImgService {

    @Value("${spring.servlet.multipart1.location}")
    String location1;
    @Value("${spring.servlet.multipart.location}")
    String location;

    private final ShopImgRepository shopImgRepository;

    private final ShopFileService shopFileService;

    public void saveShopItemImg(ShopImgEntity shopImgEntity, MultipartFile itemImgFile) throws Exception {
        String shop_oriImgName = itemImgFile.getOriginalFilename();
        String shop_imgName = "";
        String shop_imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(shop_oriImgName)) {
            shop_imgName = shopFileService.uploadFile(location, shop_oriImgName, itemImgFile.getBytes());
            shop_imgUrl = "../img/shopImg/" + shop_imgName;
        }

        // 상품 이미지 정보 저장
        shopImgEntity.ShopImg(shop_oriImgName, shop_imgName, shop_imgUrl);
        shopImgRepository.save(shopImgEntity);
    }

    public List<ShopImgDto> findAll() {
        List<ShopImgEntity> ShopImgList = shopImgRepository.findAll();
        List<ShopImgDto> ShopImgDtoList=new ArrayList<>();
        for(ShopImgEntity shopImgEntity : ShopImgList){
            ShopEntity shopEntity = shopImgEntity.getShopEntity();
            ShopMenuFormDto shopMenuFormDto = ShopMenuFormDto.of(shopEntity);

            ShopImgDto shopImgDto = ShopImgDto.of(shopImgEntity);
            shopImgDto.setShop_id(shopMenuFormDto.getSid());
            ShopImgDtoList.add(shopImgDto);
        }
        return ShopImgDtoList;
    }

    public List<String> findShopImgNameBySid(List<Long> sid) {
        List<String> shopImgNames = shopImgRepository.findShopImgNameBySid(sid);
        return shopImgNames;
    }

    public List<String> findShopImgNameBySid2(List<Long> sid) {
        List<String> shopImgNames = shopImgRepository.findShopImgNameBySid2(sid);
        return shopImgNames;
    }
}
