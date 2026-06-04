package inhatc.project.tap.service;

import inhatc.project.tap.constant.Code;
import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //등록자 신청한 회원정보 가져오기

    public List<MemberDTO> editorCheck() {
        List<MemberEntity> memberEntityList = memberRepository.findByCode(Code.WAIT);
        List<MemberDTO> memberDTOList=new ArrayList<>();
        for(MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;
    }

    public boolean promote(String memberId) {
        Optional<MemberEntity> result = memberRepository.findById(memberId);
        if(result.isPresent()){
            MemberEntity memberEntity = result.get();
            memberEntity.setRole(Rank.EDITOR);
            memberEntity.setCode(Code.FINISH);
            memberRepository.save(memberEntity);
            return true;
        }
        return false;
    }

    public boolean hold(String memberId) {
        Optional<MemberEntity> result = memberRepository.findById(memberId);
        if(result.isPresent()){
            MemberEntity memberEntity = result.get();
            memberEntity.setCode(Code.FINISH);
            memberRepository.save(memberEntity);
            return true;
        }
        return false;
    }

    public List<MemberDTO> memberAll() {
        List<MemberEntity> memberEntityList = memberRepository.findByRole();
        List<MemberDTO> memberDTOList=new ArrayList<>();

        for(MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;

    }

    public void updateMember( MemberDTO memberDTO) {
        Optional<MemberEntity> byId = memberRepository.findById(memberDTO.getMember_id());
        byId.get().setRole(memberDTO.getMember_Role());
        memberRepository.save(byId.get());
    }
}
