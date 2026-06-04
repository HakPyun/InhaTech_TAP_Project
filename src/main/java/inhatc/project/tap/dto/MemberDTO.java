package inhatc.project.tap.dto;

import inhatc.project.tap.constant.Code;
import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.entity.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String member_id;


    @NotBlank(message = "비밀번호는 필수 입력 값입니다..")
    //정규 표현식으로 비밀번호 패턴 생성
    //(?=.*\d) : 문자열에 숫자가 적어도 1개 이상 포함되어야 함을 나타냅니다.
    //(?=.*[a-zA-Z]) : 문자열에 영문자가 적어도 1개 이상 포함되어야 함을 나타냅니다.
    //(?=.*[\W_]) : 문자열에 특수문자가 적어도 1개 이상 포함되어야 함을 나타냅니다.
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String member_pw;

    //정규표현식:2~6자 이내 이름
    @Pattern(regexp = "^[가-힣]{2,5}$",message ="이름 형식에 맞지 않습니다.")
    private String member_name;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String member_phone;

    @Pattern(regexp = "^[\\w\\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String member_nickname;

    private Code admin_check_code;

    private Rank member_Role;

    private String member_status;

    @NotBlank(message = "답변은 필수 입력 값입니다.")
    private String member_answer;
    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO=new MemberDTO();
        memberDTO.setMember_id(memberEntity.getId());
        memberDTO.setMember_pw(memberEntity.getPw());
        memberDTO.setMember_name(memberEntity.getName());
        memberDTO.setMember_Role(memberEntity.getRole());
        memberDTO.setMember_phone(memberEntity.getPhoneNumber());
        memberDTO.setMember_answer(memberEntity.getAnswer());
        memberDTO.setMember_status(memberEntity.getStatus());
        memberDTO.setMember_nickname(memberEntity.getMemberNickname());
        memberDTO.setAdmin_check_code(memberEntity.getCode());
        return memberDTO;
    }

}
