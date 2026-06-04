package inhatc.project.tap.controller;

//import inhatc.project.tap.dto.ShopDTO;
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
import inhatc.project.tap.service.MemberService;
import inhatc.project.tap.service.ShopItemService;
import inhatc.project.tap.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ShopMenuImgController {
    private final ShopRepository shopRepository;
    private final ShopMenuRepository shopMenuRepository;
    private final ShopItemService shopItemService;
    private final MemberService memberService;
    private ShopService shopService;
    private final ShopImgRepository shopImgRepository;
    private final BusinessHourRepository businessHourRepository;
    //    @GetMapping("/shop/{sid}}")
//    public String findBySid(@PathVariable Long sid, Model model){
//        ShopDTO shopDTO= shopService.findBySid(sid);
//        model.addAttribute("shop",shopDTO);
//        return "shop_detail";
//    }
    @GetMapping("/shopregist")
    public String shopmenuimgForm(Model model,@RequestParam(name = "errorMessage", required = false) String errorMessage) {
        String nickname = memberService.getNickname();
        String userId = memberService.getAuthenticatedId();
        model.addAttribute("userId", userId);
        model.addAttribute("shopMenuFormDto", new ShopMenuFormDto());
        model.addAttribute("hour",new ShopBusinessHourDto());
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "ShopInsertForm";
    }

    @PostMapping("/shopregist")
    public String insertShopData(@Validated ShopMenuFormDto shopMenuFormDto, BindingResult bindingResult, Model model
            , ShopBusinessHourDto shopBusinessHourDto, @RequestParam(value = "dayOff", required = false) List<String> dayOff,
                                 @RequestParam("itemImgFile") List<MultipartFile> imgFileList, @RequestParam("menu") List<String> menu,
                                 @RequestParam("menu_price") List<String> menuPrice) {
        String authenticatedId = memberService.getAuthenticatedId();
        shopMenuFormDto.setMember_id(authenticatedId);
        if(menu.isEmpty()&&menuPrice.isEmpty()) {
            model.addAttribute("errorMessage","메뉴 필수");
            return "/ShopInsertForm";
        }

        if (bindingResult.hasErrors()) {
            return "/ShopInsertForm";
        }
        if(shopBusinessHourDto.getShop_start_time()==null&&shopBusinessHourDto.getShop_end_time()==null){
        model.addAttribute("errorMessage","영업시간 필수 입력값입니다.");

        }
        if (imgFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage","이미지 등록 필수");

            return "/ShopInsertForm";
        }

        try {
            Iterator<MultipartFile> iterator = imgFileList.iterator();
            while (iterator.hasNext()) {
                String element = String.valueOf(iterator.next());
                if (element == null || element.isEmpty()) {
                    iterator.remove();
                }
            }
            Iterator<String> menuiterator = menu.iterator();
            while (iterator.hasNext()) {
                String element = String.valueOf(iterator.next());
                if (element == null || element.isEmpty()) {
                    iterator.remove();
                }
            }
            Iterator<String> priceiterator = menuPrice.iterator();
            while (iterator.hasNext()) {
                String element = String.valueOf(iterator.next());
                if (element == null || element.isEmpty()) {
                    iterator.remove();
                }
            }


            ShopEntity shopEntity = shopItemService.saveShopImage(shopMenuFormDto, imgFileList);
            shopItemService.saveMenu(menu, menuPrice, shopEntity);
            shopItemService.saveBusiness(shopBusinessHourDto,dayOff,shopEntity);

            //            shopItemService.save(shopMenuFormDto);
        } catch (Exception e) {
            log.error("this is an error =====>"+e);
//            error="메뉴를 등록중 에러 발생";
//            String encodeError= URLEncoder.encode(error, StandardCharsets.UTF_8);
//            return "redirect:/shopregist?errorMessage="+encodeError;
            model.addAttribute("errorMessage",e.getMessage());
            return "/ShopInsertForm";
        }

        return "redirect:/";
    }


    @PostMapping("/shop/update")
    public String updateShop(@Validated ShopMenuFormDto shopMenuFormDto, BindingResult bindingResult, Model model
            , ShopBusinessHourDto shopBusinessHourDto, @RequestParam(value = "dayOff", required = false) List<String> dayOff,
                                 @RequestParam("itemImgFile") List<MultipartFile> imgFileList, @RequestParam("menu") List<String> menu,
                                 @RequestParam("menu_price") List<String> menuPrice) {
        String error=null;
        String encodeSName=URLEncoder.encode(shopMenuFormDto.getSName(),StandardCharsets.UTF_8);
        if(menu.isEmpty()&&menuPrice.isEmpty()) {
            error="메뉴는 필수 입니다.";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/update/"+encodeSName+"?errorMessage="+encodeError;
        }
        if (bindingResult.hasErrors()) {
            error="알수 없는 에러.";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/update/"+encodeSName+"?errorMessage="+encodeError;

        }
        if(shopBusinessHourDto.getShop_start_time()==null&&shopBusinessHourDto.getShop_end_time()==null){
            error="영업시간 필수.";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/update/"+encodeSName+"?errorMessage="+encodeError;

        }
        if (imgFileList.get(0).isEmpty()) {
            error="이미지 등록 필수";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/update/"+encodeSName+"?errorMessage="+encodeError;

        }

        try {
            Optional<ShopEntity> bySName = shopRepository.findBySName(shopMenuFormDto.getSName());
            if(bySName.isEmpty()){
                return "redirect:/";
            }
            List<ShopImgEntity> imgList = shopImgRepository.findByShopEntity_Sid(bySName.get().getSid());
            if(!imgList.isEmpty()){
                for(ShopImgEntity entity :imgList){
                shopImgRepository.delete(entity);
                }
            }
            Optional<BusinessHourEntity> entitySid = businessHourRepository.findByShopEntity_Sid(bySName.get().getSid());
            entitySid.ifPresent(businessHourRepository::delete);
            List<ShopMenuEntity> byShopEntitySid = shopMenuRepository.findByShopEntity_Sid(bySName.get().getSid());
            if(!byShopEntitySid.isEmpty()){
                for(ShopMenuEntity shopMenuEntity: byShopEntitySid){
                    shopMenuRepository.delete(shopMenuEntity);
                }
            }
            ShopEntity shopEntity = shopItemService.updateShopImage(shopMenuFormDto, imgFileList);
            shopItemService.saveMenu(menu, menuPrice, shopEntity);
            shopItemService.saveBusiness(shopBusinessHourDto,dayOff,shopEntity);

            //            shopItemService.save(shopMenuFormDto);
        } catch (Exception e) {
            log.error("this is an error =====>"+e);
            error="예외 발생.";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/update/"+encodeSName+"?errorMessage="+encodeError;

        }

        return "redirect:/";
    }





    @GetMapping("/showImage")
    public String showImage(Model model) {
        // 외부 이미지 경로를 Thymeleaf 템플릿으로 전달
//        model.addAttribute("imagePath", "location+${sNAme}");
        return "showImage"; // Thymeleaf 템플릿 이름
    }
}
