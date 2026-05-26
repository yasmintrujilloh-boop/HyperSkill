package taskmanagement.servicio;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import taskmanagement.dto.CommentRequestDto;
import taskmanagement.dto.CommentResponseDto;

import taskmanagement.modelo.Comment;
import taskmanagement.modelo.Task;
import taskmanagement.modelo.Usuario;
import taskmanagement.repositorio.CommentRepositorio;
import taskmanagement.repositorio.TaskRepositorio;

import java.util.List;

@Service
public class CommentServicio implements ICommentServicio {
    private final CommentRepositorio commentRepositorio;
    private final TaskRepositorio taskRepositorio;
    private final IUsuarioServicio usuarioServicio;
    public CommentServicio( CommentRepositorio commentRepositorio,
                            TaskRepositorio taskRepositorio,
                            IUsuarioServicio usuarioServicio) {
        this.commentRepositorio = commentRepositorio;
        this.taskRepositorio = taskRepositorio;
        this.usuarioServicio = usuarioServicio;
    }
    @Override
    public void crearComentario(Long taskId,
                                CommentRequestDto commentRequestDto,
                                String email) {
        Task task = this.taskRepositorio.findById(taskId).orElseThrow(() ->
                             new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        Usuario usuario = this.usuarioServicio.obtenerUsuarioPorEmail(email);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setText(commentRequestDto.getText());
        comment.setAuthor(usuario);
        this.commentRepositorio.save(comment);




    }

    @Override
    public List<CommentResponseDto> obtenerComentarios(Long taskId) {
        this.taskRepositorio.findById(taskId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));


        return this.commentRepositorio
                .findByTaskIdOrderByIdDesc(taskId)
                .stream()
                .map(this::convertirDto)
                .toList();
    }

    private CommentResponseDto convertirDto(Comment comment) {

        CommentResponseDto dto =
                new CommentResponseDto();

        dto.setId(comment.getId().toString());

        dto.setTask_id(
                comment.getTask()
                        .getId()
                        .toString()
        );

        dto.setText(comment.getText());

        dto.setAuthor(
                comment.getAuthor()
                        .getEmail()
                        .toLowerCase()
        );

        return dto;
    }
}
