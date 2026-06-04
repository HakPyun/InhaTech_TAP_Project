package inhatc.project.tap.validator;

import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<MemberDTO>{
    private final MemberRepository memberRepository;

    @Override
    protected  void doValidate(MemberDTO dto, Errors errors){
        if(memberRepository.existsByMemberNickname(dto.getMember_nickname())){
            errors.rejectValue("member_nickname","닉네임 중복 오류","이미 사용중인 닉네임입니다.");
        }
    }
}
