package taskmanagement.controlador;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import taskmanagement.modelo.Usuario;
import taskmanagement.servicio.IUsuarioServicio;

import java.util.List;

@RestController
public class UsuarioControlador {
    private final IUsuarioServicio usuarioServicio;

    public UsuarioControlador(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/api/accounts")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody Usuario usuario) {
        usuario.setEmail(usuario.getEmail().toLowerCase());
        try {
            Usuario usuarioRegistrado = this.usuarioServicio.obtenerUsuarioPorEmail(usuario.getEmail());
            if (usuarioRegistrado != null)  {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
                //throw new IllegalArgumentException("El email ya está registrado");
            }
            this.usuarioServicio.guardarUsuario(usuario);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
