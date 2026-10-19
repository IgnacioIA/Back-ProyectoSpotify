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

    @GetMapping
    public List<Historial> obtenerHistorial() {
        return historialRepository.findAll();
    }

    @PostMapping("/reproducir/{cancionId}")
    public ResponseEntity<Historial> registrarReproduccion(@PathVariable Long cancionId) {
        return cancionRepository.findById(cancionId)
                .map(cancion -> {
                    Historial historial = new Historial(cancion);
                    Historial guardado = historialRepository.save(historial);
                    
                    // Opcional: si también quieres actualizar la última reproducción en la canción
                    cancion.setUltimaReproduccion(guardado.getFechaReproduccion());
                    cancionRepository.save(cancion);

                    return ResponseEntity.ok(guardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}