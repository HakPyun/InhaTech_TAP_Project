package inhatc.project.tap.service;

import inhatc.project.tap.dto.ReviewDto;
import inhatc.project.tap.entity.*;
import inhatc.project.tap.repository.MemberRepository;
import inhatc.project.tap.repository.ReviewImgRepository;
import inhatc.project.tap.repository.ReviewRepository;
import inhatc.project.tap.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
private final ShopRepository shopRepository;
private final MemberRepository memberRepository;
private final ReviewRepository reviewRepository;
private final ShopFileService shopFileService;
private final ReviewImgRepository reviewImgRepository;
String location="../tap_project/src/main/resources/static/img/ReviewImg";
    public void saveReview(ReviewDto reviewDto,MultipartFile file) throws Exception {
        Optional<MemberEntity> member = memberRepository.findById(reviewDto.getMId());
        Optional<ShopEntity> shopEntity = shopRepository.findBySid(reviewDto.getSId());
        if(shopEntity.isPresent()&&member.isPresent()){
            ShopReviewEntity shopReviewEntity=new ShopReviewEntity();
            shopReviewEntity.setShopEntity(shopEntity.get());
            shopReviewEntity.setMemberEntity(member.get());
            shopReviewEntity.setUReview(reviewDto.getUReview());
            shopReviewEntity.setUScore(reviewDto.getUScore());
            reviewRepository.save(shopReviewEntity);

            ShopReviewImgEntity shopReviewImg=new ShopReviewImgEntity();
            shopReviewImg.setMemberEntity(member.get());
            shopReviewImg.setShopEntity(shopEntity.get());
            saveReviewImg(shopReviewImg,file);

        }
    }

    private void saveReviewImg(ShopReviewImgEntity shopReviewImgEntity, MultipartFile file) throws  Exception{
        String review_oriImgName=file.getOriginalFilename();
        String review_imgName="";
        String review_imgUrl="";

        if(!StringUtils.isEmpty(review_oriImgName)){
            review_imgName=shopFileService.uploadFile(location,review_oriImgName,file.getBytes());
            review_imgUrl="../img/ReviewImg/"+review_imgName;

        }
        shopReviewImgEntity.ReviewImg(review_oriImgName,review_imgName,review_imgUrl);
        reviewImgRepository.save(shopReviewImgEntity);
    }

    public List<Long> findReviewIdsByShopIds(List<Long> sid) {
        List<Long> reviewIds = reviewRepository.findReviewIdByShopId(sid);
        return reviewIds;
    }

    public List<String> findMemberIdsByReviewIds(List<Long> rid) {
        List<String> memberIds = reviewRepository.findMemberIdByReviewId(rid);
        return memberIds;
    }

    public List<Double> findUserScoresByReviewIds(List<Long> rid) {
        List<Double> userScores = reviewRepository.findShopScoreByReviewId(rid);
        return userScores;
    }

    public List<String> findUserReviewsByReviewIds(List<Long> rid) {
        List<String> userReviews = reviewRepository.findShopReviewByReviewId(rid);
        return userReviews;
    }

    public List<Timestamp> findCreatedTimesByReviewIds(List<Long> rid) {
        List<Timestamp> createdTimes = reviewRepository.findReviewCreatedByReviewId(rid);
        return createdTimes;
    }

    public List<Long> findImgIdsByShopIds(List<Long> sid) {
        List<Long> imgIds = reviewImgRepository.findImgIdByShopId(sid);
        return imgIds;
    }

    public List<String> findImgNamesByImgIds(List<Long> imgId) {
        List<String> imgNames = reviewImgRepository.findImgNameByImgId(imgId);
        return imgNames;
    }
}
