package inhatc.project.tap.entity;

import inhatc.project.tap.constant.Code;
import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity(name="member")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberEntity {
    //아이디
    @Id
    @Column(nullable = false,name = "member_id",unique = true) // 속성명을 바꿔버림
    private String id;

    //비밀번호
    @Column(nullable = false,name="member_pw")
    private String pw;

    //이름
    @Column(nullable = false,name="member_name")
    private String name;

    //전화번호
    @Column(nullable = false, name="member_phone",unique = true)
    private String phoneNumber;

    //닉네임
    @Column(name="member_nickname" ,unique = true)
    private String memberNickname;

    //등록자 신청
    @Column( name="admin_check_code")
    @Enumerated(EnumType.STRING)

    private Code code;   //0,1,2

    //직책
    @Column(nullable = false, name="member_Role")
    @Enumerated(EnumType.STRING)
    private Rank role;

    //질문
    @Column( name="member_status")

    private String status;
    //답변
    @Column(nullable = false, name="member_answer")
    private String answer;

    @OneToMany(mappedBy = "memberEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private final List<ShopEntity> shopEntities=new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private final List<ShopLikeEntity> likeEntities=new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private final List<ShopReviewEntity> shopReviewEntities=new ArrayList<>();
    @OneToMany(mappedBy = "memberEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private final List<ShopReviewImgEntity>shopReviewImgEntities=new ArrayList<>();
    //DTO와 Entity객체와 매핑
    public static MemberEntity createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder){
        MemberEntity memberEntity =MemberEntity.builder()
                .id(memberDTO.getMember_id())
                .memberNickname(memberDTO.getMember_nickname())
                .pw(passwordEncoder.encode(memberDTO.getMember_pw()))
                .status(memberDTO.getMember_status())
                .answer(memberDTO.getMember_answer())
                .phoneNumber(memberDTO.getMember_phone())
                .name(memberDTO.getMember_name())
                .role(memberDTO.getMember_Role())
                .code(Code.NONE)
                .build();
        return memberEntity;
    }

    public void setMember_id(String member_id) {
        this.id = member_id;
    }

    public void addShop(ShopEntity shop) {
        shopEntities.add(shop);
        shop.setMemberEntity(this); // 양방향 연관 관계 설정
    }
}

