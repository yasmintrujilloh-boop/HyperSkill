package taskmanagement.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dto.TokenResponseDto;
import taskmanagement.seguridad.IJwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    private final IJwtService tokenServicio;

    public AuthControlador(IJwtService tokenServicio) {
        this.tokenServicio = tokenServicio;
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> generarToken(
            Authentication authentication) {

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                tokenServicio.generarToken(userDetails);

        TokenResponseDto response =
                new TokenResponseDto(token);

        return ResponseEntity.ok(response);
    }
}
