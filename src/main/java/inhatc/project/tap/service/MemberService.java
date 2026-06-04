package inhatc.project.tap.service;


import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;



    public void saveMember(MemberEntity member) {
        validateDuplicationMember(member);
        memberRepository.save(member);
    }

    private void validateDuplicationMember(MemberEntity member) {
        Optional<MemberEntity> findMember = memberRepository.findById(member.getId());
        if(findMember.isPresent()){
            System.out.println(findMember.get().getId());
            throw new IllegalStateException("이미 존재하는 회원");
        }

    }
    //entity 업데이트 메소드
    public void updateMember(MemberDTO memberDTO){
        Optional<MemberEntity> byId = memberRepository.findById(memberDTO.getMember_id());
        if(byId.isPresent()){
            MemberEntity memberEntity = byId.get();
            memberEntity.setMemberNickname(memberDTO.getMember_nickname());
            memberEntity.setAnswer(memberDTO.getMember_answer());
            memberEntity.setPhoneNumber(memberDTO.getMember_phone());
            memberEntity.setStatus(memberDTO.getMember_status());
            memberRepository.save(memberEntity);

        }
    }

    //아이디 찾는거
    public MemberDTO find_id(String id) {
        Optional<MemberEntity> byId = memberRepository.findById(id);
        if(byId.isPresent()){
            MemberEntity memberEntity =byId.get();
            return MemberDTO.toMemberDTO(memberEntity);
        }
        else{
            return null;
        }
    }
    //비밀번호 찾기에서 입력된 정보가 id를 통해 가져온 entity의 정보가 같는지 확인
    public boolean checkPw(MemberDTO memberDTO){
        Optional<MemberEntity> idResult = memberRepository.findById(memberDTO.getMember_id());
        if(idResult.isPresent()){
            MemberEntity member = idResult.get();
            String name = member.getName();
            String status = member.getStatus();
            String phoneNumber = member.getPhoneNumber();
            String answer = member.getAnswer();
            if(name.equals(memberDTO.getMember_name())&&status.equals(memberDTO.getMember_status())&&phoneNumber.equals(memberDTO.getMember_phone())&&answer.equals(memberDTO.getMember_answer())){
                return true;
            }
            return false;
        }
        return false;
    }
    //모든 회원정보 가져오기
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList=new ArrayList<>();
        for(MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;
    }
    //등록자 신청한 회원정보 가져오기


    //닉네임 찾기
    public MemberDTO findByNickname(String nickname) {
        Optional<MemberEntity> memberOptional = memberRepository.findByMemberNickname(nickname);
        //html에 보여주기 위해서는 DTO 타입으로 변형 시켜야함
        return memberOptional.map(MemberDTO::toMemberDTO).orElse(null);
    }

    //아이디 찾기
    public MemberDTO findById(String id) {
        Optional<MemberEntity> memberOptional = memberRepository.findById(id);
        //html에 보여주기 위해서는 DTO 타입으로 변형 시켜야함
        return memberOptional.map(MemberDTO::toMemberDTO).orElse(null);
    }


    //로그인 된 정보를 가지고 닉네임 가져오기
    public String getNickname(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 객체가 특정 클래스 또는 인터페이스의 인스턴스인지를 확인
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<MemberEntity> byId = memberRepository.findById(username);
            return byId.map(MemberEntity::getMemberNickname).orElse(null);
            // 사용자 이름(username)을 이용한 로직 처리
        } else {
            return null;
            // 사용자 이름(username)을 이용한 로직 처리
        }
    }
    //로그인 된 정보를 가지고 전화번호 가져오기
    public String getPhoneNumber(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 객체가 특정 클래스 또는 인터페이스의 인스턴스인지를 확인
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<MemberEntity> byId = memberRepository.findById(username);
            return byId.map(MemberEntity::getPhoneNumber).orElse(null);
            // 사용자 이름(username)을 이용한 로직 처리
        } else {
            return null;
            // 사용자 이름(username)을 이용한 로직 처리
        }
    }
    //인증 완료된 아이디 가져오기
    public String getAuthenticatedId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<MemberEntity> byId = memberRepository.findById(username);
            return byId.get().getId();
        }else{
            return null;
        }
    }

    public boolean checkId(String memberId) {
        return memberRepository.existsById(memberId);
    }

    public boolean checkPhoneNumber(String memberPhone) {
        return memberRepository.existsByPhoneNumber(memberPhone);
    }

    public boolean checkNickname(String memberNickname) {
        return memberRepository.existsByMemberNickname(memberNickname);
    }
}
