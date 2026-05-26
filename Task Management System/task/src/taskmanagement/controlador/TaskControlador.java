package taskmanagement.controlador;

import taskmanagement.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskmanagement.servicio.ITaskServicio;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskControlador {


    private final ITaskServicio taskServicio;

    public TaskControlador(ITaskServicio taskServicio) {
        this.taskServicio = taskServicio;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid
                                                          @RequestBody TaskRequestDto taskRequestDto,
                                                      Authentication authentication) {
        String email = authentication.getName();
        TaskResponseDto taskResponseDto = this.taskServicio.crearTask(taskRequestDto, email);
        return ResponseEntity.ok(taskResponseDto);
    }
    @PutMapping("/{taskId}/assign")
    public ResponseEntity<TaskResponseDto> assignTask(
            @PathVariable Long taskId,
            @Valid @RequestBody AssignTaskDto assignTaskDto,
            Authentication authentication) {

        TaskResponseDto response =
                this.taskServicio.asignarTask(
                        taskId,
                        assignTaskDto.getAssignee(),
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDto> updateStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateStatusDto updateStatusDto,
            Authentication authentication) {

        TaskResponseDto response =
                this.taskServicio.actualizarStatus(
                        taskId,
                        updateStatusDto.getStatus(),
                        authentication.getName()
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskCommentResponseDto>> getTasks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String assignee) {

        List<TaskCommentResponseDto> tasks;
        if (author != null && assignee != null) {
            tasks = this.taskServicio.obtenerTasksPorAutorYAssignee(
                    author,
                    assignee
            );
        } else if (author != null) {
            tasks = this.taskServicio.obtenerTasksPorAutor(author);
        } else if (assignee != null) {
            tasks = this.taskServicio.obtenerTasksPorAssignee(assignee);
        } else {
            tasks = this.taskServicio.obtenerTasks();
        }
        return ResponseEntity.ok(tasks);
    }

}
