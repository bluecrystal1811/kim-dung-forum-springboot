package poly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String author;   // penName của người bình luận

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    private String createdAt;  // định dạng "HH:mm - dd/MM/yyyy"
}
