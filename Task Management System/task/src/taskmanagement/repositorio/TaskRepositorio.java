package taskmanagement.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.modelo.Task;

import java.util.List;

@Repository
public interface TaskRepositorio extends JpaRepository<Task, Long> {

    List<Task> findAllByOrderByIdDesc();

    List<Task> findAllByAuthorEmailIgnoreCaseOrderByIdDesc(String email);

    List<Task> findAllByAssigneeEmailIgnoreCaseOrderByIdDesc(String email);

    List<Task> findAllByAuthorEmailIgnoreCaseAndAssigneeEmailIgnoreCaseOrderByIdDesc(
            String authorEmail,
            String assigneeEmail);
}
