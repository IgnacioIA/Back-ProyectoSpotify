package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> payload) {
        String nombreUsuario = payload.get("nombreUsuario");
        String email = payload.get("email");
        String contrasenia = payload.get("contrasenia");

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            contrasenia == null || contrasenia.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Todos los campos son obligatorios.");
            return ResponseEntity.badRequest().body(error);
        }

        nombreUsuario = nombreUsuario.trim();
        email = email.trim();

        if (usuarioRepository.existsByNombreUsuario(nombreUsuario)) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El nombre de usuario ya está registrado.");
            return ResponseEntity.badRequest().body(error);
        }

        if (usuarioRepository.existsByEmail(email)) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "El correo electrónico ya está registrado.");
            return ResponseEntity.badRequest().body(error);
        }

        Usuario nuevoUsuario = new Usuario(nombreUsuario, email, contrasenia);
        Usuario guardado = usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Map<String, String> payload) {
        String identificador = payload.get("identificador");
        String contrasenia = payload.get("contrasenia");

        if (identificador == null || identificador.trim().isEmpty() ||
            contrasenia == null || contrasenia.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Debe ingresar usuario o correo y contraseña.");
            return ResponseEntity.badRequest().body(error);
        }

        identificador = identificador.trim();

        // Buscar por nombre de usuario o por email
        Usuario usuario = usuarioRepository.findByNombreUsuarioOrEmail(identificador, identificador)
                .orElse(null);

        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Usuario, correo o contraseña incorrectos.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        return ResponseEntity.ok(usuario);
    }
}
