package poly.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.entity.User;
import poly.repository.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;

    // GET /users  — Vue auth store tải toàn bộ danh sách để filter cục bộ
    @GetMapping
    public List<User> getAll() {
        return userRepo.findAll();
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /users  — đăng ký tài khoản mới
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email đã tồn tại."));
        }

        // Tự sinh id dạng "u01", "u02", ...
        if (user.getId() == null || user.getId().isBlank()) {
            List<User> all = userRepo.findAll();
            int next = all.stream()
                    .mapToInt(u -> {
                        try { return Integer.parseInt(u.getId().replace("u", "")); }
                        catch (Exception e) { return 0; }
                    })
                    .max().orElse(0) + 1;
            user.setId(String.format("u%02d", next));
        }

        return ResponseEntity.ok(userRepo.save(user));
    }

    // PATCH /users/{id}  — cập nhật profile (tên, bút danh, avatar, badge)
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, Object> fields) {
        return userRepo.findById(id).map(user -> {
            if (fields.containsKey("fullName"))  user.setFullName((String) fields.get("fullName"));
            if (fields.containsKey("penName"))   user.setPenName((String) fields.get("penName"));
            if (fields.containsKey("avatar"))    user.setAvatar((String) fields.get("avatar"));
            if (fields.containsKey("badge"))     user.setBadge((String) fields.get("badge"));
            if (fields.containsKey("password"))  user.setPassword((String) fields.get("password"));
            if (fields.containsKey("role"))      user.setRole((String) fields.get("role"));
            return ResponseEntity.ok(userRepo.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!userRepo.existsById(id)) return ResponseEntity.notFound().build();
        userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
