package taskmanagement.servicio;

import taskmanagement.modelo.Usuario;

public interface IUsuarioServicio {
    Usuario obtenerUsuarioPorEmail(String email);
    void guardarUsuario(Usuario usuario);
}
