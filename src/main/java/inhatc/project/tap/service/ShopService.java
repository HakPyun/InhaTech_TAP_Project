package inhatc.project.tap.service;

//import inhatc.project.tap.dto.ShopDTO;
import inhatc.project.tap.dto.MemberDTO;
import inhatc.project.tap.dto.ShopMenuFormDto;
import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.entity.ShopEntity;
import inhatc.project.tap.repository.MemberRepository;
import inhatc.project.tap.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.MemberAttributeExtension;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final MemberRepository memberRepository;

    private final ShopRepository shopRepository;
    private static ModelMapper modelMapper = new ModelMapper();

    public void save() {
        //repository save 메서드 호출(entity 객체를 넘겨줘야함)
        //1.dto->entity 변환
        //2.repositroy의 save메서드 호출
        ShopEntity shopEntity = modelMapper.map(this, ShopEntity.class);
        shopRepository.save(shopEntity);
    }
    public List<ShopMenuFormDto> findAll() {
        List<ShopEntity> ShopList = shopRepository.findAll();
        List<ShopMenuFormDto> ShopDtoList=new ArrayList<>();
        for(ShopEntity shopEntity : ShopList){
            MemberEntity memberEntity = shopEntity.getMemberEntity();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);

            ShopMenuFormDto shopMenuFormDto = ShopMenuFormDto.of(shopEntity);
            shopMenuFormDto.setMember_id(memberDTO.getMember_id());
            ShopDtoList.add(shopMenuFormDto);
        }
        return ShopDtoList;
    }
    public List<ShopEntity> getMemberList(String id) {
        Optional<MemberEntity> byId = memberRepository.findById(id);
        List<ShopEntity> byMemberEntity = shopRepository.findByMemberEntity(byId.get());
        return byMemberEntity;
    }
    public List<ShopEntity> getMyList(String authenticatedId) {
        Optional<MemberEntity> byId = memberRepository.findById(authenticatedId);
        List<ShopEntity> byMemberEntity = shopRepository.findByMemberEntity(byId.get());
        return byMemberEntity;
    }

    public ShopEntity find_myShop(String sName) {
        Optional<ShopEntity> bySName = shopRepository.findBySName(sName);
        return bySName.orElse(null);
    }

    public List<Long> findShopIdsByCategory(String cat) {
        List<Long> shopIds = shopRepository.findByCat(cat);
        return shopIds;
    }
    public List<String> findShopNameBySid(List<Long> sid) {
        List<String> shopNames = shopRepository.findSNameBySid(sid);
        return shopNames;
    }

    public List<Long> findShopIdsByAll() {
        List<Long> shopIds = shopRepository.findShopIdByAll();
        return shopIds;
    }

    public List<Long> findShopIdsByShopNames(String sName) {
        List<Long> shopIds = shopRepository.findShopIdByShopName(sName);
        return shopIds;
    }

//    private final ShopDTO shopDTO;
//public ShopDTO findBySid(Long sid) {
//    Optional<ShopEntity> shopEntityOptional = shopRepository.findBySid(sid);
//    if(shopEntityOptional.isPresent()){
//        //html에 보여주기 위해서는 DTO 타입으로 변형 시켜야함
//        return ShopDTO.toShopDTO(shopEntityOptional.get());
//    }else{
//        return null;
//    }
//}

//    public void save(ShopDTO shopDTO) {
//        MemberEntity member=new MemberEntity();//member 객체 생성
//        member.setId(shopDTO.getMember_id());//member_id dto값을 member객체 넣어놓음
//        ShopEntity shop=ShopEntity.toShop(shopDTO);//member_id제외하고 dto 데이터 값을 entity틀로 바꾼다.
//        shop.setMemberEntity(member);//shop entity의 member_id는 외래키이므로,MemberEntity 형태로 가져와야하기에  위에 member객체 생성함
//        shopRepository.save(shop);
//
//    }
}
