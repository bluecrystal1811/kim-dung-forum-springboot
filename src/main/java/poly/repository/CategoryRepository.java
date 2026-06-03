package poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
