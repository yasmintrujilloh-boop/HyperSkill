package taskmanagement.servicio;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanagement.modelo.Usuario;
import taskmanagement.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio implements IUsuarioServicio {
     private final UsuarioRepositorio usuarioRepositorio;
     private final PasswordEncoder passwordEncoder;

     public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
         this.usuarioRepositorio = usuarioRepositorio;
         this.passwordEncoder = passwordEncoder;
     }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return this.usuarioRepositorio.findByEmail(email);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
         usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
         this.usuarioRepositorio.save(usuario);
    }
}
