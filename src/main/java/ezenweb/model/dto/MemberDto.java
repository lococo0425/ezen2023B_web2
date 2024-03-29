package ezenweb.model.dto;

import ezenweb.model.entity.MemberEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @SuperBuilder @ToString
public class MemberDto extends BaseTimeDto{
    private int mno;
    private String memail;
    private String mpassword;
    private String mname;
    private String mrol;

    // - dto를 엔티티로 변환하는 메소드 // C
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno( this.mno )
                .mname( this.mname )
                .memail( this.memail )
                .mpassword( this.mpassword )
                .mrol( this.mrol )
                .build();
        // this ?? : 해당 메소드를 호출한 인스턴스
    }

}