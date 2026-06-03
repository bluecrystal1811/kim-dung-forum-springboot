package poly.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.entity.Category;
import poly.repository.CategoryRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepo;

    // GET /categories
    @GetMapping
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    // POST /categories
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Category category) {
        if (categoryRepo.existsByName(category.getName())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Danh mục đã tồn tại."));
        }
        return ResponseEntity.ok(categoryRepo.save(category));
    }

    // DELETE /categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoryRepo.existsById(id)) return ResponseEntity.notFound().build();
        categoryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
