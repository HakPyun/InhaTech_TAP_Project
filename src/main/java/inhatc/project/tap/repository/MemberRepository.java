package inhatc.project.tap.repository;


import inhatc.project.tap.constant.Code;
import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {
    //이메일로 회원 정보 조회
    //Optional :null 방지
    Optional<MemberEntity> findById(String member_id);

    Optional<MemberEntity> findByNameAndPhoneNumber(String member_name, String phone_number);
    Optional<MemberEntity> findByIdAndNameAndPhoneNumber(String id,String member_name, String phone_number);

    List<MemberEntity> findByCode(Code code);
    boolean existsById(String member_id);
    boolean existsByPhoneNumber(String member_phone);
    boolean existsByMemberNickname(String member_nickname);

    Optional<MemberEntity> findByMemberNickname(String member_nickname);

    @Query(value="select * from Member m where m.member_Role like 'USER' or m.member_Role like 'EDITOR'",nativeQuery = true )
    List<MemberEntity> findByRole();

}
