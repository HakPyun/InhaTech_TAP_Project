package inhatc.project.tap.controller;


import inhatc.project.tap.dto.*;
import inhatc.project.tap.entity.BusinessHourEntity;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.entity.ShopImgEntity;
import inhatc.project.tap.entity.ShopMenuEntity;
import inhatc.project.tap.repository.*;
import inhatc.project.tap.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class ShopController {
    private ShopMenuService shopMenuService;
    private ShopMenuEntity shopMenuEntity;

    @Autowired
    private final ShopMenuRepository shopMenuRepository;

    private final BusinessHourRepository businessHourRepository;
    private final ShopRepository  shopRepository;

    @Autowired
    private ShopImgService shopImgService;
    private ShopImgRepository shopImgRepository;
    @Autowired
    private ReviewService reviewService;

    private final MemberService memberService;

    private final MemberRepository memberRepository;
    @Autowired//service 못찾을때 사용하는 어노테이션
    private ShopService shopService;
    //가게 등록 페이지 열기
//    @GetMapping("/register")
//    public String register(Model model, HttpSession session){
//        String id=(String) session.getAttribute("loginId");
//        model.addAttribute("member_id",id);
//        return "shop_register";
//    }
//    //가게 등록 버튼 누를시 db 연결
//    @PostMapping("/register")
//    public String shop_register(@ModelAttribute ShopDTO shopDTO,HttpServletRequest request, HttpServletResponse response){
//
//        // 응답의 인코딩 설정 (응답할 때의 인코딩)
//        response.setCharacterEncoding("UTF-8");
//
//
//
//        System.out.println("shopDTO = " + shopDTO);
//        shopService.save(shopDTO);
//        return "index";
//
//    }

    @GetMapping("/shop/myShopList")
    public String myShop(Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        String authenticatedId = memberService.getAuthenticatedId();
        List<ShopEntity> myList = shopService.getMyList(authenticatedId);
        model.addAttribute("List",myList);
        return "/Editor/myShopList";
    }

    @GetMapping("/shop/{sName}")
    public String shop(@PathVariable String sName, Model model){
        ShopEntity myShop = shopService.find_myShop(sName);
        if(myShop==null){
            model.addAttribute("errorMessage","정보를 불러오지 못했습니다.");
            return "redirect:/";
        }
        Optional<BusinessHourEntity> business = businessHourRepository.findByShopEntity_Sid(myShop.getSid());
        List<ShopMenuEntity> menus = shopMenuRepository.findByShopEntity_Sid(myShop.getSid());
        List<ShopMenuDto> shopMenuFormDtoList = ShopMenuEntity.toShopMenuFormDtoList(menus);

        List<Long> shopIds = shopService.findShopIdsByShopNames(sName);
        List<String> shopImgNames = shopImgService.findShopImgNameBySid2(shopIds);

        List<Review> reviewInfo = createReviewList(shopIds);

        model.addAttribute("reviewInfo", reviewInfo);
        model.addAttribute("shopImgNames", shopImgNames);
        model.addAttribute("business",business.get());
        model.addAttribute("menus",shopMenuFormDtoList);
        model.addAttribute("myShop",myShop);

        if (reviewInfo.isEmpty()) {
            return "Default/shop_detailtest";
        } else {
            return "/Default/shop_detail";
        }
    }
    @GetMapping(value = "shop/review/{sName}", produces = "text/plain;charset=UTF-8")
    public String review(@PathVariable String sName,@RequestParam(name = "errorMessage", required = false) String errorMessage, Model model){

        Optional<ShopEntity> shop = shopRepository.findBySName(sName);
        String authenticatedId = memberService.getAuthenticatedId();
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        if(shop.isEmpty()){
            return "redirect:/";
        }
        if(authenticatedId==null){
            return "redirect:/member/login";
        }

        model.addAttribute("sId",shop.get().getSid());
        model.addAttribute("sName",shop.get().getSName());
        return "/User/review";
    }
    //가게 수정페이지
    @GetMapping(value = "/shop/update/{sName}", produces = "text/plain;charset=UTF-8")
    public String findById(@PathVariable String sName,@RequestParam(name = "errorMessage", required = false) String errorMessage, Model model){
        ShopEntity myShop = shopService.find_myShop(sName);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        if(myShop==null){
            model.addAttribute("errorMessage","정보를 불러오지 못했습니다.");
            return "redirect:/Editor/myShopList";
        }
        Optional<BusinessHourEntity> entity = businessHourRepository.findByShopEntity_Sid(myShop.getSid());
        if(entity.isEmpty()){
            return"redirect:/";
        }

        List<ShopMenuEntity> menus = shopMenuRepository.findByShopEntity_Sid(myShop.getSid());
        List<ShopMenuDto> shopMenuFormDtoList = ShopMenuEntity.toShopMenuFormDtoList(menus);
        model.addAttribute("hour",entity.get());
        model.addAttribute("menus",shopMenuFormDtoList);
        model.addAttribute("myShop",myShop);
        return "Editor/updateShop";
    }

    @PostMapping("/shop/delete")
    public  ResponseEntity<Boolean> deleteShop(@RequestBody Map<String, String> requestBody){
        String sName = requestBody.get("sName");
        if(sName==null){
            return ResponseEntity.ok(null);
        }
        Optional<ShopEntity> shop = shopRepository.findBySName(sName);
        if(shop.isEmpty()){
            return ResponseEntity.ok(null);
        }

        shopRepository.delete(shop.get());
        return ResponseEntity.ok(true);
    }

    private List<Review> createReviewList(List<Long> shopIds) {
        //shopids로 reviewIds찾기
        List<Long> reviewIds = reviewService.findReviewIdsByShopIds(shopIds);


        //reviewIds로 각종 정보 찾기
        List<String> memberIds = reviewService.findMemberIdsByReviewIds(reviewIds);
        List<Double> userScores = reviewService.findUserScoresByReviewIds(reviewIds);
        List<String> userReviews = reviewService.findUserReviewsByReviewIds(reviewIds);
        List<Timestamp> timestamps = reviewService.findCreatedTimesByReviewIds(reviewIds);
        List<LocalDateTime> createdTimes = timestamps.stream()
                .map(Timestamp::toLocalDateTime)
                .collect(Collectors.toList());

        //shopids로 img정보 찾기
        List<Long> imgIds = reviewService.findImgIdsByShopIds(shopIds);
        List<String> imgNames = reviewService.findImgNamesByImgIds(imgIds);

        if (reviewIds == null || reviewIds.isEmpty() ||
                memberIds == null || memberIds.isEmpty() ||
                userScores == null || userScores.isEmpty() ||
                userReviews == null || userReviews.isEmpty() ||
                timestamps == null || timestamps.isEmpty() ||
                imgIds == null || imgIds.isEmpty() ||
                imgNames == null || imgNames.isEmpty()) {
            return Collections.emptyList();
        }

        List<Review> reviewList = new ArrayList<>();
        for (int i = 0; i < reviewIds.size(); i++) {
            Review review = Review.builder()
                    .memberId(memberIds.get(i))
                    .userScore(userScores.get(i))
                    .userReview(userReviews.get(i))
                    .created_at(createdTimes.get(i))
                    .ImgName(imgNames.get(i))
                    .build();
           reviewList.add(review);
        }

        return reviewList;
    }

//    @GetMapping("/mypage/check")
//    public String myPageCheck(HttpSession session,Model model){
//        String id=(String) session.getAttribute("loginId");
//        model.addAttribute("member_id",id);
//        return "my_page_check";
//    }

//    @PostMapping("/mypage/check")
//    public String pwCheck(@ModelAttribute MemberDTO memberDTO){
//
//        MemberDTO checkMember=memberService.findById(memberDTO.getMember_id());
//
//        System.out.println("memberDTO = " + memberDTO+"checkMember"+checkMember);
//        if (checkMember != null) {
//        if(memberDTO.getMember_pw().equals(checkMember.getMember_pw())){
//            return "redirect:/shop/mypage";
//        }else{
//            return "redirect:/main";
//        }}
//        else{
//            return "redirect:/main";
//        }
//    }

//    @GetMapping("/mypage")
//    public String myPage(HttpSession session, Model model){
//        String id=(String)session.getAttribute("loginId");
//        MemberDTO member_detail=memberService.findById(id);
//        if(member_detail!=null){
//            model.addAttribute("member",member_detail);
//            return "mypage";
//        }
//        else{
//            return "redirect:/main";
//        }
//    }

}
