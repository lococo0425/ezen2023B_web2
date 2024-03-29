package ezenweb.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@SuperBuilder
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@ToString
public class BaseTimeDto {
    private LocalDateTime cdate;
    private LocalDateTime udate;

}
