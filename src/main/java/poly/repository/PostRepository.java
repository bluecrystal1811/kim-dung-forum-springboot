package poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.entity.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatus(String status);
    List<Post> findByAuthorId(String authorId);
    List<Post> findByStatusAndAuthorId(String status, String authorId);
    List<Post> findByCategory(String category);
}
