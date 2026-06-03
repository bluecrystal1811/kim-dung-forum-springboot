package poly.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.entity.Post;
import poly.repository.PostRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepo;

    // GET /posts                     — tất cả bài viết
    // GET /posts?status=Đã+duyệt     — lọc theo status
    // GET /posts?authorId=u01        — lọc theo tác giả
    // GET /posts?status=X&authorId=Y — kết hợp
    @GetMapping
    public List<Post> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String authorId) {

        if (status != null && authorId != null) {
            return postRepo.findByStatusAndAuthorId(status, authorId);
        }
        if (status != null) {
            return postRepo.findByStatus(status);
        }
        if (authorId != null) {
            return postRepo.findByAuthorId(authorId);
        }
        return postRepo.findAll();
    }

    // GET /posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        return postRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /posts  — tạo bài mới (mặc định "Chờ duyệt")
    @PostMapping
    public Post create(@RequestBody Post post) {
        post.setId(null); // luôn để DB tự sinh IDENTITY, bỏ qua id client gửi lên
        if (post.getCreatedAt() == null) {
            post.setCreatedAt(LocalDate.now());
        }
        if (post.getStatus() == null || post.getStatus().isBlank()) {
            post.setStatus("Chờ duyệt");
        }
        if (post.getImage() == null || post.getImage().isBlank()) {
            post.setImage("/images/banner4.jpg");
        }
        return postRepo.save(post);
    }

    // PUT /posts/{id}  — cập nhật toàn bộ bài viết
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post updated) {
        return postRepo.findById(id).map(post -> {
            post.setTitle(updated.getTitle());
            post.setExcerpt(updated.getExcerpt());
            post.setContent(updated.getContent());
            post.setCategory(updated.getCategory());
            post.setImage(updated.getImage());
            post.setStatus(updated.getStatus());
            return ResponseEntity.ok(postRepo.save(post));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /posts/{id}  — cập nhật một phần (status, ...)
    @PatchMapping("/{id}")
    public ResponseEntity<Post> patch(@PathVariable Long id, @RequestBody Post updated) {
        return postRepo.findById(id).map(post -> {
            if (updated.getTitle() != null)    post.setTitle(updated.getTitle());
            if (updated.getExcerpt() != null)  post.setExcerpt(updated.getExcerpt());
            if (updated.getContent() != null)  post.setContent(updated.getContent());
            if (updated.getCategory() != null) post.setCategory(updated.getCategory());
            if (updated.getImage() != null)    post.setImage(updated.getImage());
            if (updated.getStatus() != null)   post.setStatus(updated.getStatus());
            return ResponseEntity.ok(postRepo.save(post));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!postRepo.existsById(id)) return ResponseEntity.notFound().build();
        postRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
