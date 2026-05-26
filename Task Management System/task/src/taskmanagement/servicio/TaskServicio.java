package taskmanagement.servicio;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import taskmanagement.dto.TaskCommentResponseDto;
import taskmanagement.dto.TaskRequestDto;
import taskmanagement.dto.TaskResponseDto;
import org.springframework.stereotype.Service;
import taskmanagement.modelo.Estatus;
import taskmanagement.modelo.Task;
import taskmanagement.modelo.Usuario;
import taskmanagement.repositorio.CommentRepositorio;
import taskmanagement.repositorio.TaskRepositorio;

import java.util.List;

@Service
public class TaskServicio implements ITaskServicio {
    private final TaskRepositorio taskRepositorio;
    private final UsuarioServicio usuarioServicio;
    private final CommentRepositorio commentRepositorio;

    public TaskServicio(TaskRepositorio taskRepositorio,
                        UsuarioServicio usuarioServicio, CommentRepositorio commentRepositorio) {
        this.taskRepositorio = taskRepositorio;
        this.usuarioServicio = usuarioServicio;
        this.commentRepositorio = commentRepositorio;
    }

    @Override
    public TaskResponseDto crearTask(TaskRequestDto dto, String email) {
        Usuario author = usuarioServicio.obtenerUsuarioPorEmail(email);
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        task.setStatus(Estatus.CREATED);
        task.setAuthor(author);

        return convertirDto(this.taskRepositorio.save(task));
    }

    @Override
    public TaskResponseDto obtenerTaskId(Long id) {
        return convertirDto(this.taskRepositorio.findById(id).orElse(null));

    }

    @Override
    public List<TaskCommentResponseDto> obtenerTasks() {
        return this.taskRepositorio.findAllByOrderByIdDesc()
                .stream()
                .map(this::convertirDtoComment)
                .toList();
    }

    @Override
    public List<TaskCommentResponseDto> obtenerTasksPorAutor(String email) {
        return this.taskRepositorio.findAllByAuthorEmailIgnoreCaseOrderByIdDesc(email)
                .stream()
                .map(this::convertirDtoComment)
                .toList();
    }

    @Override
    public List<TaskCommentResponseDto> obtenerTasksPorAssignee(String email) {
        return this.taskRepositorio.findAllByAssigneeEmailIgnoreCaseOrderByIdDesc(email)
                .stream()
                .map(this::convertirDtoComment)
                .toList();
    }

    @Override
    public List<TaskCommentResponseDto> obtenerTasksPorAutorYAssignee(
            String authorEmail,
            String assigneeEmail) {

        return this.taskRepositorio
                .findAllByAuthorEmailIgnoreCaseAndAssigneeEmailIgnoreCaseOrderByIdDesc(
                        authorEmail,
                        assigneeEmail
                )
                .stream()
                .map(this::convertirDtoComment)
                .toList();
    }



    @Override
    public TaskResponseDto asignarTask(Long taskId,
                                       String assigneeEmail,
                                       String usuarioActualEmail) {

        Task task = this.taskRepositorio.findById(taskId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!task.getAuthor()
                .getEmail()
                .equalsIgnoreCase(usuarioActualEmail)) {

            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (assigneeEmail.equalsIgnoreCase("none")) {

            task.setAssignee(null);

        } else {

            Usuario assignee =
                    this.usuarioServicio.obtenerUsuarioPorEmail(
                            assigneeEmail.toLowerCase()
                    );

            if (assignee == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            task.setAssignee(assignee);
        }

        Task taskActualizada = this.taskRepositorio.save(task);

        return convertirDto(taskActualizada);
    }

    @Override
    public TaskResponseDto actualizarStatus(Long taskId, Estatus nuevoStatus, String usuarioActualEmail) {
        Task task = this.taskRepositorio.findById(taskId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean esAuthor = task.getAuthor()
                .getEmail()
                .equalsIgnoreCase(usuarioActualEmail);

        boolean esAssignee =
                task.getAssignee() != null &&
                        task.getAssignee()
                                .getEmail()
                                .equalsIgnoreCase(usuarioActualEmail);

        if (!esAuthor && !esAssignee) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        task.setStatus(nuevoStatus);

        Task taskActualizada =
                this.taskRepositorio.save(task);

        return convertirDto(taskActualizada);
    }


    private TaskResponseDto convertirDto(Task task) {

        TaskResponseDto dto = new TaskResponseDto();

        dto.setId(task.getId().toString());

        dto.setTitle(task.getTitle());

        dto.setDescription(task.getDescription());

        dto.setStatus(task.getStatus().name());

        dto.setAuthor(task.getAuthor().getEmail().toLowerCase());

        if (task.getAssignee() != null) {
            dto.setAssignee(task.getAssignee().getEmail().toLowerCase());
        } else {
            dto.setAssignee("none");
        }

        return dto;
    }

    private TaskCommentResponseDto convertirDtoComment(Task task) {

        TaskCommentResponseDto dto = new TaskCommentResponseDto();

        dto.setId(task.getId().toString());

        dto.setTitle(task.getTitle());

        dto.setDescription(task.getDescription());

        dto.setStatus(task.getStatus().name());

        dto.setAuthor(task.getAuthor().getEmail().toLowerCase());

        if (task.getAssignee() != null) {
            dto.setAssignee(task.getAssignee().getEmail().toLowerCase());
        } else {
            dto.setAssignee("none");
        }
        long totalComentarios =
                this.commentRepositorio.countByTaskId(
                        task.getId()
                );

        dto.setTotal_comments(totalComentarios);
        return dto;
    }




}
