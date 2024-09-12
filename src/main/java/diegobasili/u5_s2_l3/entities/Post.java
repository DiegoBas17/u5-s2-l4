package diegobasili.u5_s2_l3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Post {
    @Id
    @GeneratedValue
    private UUID id;
    private String category;
    private String title;
    private String cover;
    private String content;
    private int timeForRead;
    @ManyToOne
    private Author author;

    public Post(String category, String title, String cover, int timeForRead, String content, Author author) {
        this.category = category;
        this.title = title;
        this.cover = cover;
        this.timeForRead = timeForRead;
        this.content = content;
        this.author = author;
    }
}
