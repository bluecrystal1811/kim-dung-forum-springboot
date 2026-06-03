package poly.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.entity.Comment;
import poly.repository.CommentRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepo;

    // GET /comments           — tất cả bình luận
    // GET /comments?postId=3  — bình luận theo bài viết
    @GetMapping
    public List<Comment> getAll(@RequestParam(required = false) Long postId) {
        if (postId != null) {
            return commentRepo.findByPostId(postId);
        }
        return commentRepo.findAll();
    }

    // POST /comments  — thêm bình luận mới
    @PostMapping
    public Comment create(@RequestBody Comment comment) {
        comment.setId(null); // luôn để DB tự sinh IDENTITY
        if (comment.getCreatedAt() == null || comment.getCreatedAt().isBlank()) {
            LocalDateTime now = LocalDateTime.now();
            String formatted = now.format(DateTimeFormatter.ofPattern("HH:mm")) +
                    " - " + now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            comment.setCreatedAt(formatted);
        }
        return commentRepo.save(comment);
    }

    // DELETE /comments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!commentRepo.existsById(id)) return ResponseEntity.notFound().build();
        commentRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
