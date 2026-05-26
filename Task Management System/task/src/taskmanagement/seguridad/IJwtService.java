package taskmanagement.seguridad;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generarToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean validaToken(String token, UserDetails userDetails);
    Claims obtenerClaims(String token);
}
