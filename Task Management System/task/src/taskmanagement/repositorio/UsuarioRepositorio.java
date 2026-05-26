package taskmanagement.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.modelo.Usuario;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario,Integer> {
    Usuario findByEmail(String email);

}
