package ezenweb.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class PageDto {
    int page ;      //현재 페이지
    int count ;     //총 페이지
    List<Object> data;    // 본문 내용들...

}
