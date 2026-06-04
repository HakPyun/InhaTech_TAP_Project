package inhatc.project.tap.controller;


import inhatc.project.tap.constant.Code;
import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.dto.ShopInfo;
import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.service.*;
import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.repository.MemberRepository;
import inhatc.project.tap.validator.CheckMemberNameValidator;
import inhatc.project.tap.validator.CheckNicknameValidator;
import inhatc.project.tap.validator.CheckPhoneValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final CheckMemberNameValidator checkMemberNameValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckPhoneValidator checkPhoneValidator;
    private String find_id;

    public String session_id;
    //session값 shopController에 넣기

    private final PasswordEncoder passwordEncoder;
    //생성자 주입
    //컨트롤러가 이 서비스의 메서드를 사용할 수 있는 권한이 생기는 것
    private final MemberService memberService;

    private final MemberRepository memberRepository;


    private final MemberSecurityService userDetailsService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopImgService shopImgService;


    //메인
    @GetMapping("/")
    public String main(Model model)
    {
        //전체 데이터 가져오기
//        List<ShopInfo> allShopInfo = createShopInfoList2();
//        model.addAttribute("allShopInfo", allShopInfo);
//
//        // 술집 카테고리에 해당하는 데이터 가져오기
//        List<ShopInfo> sakeShopInfo = createShopInfoList("술집");
//        model.addAttribute("sakeShopInfo", sakeShopInfo);
//
//        // 음식점 카테고리에 해당하는 데이터 가져오기
//        List<ShopInfo> foodShopInfo = createShopInfoList("음식점");
//        model.addAttribute("foodShopInfo", foodShopInfo);
//
//        // 카페 카테고리에 해당하는 데이터 가져오기
//        List<ShopInfo> coffeeShopInfo = createShopInfoList("카페");
//        model.addAttribute("coffeeShopInfo", coffeeShopInfo);
//        System.out.println("allShopInfo: " + allShopInfo);
//        System.out.println("sakeShopInfo: " + sakeShopInfo);
//        System.out.println("foodShopInfo: " + foodShopInfo);
//        System.out.println("coffeeShopInfo: " + coffeeShopInfo);
//        if (allShopInfo.isEmpty() || sakeShopInfo.isEmpty() || foodShopInfo.isEmpty() || coffeeShopInfo.isEmpty()) {
//            // Return "index" if the list is empty
//            return "Default/index";
//        } else {
//            // Return "indextest" if the list is not empty
            return "indextest";
//        }
    }

    //회원가입 페이지 매핑
    @GetMapping("/member/save")
    public String SaveForm(Model model){
        model.addAttribute("memberDTO",new MemberDTO());

        return "Default/save";
    }

    //RequestParam:html의 name이 memberEmail인 값을 memberEmail이라는 변수로 가져온다.
    //modelAttribute:모델(html)에 있는 name과 DTO의 변수명과 같은 객체의 값을 가져온다.
    //회원가입 매핑
    @PostMapping("/member/save")
    public String save(@Valid @ModelAttribute  MemberDTO memberDTO, BindingResult bindingResult, Model model){
        if(memberService.checkId(memberDTO.getMember_id())){
            model.addAttribute("errorMessage","이미 존재하는 아이디 입니다.");
            return "Default/save";
        }else if(memberService.checkNickname(memberDTO.getMember_nickname())){
            model.addAttribute("errorMessage","이미 존재하는 닉네임 입니다.");
            return "Default/save";
        }else if(memberService.checkPhoneNumber(memberDTO.getMember_phone())){
            model.addAttribute("errorMessage","이미 존재하는 전화번호 입니다.");
            return "Default/save";
        }if(bindingResult.hasErrors()) {
            return "Default/save";
        }
        try{memberDTO.setAdmin_check_code(Code.NONE);
            memberDTO.setMember_Role(Rank.USER);
            MemberEntity member=MemberEntity.createMember(memberDTO,passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "Default/save";
        }
        return "redirect:/";

    }

    //아이디 중복 버튼 쿠현
    // 입력된 정보를 토대로 db에서 id 가져옴
    @PostMapping("/member/check/id")
    public ResponseEntity<Boolean> checkId(@RequestBody MemberDTO memberDTO){
        Optional<MemberEntity> byId = memberRepository.findById(memberDTO.getMember_id());

        if(byId.isPresent()){

            return ResponseEntity.ok(null);
        }else{
        return ResponseEntity.ok(true);
    }}

    //닉네임 중복
    @PostMapping("/member/check/nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestBody MemberDTO memberDTO){
        log.info(memberDTO.getMember_nickname());
        Optional<MemberEntity> byNick = memberRepository.findByMemberNickname(memberDTO.getMember_nickname());

        if(byNick.isPresent()){

            return ResponseEntity.ok(null);
        }else{
            return ResponseEntity.ok(true);
        }}

    //로그인 페이지
    @GetMapping("/member/login")
    public String loginForm()
    {
        return "Default/login";

    }
    //로그인 에러
    @GetMapping(value = "/member/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "Default/login";
    }

    //마이페이지 들어가기 전에 비밀번호 확인하는 페이지
    @GetMapping("/mypage/check")
    public String myPageCheck(Model model)
    {String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        return "User/mypage_check";
}

    //비밀번호 맞는지 확인
    //맞을 시 마이페이지로 이동
    @PostMapping("/mypage/check")
    public String myCheck(MemberDTO memberDTO ,Model model){
        log.info(memberDTO.getMember_pw());

        if(memberDTO.getMember_pw().isEmpty()) {
            model.addAttribute("ErrorMsg", "비밀번호 입력하세요.");
            return "User/mypage_check";
        }else{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info(((UserDetails) principal).getPassword());
            String username = ((UserDetails) principal).getUsername();
            Optional<MemberEntity> byId = memberRepository.findById(username);
            if(byId.isPresent()){
                if(passwordEncoder.matches(memberDTO.getMember_pw(),byId.get().getPw())){
                    return "redirect:/mypage";
                }
                else {
                    model.addAttribute("ErrorMsg","비밀번호 오류");
                    return "User/mypage_check";
                }
            }
            return "User/mypage_check";
        }
    }

    //마이페이지
    @GetMapping("/mypage")
    public String myPage(Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Optional<MemberEntity> byId = memberRepository.findById(username);
            MemberDTO memberDTO = MemberDTO.toMemberDTO(byId.get());
            model.addAttribute("memberDTO",memberDTO);

        return "User/mypage";
    }

    //마이페이지 중 수정하기 버튼 클릭 시 사용되는 post
    //이름이나 닉네임은 그대로 가도 상관 없기에 if문으로 db검색 제어
    @PostMapping("/mypage/update")
    public ResponseEntity<?> memberUpdate( @RequestBody MemberDTO memberDTO){
        log.info(String.valueOf(memberDTO));
        String nickname = memberService.getNickname();
        String phone=memberService.getPhoneNumber();
        if (!nickname.equals(memberDTO.getMember_nickname()) && memberRepository.existsByMemberNickname(memberDTO.getMember_nickname())) {
            return ResponseEntity.ok(null); // 닉네임이 중복됨
        }

        if (!phone.equals(memberDTO.getMember_phone()) && memberRepository.existsByPhoneNumber(memberDTO.getMember_phone())) {
            return ResponseEntity.ok(null); // 전화번호가 중복됨
        }

        memberService.updateMember(memberDTO);
        return ResponseEntity.ok(true); // 업데이트 성공
    }

    //삭제 버튼 클릭시 사용됨
    //회원 삭제 구현
    @PostMapping("/mypage/delete")
    public ResponseEntity<?> memberDelete(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        memberRepository.deleteById(username);
        return ResponseEntity.ok(true);
    }


    //아이디 찾기 페이지 구현
    @GetMapping("/member/find/id")
    public String find_id(Model model){
        model.addAttribute("memberDTO",new MemberDTO());
        return "Default/find_id";
    }

    //입력된 정보가 db에서 가져온 entity의 정보랑 같는지 확인 하고
    //같으면 아이디를 보여줌
    //다르면 아이디 찾을 수 없다
    @PostMapping("/member/find/id")
    public String findId(@ModelAttribute MemberDTO memberDTO,Model model){
        log.info(String.valueOf(memberDTO));
        if(memberDTO.getMember_name().isEmpty() ||memberDTO.getMember_phone().isEmpty())
        {
            model.addAttribute("memberDTO",memberDTO);
            model.addAttribute("ErrorMsg","해당 정보를 입력하세요.");
            return "Default/find_id";
        }else {
            memberRepository.findByNameAndPhoneNumber(memberDTO.getMember_name(), memberDTO.getMember_phone())
                    .ifPresentOrElse(
                            memberEntity -> model.addAttribute("ErrorMsg", "찾으시는 아이디:" + memberEntity.getId() + "입니다."),
                            () -> model.addAttribute("ErrorMsg", "해당 정보에 대한 아이디를 찾을 수 없습니다.")
                    );
        }

        return "Default/find_id";

    }

    //비밀번호 찾기 페이지 구현
    @GetMapping("/member/find/pw")
    public String find_pw(Model model){

        model.addAttribute("memberDTO",new MemberDTO());
        return "Default/find_pw";
    }

    //정보 입력란이 빈칸이면 error 메세지 출력
    //정보가 entity의 정보와 맞을 시 비밀번호 재설정 페이지로 이동
    @PostMapping("/member/find/pw")
    public String findPw(@ModelAttribute MemberDTO memberDTO, Model model) {
        if (isEmptyOrNull(memberDTO.getMember_name(), memberDTO.getMember_phone(), memberDTO.getMember_id(), memberDTO.getMember_answer())) {
            model.addAttribute("memberDTO", memberDTO);
            model.addAttribute("ErrorMsg", "해당 정보를 입력하세요.");
            return "Default/find_pw";
        }

        if (memberService.checkPw(memberDTO)) {
            model.addAttribute("memberDTO",memberDTO);
            return "Default/find_pw_result";
        }

        model.addAttribute("ErrorMsg", "해당 정보를 가진 데이터가 존재하지 않습니다.");
        return "Default/find_pw";
    }

    private boolean isEmptyOrNull(String... values) {
        for (String value : values) {
            if (value == null || value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    //마이페이지에서 비밀번호 변경 클릭시 나오는 페이지
    @GetMapping("/member/update/pw")
    public String updatePwForm(Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        model.addAttribute("memberDTO",new MemberDTO());
        return "User/update_pw";
    }

    //마이페이지에서 비밀번호 재설정
    @PostMapping("/member/update/pw")
    public ResponseEntity<?> updatePw(@RequestBody MemberDTO updatePw){
        try {
            String authenticatedId = memberService.getAuthenticatedId();
            MemberDTO dtoResult = memberService.find_id(authenticatedId);
            dtoResult.setMember_pw(updatePw.getMember_pw());
            MemberEntity member = MemberEntity.createMember(dtoResult, passwordEncoder);
            memberRepository.save(member);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            return ResponseEntity.ok(null);

        }

    }
    //비밀번호 찾기에서 비밀번호 재설정
    @PostMapping("/member/update/pw/search")
    public ResponseEntity<?> updatePw_search(@RequestBody MemberDTO dto){
        try {
            MemberDTO dtoResult = memberService.find_id(dto.getMember_id());
            dtoResult.setMember_pw(dto.getMember_pw());
            MemberEntity member = MemberEntity.createMember(dtoResult, passwordEncoder);
            memberRepository.save(member);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            return ResponseEntity.ok(null);

        }

    }

    //등록자 신청페이지 구현
    @GetMapping("/member/register/editor")
    public String registerForm(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        MemberDTO memberDto = memberService.findById(username);
        model.addAttribute("memberDTO",memberDto);
        return "User/editorRegi";
    }

    //버튼 클릭시 코드값을 바꾼다
@PostMapping("/member/register/editor")
    public ResponseEntity<?> registerEditor(@RequestBody MemberDTO register){
        try {
            String authenticatedId = memberService.getAuthenticatedId();
            Optional<MemberEntity> resultId = memberRepository.findById(authenticatedId);
            if(resultId.isPresent()){
                resultId.get().setCode(Code.WAIT);
                memberRepository.save(resultId.get());
            }
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            return ResponseEntity.ok(null);
        }
}

    private List<ShopInfo> createShopInfoList(String category) {
        List<Long> shopIds = shopService.findShopIdsByCategory(category);
        List<String> shopNames = shopService.findShopNameBySid(shopIds);
        List<String> shopImageNames = shopImgService.findShopImgNameBySid(shopIds);
//        List<String> shopImgUrls = shopImgService.findShopImgUrlBySid(shopIds);

        List<ShopInfo> shopInfoList = new ArrayList<>();
        for (int i = 0; i < shopIds.size(); i++) {
            ShopInfo shopInfo = ShopInfo.builder()
                    .shopId(shopIds.get(i))
                    .shopName(shopNames.get(i))
                    .shopImageName(shopImageNames.get(i))
//                    .imageUrl(shopImgUrls.get(i))
                    .build();
            shopInfoList.add(shopInfo);
        }

        return shopInfoList;
    }

    private List<ShopInfo> createShopInfoList2() {
        List<Long> shopIds = shopService.findShopIdsByAll();
        System.out.println(shopIds);
        List<String> shopNames = shopService.findShopNameBySid(shopIds);
        System.out.println(shopNames);
        List<String> shopImageNames = shopImgService.findShopImgNameBySid(shopIds);
        System.out.println(shopImageNames);

        List<ShopInfo> shopInfoList = new ArrayList<>();
        for (int i = 0; i < shopIds.size(); i++) {
            ShopInfo shopInfo = ShopInfo.builder()
                    .shopId(shopIds.get(i))
                    .shopName(shopNames.get(i))
                    .shopImageName(shopImageNames.get(i))
                    .build();
            shopInfoList.add(shopInfo);
        }
        return shopInfoList;
    }


}