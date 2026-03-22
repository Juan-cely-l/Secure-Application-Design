package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class LoginCOntroller {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        try {
            // Busca el usuario en el sistema
            var userDetails = userDetailsService.loadUserByUsername(username);

            // Compara la contraseña ingresada con el hash guardado
            // passwordEncoder.matches() hace la magia de BCrypt
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login exitoso",
                    "user", username
                ));
            } else {
                return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Contraseña incorrecta"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Usuario no encontrado"
            ));
        }
    }

     @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(Map.of("message", "Greetings from Secure Spring Boot!"));
    }
    
}
