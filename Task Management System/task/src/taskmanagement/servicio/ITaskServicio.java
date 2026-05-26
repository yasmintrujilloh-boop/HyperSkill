package taskmanagement.servicio;

import taskmanagement.dto.TaskCommentResponseDto;
import taskmanagement.dto.TaskRequestDto;
import taskmanagement.dto.TaskResponseDto;
import taskmanagement.modelo.Estatus;
import taskmanagement.modelo.Task;

import java.util.List;

public interface ITaskServicio {

    TaskResponseDto crearTask(TaskRequestDto dto, String email);

    TaskResponseDto obtenerTaskId(Long id);

    List<TaskCommentResponseDto> obtenerTasks();

    List<TaskCommentResponseDto> obtenerTasksPorAutor(String email);

    List<TaskCommentResponseDto> obtenerTasksPorAssignee(String email);

    List<TaskCommentResponseDto> obtenerTasksPorAutorYAssignee(String authorEmail,
                                                        String assigneeEmail);

    TaskResponseDto asignarTask(Long taskId,
                                String assigneeEmail,
                                String usuarioActualEmail);

    TaskResponseDto actualizarStatus(Long taskId,
                                     Estatus nuevoStatus,
                                     String usuarioActualEmail);

}
