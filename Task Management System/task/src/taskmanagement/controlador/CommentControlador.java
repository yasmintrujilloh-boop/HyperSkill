package taskmanagement.controlador;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dto.CommentRequestDto;
import taskmanagement.dto.CommentResponseDto;
import taskmanagement.servicio.ICommentServicio;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class CommentControlador {

    private final ICommentServicio  commentServicio;

    public CommentControlador(ICommentServicio commentServicio) {
        this.commentServicio = commentServicio;
    }


    @PostMapping("/{taskId}/comments")
    public ResponseEntity<Void> crearComentario(@PathVariable Long taskId,
                                                @Valid @RequestBody CommentRequestDto commentRequestDto,
                                                Authentication authentication) {

        String email = authentication.getName();

        this.commentServicio.crearComentario(
                taskId,
                commentRequestDto,
                email
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<CommentResponseDto>>
    obtenerComentarios(
            @PathVariable Long taskId
    ) {

        List<CommentResponseDto> comentarios =
                this.commentServicio
                        .obtenerComentarios(taskId);

        return ResponseEntity.ok(comentarios);
    }
}
