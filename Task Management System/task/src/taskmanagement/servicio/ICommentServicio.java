package taskmanagement.servicio;

import taskmanagement.dto.CommentRequestDto;
import taskmanagement.dto.CommentResponseDto;

import java.util.List;

public interface ICommentServicio {

    void crearComentario(Long taskId,
                         CommentRequestDto commentRequestDto,
                         String email);

    List<CommentResponseDto> obtenerComentarios(Long taskId);
}
