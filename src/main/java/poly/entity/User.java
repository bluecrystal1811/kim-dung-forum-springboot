package poly.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(length = 10)
    private String id;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String fullName;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String penName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(length = 20)
    private String role;       // "author" | "moderator"

    private int joinedYear;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String badge;

    @Column(length = 500)
    private String avatar;
}
