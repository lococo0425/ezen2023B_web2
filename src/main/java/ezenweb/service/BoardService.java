package ezenweb.service;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.model.dto.PageDto;
import ezenweb.model.entity.BoardEntity;
import ezenweb.model.entity.BoardImgEntity;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.entity.ReplyEntity;
import ezenweb.model.repository.BoardEntityRepository;
import ezenweb.model.repository.BoardUploadFileEntityRepository;
import ezenweb.model.repository.MemberEntityRepository;
import ezenweb.model.repository.ReplyEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    // * 리포지토리 객체
    @Autowired private BoardEntityRepository boardEntityRepository;
    @Autowired private MemberEntityRepository memberEntityRepository;
    @Autowired private ReplyEntityRepository replyEntityRepository;
    @Autowired private MemberService memberService;
    @Autowired private FileService fileService;
    @Autowired private BoardUploadFileEntityRepository boardUploadFileEntityRepository;
    // 1. C
    @Transactional
    public boolean postBoard( BoardDto boardDto){ //  ======= 테스트 ==========
        MemberDto loginDto =  memberService.doLoginInfo();
        if( loginDto == null ) return false;
        // 1. 로그인된 회원 엔티티 찾기
        Optional< MemberEntity > optionalMemberEntity = memberEntityRepository.findById( loginDto.getMno() );
        // 2. 찾은 엔티티가 존재하지 않으면 실패;
        if( !optionalMemberEntity.isPresent() ) return false;
        // 3. 엔티티 꺼내기
        MemberEntity memberEntity = optionalMemberEntity.get();
        // - 글쓰기
        BoardEntity saveBoard = boardEntityRepository.save( boardDto.toEntity() ) ;


        // - FK 대입
        if( saveBoard.getBno() >= 1){ // 글쓰기를 성공했으면
            saveBoard.setMemberEntity( memberEntity );
            for(MultipartFile m : boardDto.getUploadList()){
                BoardImgEntity boardImgEntity = BoardImgEntity.builder()
                        .bimg(fileService.fileUpload(m)).build();
                BoardImgEntity saveBoardUploadFile = boardUploadFileEntityRepository.save(boardImgEntity);
                saveBoardUploadFile.setBoardEntity(saveBoard);
                System.out.println("거ㅏ가가가가가"+saveBoardUploadFile);
            }
            return true;
        }
        return false;
    }
    // 2. R
    @Transactional
    public PageDto getBoard(int page , int view){
        // ======================= 1 ============================ //
        /*
        // 1. 리포지토리를 이용한 모든 엔티티( 테이블에 매핑 하기전 엔티티 )를 호출
        List<BoardEntity> result = boardEntityRepository.findAll( );
        // 2. Entity ---> Dto 변환한다
        List<BoardDto> boardDtoList = new ArrayList<>();
            // 1. 꺼내온 entity 을 순회한다
        for( int i = 0 ; i < result.size() ; i++ ){
                // 2. 하나씩 enriry 꺼낸다
            BoardEntity boardEntity = result.get(i);
                // 3. 해당 엔티티를 dto로 변환한다.
            BoardDto boardDto = boardEntity.toDto();
                    // ---------- 게시물안에 게시물사진
                    List<String> bimgList = new ArrayList<>();
                    for( int j = 0 ; j < boardEntity.getBoardImgEntityList().size() ; j++ ){
                        BoardImgEntity boardImgEntity = boardEntity.getBoardImgEntityList().get(j);
                        String bimg = boardImgEntity.getBimg();
                        bimgList.add( bimg );
                    }
                    boardDto.setBimgList( bimgList );
                // 4. 변환된 dto를 리스트에 담는다.
            boardDtoList.add( boardDto );
        }
        return boardDtoList;
        */
        // ======================= ===== ============================ //

        //1. pageable 인터페이스 를 이용한 페이징 처리
            //page-1 : pagealbe 은 0부터 시작  화면은 1 부터 시작 그렇다면 사용자가 1을 클릭했을때 스프링은 0이 나와야함.
        Pageable pageable = PageRequest.of(page-1,view);

            //* 페이징 처리된 게시물
            // 1. 페이징 처리된 엔티티 호출
        Page<BoardEntity> boardEntityPage = boardEntityRepository.findAll(pageable);

            //-- List 아닌 Page 타입일때 List 동일한 메소드 사용하고 추가 기능
        //전체 페이지 수
        System.out.println("boardEntityPage.getTotalPages() = " + boardEntityPage.getTotalPages());
        int count = boardEntityPage.getTotalPages();
        //전체 게시물 수
        System.out.println("boardEntityPage.getTotalElements() = " + boardEntityPage.getTotalElements());

            // 2. 엔티티를 DTO 로 변환
        List<Object> data =  boardEntityPage.stream().map( (boardEntity)->{
            return boardEntity.toDto();
        }).collect(Collectors.toList());

        //2. PageDto 반환 값 구성
            //1.
        PageDto pageDto = PageDto.builder().data(data)
                .page(page)
                .count(count)
                .build();

        return pageDto;
        // ======================= ===== ============================ //
    }
    // 3. U
    @Transactional
    public boolean putBoard(){
        BoardEntity boardEntity = boardEntityRepository.findById( 1 ).get();
        boardEntity.setBcontent("JPA수정테스트중");
        return false;
    }
    // 4. D
    @Transactional
    public boolean deleteBoard(int bno){
        //유효성 검사
        MemberDto memberDto = memberService.doLoginInfo();
        if(memberDto==null) return false;
        //내 게시물 확인
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(bno);

        if(optionalBoardEntity.isPresent()){
            if(optionalBoardEntity.get().getMemberEntity().getMno() == memberDto.getMno()){
                boardEntityRepository.deleteById(bno);
                return true;
            }

        }

        boardEntityRepository.deleteById(bno);

        return true;
    }
}