package ezenweb.service;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }

    //-(시큐리티) 로그인 커스텀
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // 1. 로그인 창에서 입력받은 아이디
        System.out.println("username = " + memail);
        // 2. 입력받은 아이디로 실제 아이디와 실제 (암호화된)패스워드
            // 2-1 memail 이용한 회원엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail(memail);
        // 만약에 해당 입력한 이메일의 엔티티가 없으면
        if(memberEntity == null){return null;} //userDetail 이 없다...

        //- ROLE 부여
        List<GrantedAuthority> 등급목록 = new ArrayList<>();
        등급목록.add(new SimpleGrantedAuthority("ROLE_"+memberEntity.getMrol())); // ROLE_등급명


        // 3. UserDetails 반환[ 1.아이디 2.패스워드 ]
            //UserDetails 목적 : Token에 입력받은 아이디/패스워드 검정하기 위한 실제 정보 반환
        UserDetails userDetails = User.builder()
                .username(memberEntity.getMemail())     //실제 아이디
                .password(memberEntity.getMpassword())  //실제 비밀번호
                .authorities(등급목록)    //ROLE 등급
                .build();

        return userDetails;
    }

    @Autowired MemberEntityRepository memberEntityRepository;

    // 1. 회원가입 ( 시큐리티 사용시 패스워드 암호화 필수!!)
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
        //(시큐리티 사용하기 전)
//        Object object = request.getSession().getAttribute("loginInfo");
//        if( object != null ){
//            return (MemberDto)object; // 강제형변환
//        }
//        return null;
        //(시큐리티 사용 했을때) Principal : 본인/주역/주체자 : 브라우저마다 1개
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(object);
        // 로그인 상태가 아니면
        if(object.equals("anonymousUser")){
            return null;
        }
        // 로그인 상태이면 UserDetails 타입 변환
        UserDetails userDetails = (UserDetails) object;
        // 회원정보(비밀번호 제외 권장)
        MemberEntity memberEntity = memberEntityRepository.findByMemail(userDetails.getUsername());


        return MemberDto.builder()
                .memail(memberEntity.getMemail())
                .mname(memberEntity.getMname())
                .mno(memberEntity.getMno())
                .build();

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