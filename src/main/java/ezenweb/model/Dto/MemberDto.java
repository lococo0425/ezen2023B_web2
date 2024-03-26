package ezenweb.model.Dto;

import ezenweb.model.entity.MemberEntity;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@Builder@ToString
public class MemberDto {

    private int mno;

    private String memail;

    private String mpassword;

    private String mname;

    private String mrol;

    // -dto 엔티티로 변환하는 메소드
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .mname(this.mname)
                .memail(this.memail)
                .mpassword(this.mpassword)
                .mrol(this.mrol)
                .build();
    }
}
