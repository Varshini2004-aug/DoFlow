package com.Springbootpace.Springboot.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.Springbootpace.Springboot.Entity.Task;
import com.Springbootpace.Springboot.Entity.TaskDto;
import com.Springbootpace.Springboot.Entity.User;
import com.Springbootpace.Springboot.Repository.TaskRepository;
import com.Springbootpace.Springboot.Repository.UserRepository;

/**
 * REST CRUD for tasks  –>  /api/tasks/…
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")            // ← CORS (keep / tighten as you like)
public class TaskController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskController(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    /* ─────────────── READ ─────────────── */

    /** GET /api/tasks  – all tasks (admin / debug) */
    @GetMapping
    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    /** GET /api/tasks/{id} */
    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id) {
        return taskRepo.findById(id)
                       .orElseThrow(() -> new TaskNotFound(id));
    }

    /** GET /api/tasks/user/{userId} – tasks for a single user */
    @GetMapping("/user/{userId}")
    public List<TaskDto> getTasksByUser(@PathVariable Long userId) {
        List<Task> tasks = taskRepo.findByUser_Id(userId);
        return tasks.stream().map(task -> {
            TaskDto dto = new TaskDto();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setDate(task.getDate());


            dto.setStatus(task.getStatus() != null ? task.getStatus().name() : null);
            dto.setCategoryName(task.getCategory()); // ← this will fix your error


            return dto;
        }).collect(Collectors.toList());
    }


    /* ─────────────── CREATE ─────────────── */

    /**
     * POST /api/tasks?userId={id}
     * (If you use Spring Security you can pull the user from the auth context
     *  instead of a query‑param.)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody Task payload,
                       @RequestParam Long userId) {

        User owner = userRepo.findById(userId)
                             .orElseThrow(() -> new RuntimeException("User not found"));
        payload.setUser(owner);
        return taskRepo.save(payload);
    }

    /* ─────────────── UPDATE ─────────────── */

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id,
                       @RequestBody Task payload) {

        return taskRepo.findById(id)
                       .map(t -> {
                           t.setTitle(payload.getTitle());
                           t.setDescription(payload.getDescription());
                           t.setDate(payload.getDate());
                           t.setStatus(payload.getStatus());
                           t.setCategory(payload.getCategory());
                           return taskRepo.save(t);
                       })
                       .orElseThrow(() -> new TaskNotFound(id));
    }

    /* ─────────────── DELETE ─────────────── */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskRepo.deleteById(id);
    }

    /* ─────────────── EXCEPTION ─────────────── */

    @ResponseStatus(HttpStatus.NOT_FOUND)
    private static class TaskNotFound extends RuntimeException {
        private static final long serialVersionUID = 1L;
        TaskNotFound(Long id) { super("Task " + id + " not found"); }
    }
    
    
    
}
