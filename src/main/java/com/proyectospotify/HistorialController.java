package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*")
public class HistorialController {

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Historial> obtenerHistorial(@RequestParam(required = false) Long usuarioId) {
        if (usuarioId != null) {
            return historialRepository.findByUsuarioId(usuarioId);
        }
        return historialRepository.findAll();
    }

    @PostMapping("/reproducir/{cancionId}")
    public ResponseEntity<Historial> registrarReproduccion(
            @PathVariable Long cancionId,
            @RequestParam(required = false) Long usuarioId) {
        return cancionRepository.findById(cancionId)
                .map(cancion -> {
                    Historial historial = new Historial(cancion);
                    if (usuarioId != null) {
                        usuarioRepository.findById(usuarioId).ifPresent(historial::setUsuario);
                    }
                    Historial guardado = historialRepository.save(historial);
                    
                    cancion.setUltimaReproduccion(guardado.getFechaReproduccion());
                    cancionRepository.save(cancion);

                    return ResponseEntity.ok(guardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
