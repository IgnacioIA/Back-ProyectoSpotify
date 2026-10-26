package com.proyectospotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        playlist.setCanciones(java.util.Collections.emptyList());
        return playlistRepository.save(playlist);
    }

    @PostMapping("/{playlistId}/canciones/{cancionId}")
    public ResponseEntity<Playlist> agregarCancionAPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long cancionId) {

        return playlistRepository.findById(playlistId).flatMap(playlist -> 
            cancionRepository.findById(cancionId).map(cancion -> {
                
                // Verificamos si la canción no está ya en la playlist para no duplicarla
                if (!playlist.getCanciones().contains(cancion)) {
                    playlist.getCanciones().add(cancion);
                    Playlist playlistActualizada = playlistRepository.save(playlist);
                    return ResponseEntity.ok(playlistActualizada);
                }
                
                return ResponseEntity.ok(playlist);
            })
        ).orElse(ResponseEntity.notFound().build());
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