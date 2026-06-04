package inhatc.project.tap.controller;

import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.repository.MemberRepository;
import inhatc.project.tap.service.AdminService;
import inhatc.project.tap.service.MemberService;
import inhatc.project.tap.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
private final MemberService memberService;
private final AdminService adminService;
private final MemberRepository memberRepository;
private final ShopService shopService;
    @GetMapping("/editor/check/list")
    public String memberList(Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        List<MemberDTO> DTOlist=adminService.editorCheck();
        model.addAttribute("memberList",DTOlist);
        return "Admin/admin_editor_check_list";
    }
    //rest api
    @GetMapping("/editor/check/{id}")
    public String findById(@PathVariable String id, Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        MemberDTO memberDTO= memberService.findById(id);
        model.addAttribute("memberDTO",memberDTO);
        return "Admin/editor_regi_detail";
    }

    @PostMapping("/member/check/promote")
    public ResponseEntity<?> promote(@RequestBody  MemberDTO memberDTO){
        if(adminService.promote(memberDTO.getMember_id())){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/member/check/hold")
    public ResponseEntity<?> hold(@RequestBody  MemberDTO memberDTO){
        if(adminService.hold(memberDTO.getMember_id())){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/member/list")
    public String member_list(Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        List<MemberDTO> memberDTOList = adminService.memberAll();
        model.addAttribute("memberList",memberDTOList);
        return "Admin/member_list";
    }

    @GetMapping("/member/list/{id}")
    public String list_detail(@PathVariable String id, Model model){
        String nickname = memberService.getNickname();
        model.addAttribute("userNickname",nickname);
        MemberDTO memberDTO= memberService.findById(id);
        model.addAttribute("memberDTO",memberDTO);
        List<ShopEntity> membeShopList = shopService.getMemberList(id);
        model.addAttribute("ShopList",membeShopList);

        return "Admin/member_detail";
    }



    @PostMapping("/member/delete")
    public ResponseEntity<?> deleteMember(@RequestBody  MemberDTO memberDTO){
        try{
        memberRepository.deleteById(memberDTO.getMember_id());
            return ResponseEntity.ok(true);
    }catch (IllegalArgumentException e){
            return ResponseEntity.ok(null);
        }

    }
    @PostMapping("/member/update")
    public ResponseEntity<?> updateMember(@RequestBody  MemberDTO memberDTO){
        try{log.info("+++++++++++++___________________________________________________________________-start");
            adminService.updateMember(memberDTO);
            return ResponseEntity.ok(true);
        }catch (IllegalArgumentException e){
            return ResponseEntity.ok(null);
        }

    }
}
