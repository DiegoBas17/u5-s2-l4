package diegobasili.u5_s2_l3.entities;

import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostPayload {
    private String category;
    private String title;
    private String cover;
    private String content;
    private int timeForRead;
    private UUID author_id;
}
