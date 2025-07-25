// src/main/java/com/Springbootpace/Springboot/Repository/TaskRepository.java
package com.Springbootpace.Springboot.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Springbootpace.Springboot.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // good: Spring Data will translate to … where t.user.id = ?
    List<Task> findByUser_Id(Long userId);
}
