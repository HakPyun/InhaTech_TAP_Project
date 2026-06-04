package inhatc.project.tap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberUpdatePwDto {

    @NotBlank(message = "비밀번호는 필수 입력 값입니다..")
    //정규 표현식으로 비밀번호 패턴 생성
    //(?=.*\d) : 문자열에 숫자가 적어도 1개 이상 포함되어야 함을 나타냅니다.
    //(?=.*[a-zA-Z]) : 문자열에 영문자가 적어도 1개 이상 포함되어야 함을 나타냅니다.
    //(?=.*[\W_]) : 문자열에 특수문자가 적어도 1개 이상 포함되어야 함을 나타냅니다.
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String member_pw;
}
