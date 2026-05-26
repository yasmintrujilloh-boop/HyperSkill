package taskmanagement.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.modelo.Comment;

import java.util.List;

@Repository
public interface  CommentRepositorio extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskIdOrderByIdDesc(Long taskId);
    long countByTaskId(Long taskId);
}
