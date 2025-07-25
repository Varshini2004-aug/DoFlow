package com.Springbootpace.Springboot.Controller;

import com.Springbootpace.Springboot.Entity.Category;
import com.Springbootpace.Springboot.Repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*") // Optional: for frontend access
public class CategoryController {

    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Category> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        // If no color is sent from frontend, assign a default one (optional)
        if (category.getColorCode() == null || category.getColorCode().isBlank()) {
            category.setColorCode("#f1f1f1");
        }
        return repo.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return repo.findById(id).map(existing -> {
            existing.setName(updatedCategory.getName());
            existing.setColorCode(updatedCategory.getColorCode()); // <-- added support
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
