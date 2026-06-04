package inhatc.project.tap.validator;

import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberNameValidator extends AbstractValidator<MemberDTO>{
    private final MemberRepository memberRepository;
    @Override
    protected  void doValidate(MemberDTO dto, Errors errors){
        if(memberRepository.existsById(dto.getMember_id())){
            errors.rejectValue("member_id","아이디 중복 오류","이미 사용중인 아이디 입니다.");
        }
    }
}
