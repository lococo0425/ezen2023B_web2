package ezenweb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 해당 클래스와 연동DB내 테이블과 매핑
@Table(name = "board")
@Builder @Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEntity { //테이블 역할
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno; //게시물 번호
    @Column(name="title", length = 10, nullable = false, unique = true, columnDefinition = "longtext")
    private String btitle; // 게시물제목


    private boolean 필드0;

    private byte 필드1;
    private short 필드2;
    private long 필드3;

    private char 필드4;

    private float 필드5;
    private float 필드6;

}
