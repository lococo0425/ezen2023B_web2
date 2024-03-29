package ezenweb.service;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired MemberEntityRepository memberEntityRepository;

    // 1. 회원가입
    public int doSignupPost(  MemberDto memberDto){ System.out.println("memberDto = " + memberDto);
        doCheck(memberDto.getMemail());
        if(doCheck(memberDto.getMemail())){
            return 2;
        }
        // --  Dao 아닌 엔티티 이용한 레코드 저장하는 방법
        // 1. 엔티티를 만든다.
        // 2. 리포지토리 통한 엔티티를 저장한다. ( 엔티티 저장 성공시 매핑된 엔티티 반환 )
        MemberEntity savedEntity = memberEntityRepository.save(memberDto.toEntity()); // insert
        // 3. 엔티티 생성이 되었는지 아닌지 확인 ( PK )
        if( savedEntity.getMno() > 0 ) return 1;
        return 0;
    }

    // * 로그인 했다는 증거/기록
    @Autowired private HttpServletRequest request;
    // 2. 로그인( 세션 저장 )

    public boolean doLoginPost(MemberDto memberDto){
//        //1.
//        MemberEntity result1 = memberEntityRepository.findByMemailAndMpassword(memberDto.getMemail(),memberDto.getMpassword());
//        //2.
//        boolean result2 = memberEntityRepository.existsByMemailAndMpassword(memberDto.getMemail(), memberDto.getMpassword());
        //3.
        MemberEntity result3 = memberEntityRepository.findByLoginSQL(memberDto.getMemail(), memberDto.getMpassword());

        if(result3 == null)return false;
        //세션 부여
        request.getSession().setAttribute("loginInfo",result3.toDto()); //* 회원번호 저장

        return true;
    }

    // 3. 로그아웃( 세션 삭제 )
    public boolean doLogOutGet( ){
        request.getSession().setAttribute("loginInfo" , null );
        return true;
    }
    // 4. 현재 로그인된 회원정보 호출 ( 세션 값 반환/호출 )
    public MemberDto doLoginInfo(){
        Object object = request.getSession().getAttribute("loginInfo");
        if( object != null ){
            return (MemberDto)object; // 강제형변환
        }
        return null;
    }

    public boolean getFindMeail(String memail){
        //1. 모든 엔티티에서 해당 필드의 값을 찾는다
        memberEntityRepository.findAll().forEach((m)->{
            if(m.getMemail().equals(memail)){

            }
        });

        //2. 특정 필드 조건으로 레코드/엔티티 검색
       MemberEntity result1 = memberEntityRepository.findByMemail(memail);
        System.out.println("result1 = " + result1);
        //3. 특정 필드의 조건으로 존재여부 검색
        boolean result2 = memberEntityRepository.existsByMemail(memail);
        System.out.println("result2 = " + result2);
        //4. 직접 native SQL 지원
        MemberEntity result3 = memberEntityRepository.findByMemailSQL(memail);
        System.out.println("result3 = " + result3);

        return result2;
    }



    public boolean doCheck(String id){
        List< MemberEntity > memberEntityList  = memberEntityRepository.findAll();
        for(int i=0; i<memberEntityList.size();i++){
            if(memberEntityList.get(i).getMemail().equals(id)){
                return true;
            }
        }
        return false;
    }

    //6.(로그인) 내가쓴글
    public List<BoardDto> findByMyBoardList(){
        //1. 세션에서 로그인된 회원 번호 찾기
        MemberDto loginDto = doLoginInfo();

        if(loginDto==null)return null;

        //1=========================양방향======================
            //1. 로그인된 회원번호를 이용한 엔티티 찾기
        Optional<MemberEntity> optionalMemberEntity
                = memberEntityRepository.findById(loginDto.getMno());
            //2. 만약에 엔티티가 존재하면
        if(optionalMemberEntity.isPresent()){
            //3. Optional 에서 엔티티 꺼내기
            MemberEntity memberEntity = optionalMemberEntity.get();
            //4. 내가 쓴글
            List<BoardEntity> result1 = memberEntity.getBoardEntityList();
            System.out.println("result1 = " + result1);

            List<BoardDto> boardDtoList = new ArrayList<>();
            result1.forEach((entity)->{
                boardDtoList.add(entity.toDto());
            });
            return boardDtoList;
        }else{
            return null;
        }

//        //2=========================단방향======================
//        List<Map<Object,Object>> result2
//                = memberEntityRepository.findByMyBoardSQL(loginDto.getMno());

    }
}