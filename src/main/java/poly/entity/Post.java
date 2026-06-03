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

    @Column(nullable = false, columnDefinition = "NVARCHAR(500)")
    private String title;

    @Column(columnDefinition = "NVARCHAR(1000)")
    private String excerpt;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String category;   // tên danh mục (chuỗi, không phải FK)

    @Column(length = 500)
    private String image;

    private String authorId;
    private String authorName;

    private LocalDate createdAt;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String status;     // "Chờ duyệt" | "Đã duyệt" | "Từ chối"
}
