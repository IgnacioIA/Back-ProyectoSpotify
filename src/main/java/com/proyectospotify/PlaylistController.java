package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "*")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private CancionRepository cancionRepository;

    @GetMapping
    public List<Playlist> obtenerTodas() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> obtenerPorId(@PathVariable Long id) {
        return playlistRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Playlist crearPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getCanciones() == null) {
            playlist.setCanciones(new ArrayList<>());
        }
        return playlistRepository.save(playlist);
    }

    @PostMapping("/{playlistId}/canciones/{cancionId}")
    public ResponseEntity<Playlist> agregarCancionAPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long cancionId) {

        return playlistRepository.findById(playlistId).flatMap(playlist -> 
            cancionRepository.findById(cancionId).map(cancion -> {
                if (playlist.getCanciones() == null) {
                    playlist.setCanciones(new ArrayList<>());
                }
                boolean yaExiste = playlist.getCanciones().stream().anyMatch(c -> c.getId() != null && c.getId().equals(cancionId));
                if (!yaExiste) {
                    playlist.getCanciones().add(cancion);
                    Playlist playlistActualizada = playlistRepository.save(playlist);
                    return ResponseEntity.ok(playlistActualizada);
                }
                return ResponseEntity.ok(playlist);
            })
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{playlistId}/canciones/{cancionId}")
    public ResponseEntity<Playlist> eliminarCancionDePlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long cancionId) {

        return playlistRepository.findById(playlistId).map(playlist -> {
            if (playlist.getCanciones() != null) {
                boolean removido = playlist.getCanciones().removeIf(c -> c.getId() != null && c.getId().equals(cancionId));
                if (removido) {
                    Playlist playlistActualizada = playlistRepository.save(playlist);
                    return ResponseEntity.ok(playlistActualizada);
                }
            }
            return ResponseEntity.ok(playlist);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlaylist(@PathVariable Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlistRepository.delete(playlist);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
