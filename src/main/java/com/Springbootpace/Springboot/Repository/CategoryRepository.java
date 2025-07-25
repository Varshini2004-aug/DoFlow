package com.Springbootpace.Springboot.Repository;

import com.Springbootpace.Springboot.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}