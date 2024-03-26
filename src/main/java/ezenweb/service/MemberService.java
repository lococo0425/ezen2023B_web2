package ezenweb.service;

import ezenweb.model.Dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MemberService {
        @Autowired
        private MemberEntityRepository memberEntityRepository;
        public boolean doSignupPost( MemberDto memberDto){
            System.out.println("memberDto = " + memberDto);
            //1. 엔티티 만들기
            //2. 리포지토리를 통한 엔티티를 저장한다.
            MemberEntity memberEntity = MemberEntity.builder()
                    .memail(memberDto.getMemail())
                    .mpassword(memberDto.getMpassword())
                    .mname(memberDto.getMname())
                    .build();
            memberEntityRepository.save(memberDto.toEntity());
            return false;
        }
}
