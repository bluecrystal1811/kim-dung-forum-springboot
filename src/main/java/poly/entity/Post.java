package poly.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 1000)
    private String excerpt;

    @Lob
    @Column(columnDefinition = "text")
    private String content;

    @Column(length = 100)
    private String category;   // tên danh mục (chuỗi, không phải FK)

    @Column(length = 500)
    private String image;

    @Column(name = "author_id", length = 100)
    private String authorId;

    @Column(name = "author_name", length = 255)
    private String authorName;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(length = 50)
    private String status;     // "Chờ duyệt" | "Đã duyệt" | "Từ chối"
}
