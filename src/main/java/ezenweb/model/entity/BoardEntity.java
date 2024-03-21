package ezenweb.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity // 해당 클래스와 연동DB내 테이블과 매핑
@Builder @Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEntity { //테이블 역할
    @Id // PK
    private int bno; //게시물 번호
    private String btitle; // 게시물제목

}
