package inhatc.project.tap.validator;

import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckPhoneValidator extends AbstractValidator<MemberDTO>{
    private final MemberRepository memberRepository;

    @Override
    protected  void doValidate(MemberDTO dto, Errors errors){
        if(memberRepository.existsByPhoneNumber(dto.getMember_phone())){
            errors.rejectValue("member_phone","전화번호 중복","이미 사용중인 전화번호입니다.");
        }
    }
}
