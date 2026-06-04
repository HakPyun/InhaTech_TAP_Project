package inhatc.project.tap.controller;

import inhatc.project.tap.dto.ReviewDto;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.entity.ShopReviewEntity;
import inhatc.project.tap.repository.ReviewRepository;
import inhatc.project.tap.repository.ShopRepository;
import inhatc.project.tap.service.MemberService;
import inhatc.project.tap.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    private final ShopRepository shopRepository;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ReviewRepository reviewRepository;
    @GetMapping("/review/list")
    public String reviewList(Model model){
        String authenticatedId = memberService.getAuthenticatedId();
        List<ShopReviewEntity> list = reviewRepository.findByMemberEntity_Id(authenticatedId);
        List<String> sNames = new ArrayList<>();
        for (ShopReviewEntity review : list) {
            if (review.getShopEntity() != null) {
                String sName = review.getShopEntity().getSName();
                sNames.add(sName);
            }
        }
        model.addAttribute("shopList",sNames);
        model.addAttribute("reviewList",list);
        return "/User/reviewList";
    }
    @PostMapping("/review/save")
    public String saveReview(@Validated ReviewDto reviewDto, BindingResult bindingResult, @RequestParam("file")MultipartFile file, Model model){
       log.info(String.valueOf(reviewDto.getUScore()));
        Optional<ShopEntity> bySid = shopRepository.findBySid(reviewDto.getSId());
        String encodedSName = URLEncoder.encode(bySid.get().getSName(), StandardCharsets.UTF_8);
        String SName =encodedSName.replaceAll("\\+", " ");

        log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------"+encodedSName);
        reviewDto.setMId(memberService.getAuthenticatedId());
        String error=null;
        if(reviewDto.getUScore()==0.0){
            error="별점은 필수 입니다.";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/review/"+SName+"?errorMessage="+encodeError;
        }
        if(bindingResult.hasErrors()){
            error="에러 발생";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/review/"+SName+"?errorMessage="+encodeError;
        }
        try{

               reviewService.saveReview(reviewDto, file);


       }catch (Exception e){
            error="예외 발생";
            String encodeError=URLEncoder.encode(error,StandardCharsets.UTF_8);
            return "redirect:/shop/review/"+SName+"?errorMessage="+encodeError;
       }

        return "redirect:/shop/"+SName;
    }
    @PostMapping("/review/delete")
    public ResponseEntity<Boolean> deleteShop(@RequestBody Map<String, Long> requestBody){
        long rId = requestBody.get("review_id");
        if(rId==0){
            return ResponseEntity.ok(null);
        }
        Optional<ShopReviewEntity> review = reviewRepository.findById(rId);
        if(review.isEmpty()){
            return ResponseEntity.ok(null);
        }
        reviewRepository.delete(review.get());
        return ResponseEntity.ok(true);
    }
}
