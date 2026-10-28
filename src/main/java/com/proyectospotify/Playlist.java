package com.proyectospotify;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relación Muchos a Muchos con Cancion
    @ManyToMany
    @JoinTable(
        name = "playlist_canciones",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "cancion_id")
    )
    private List<Cancion> canciones = new ArrayList<>();

    public Playlist() {
    }

    public Playlist(String nombre) {
        this.nombre = nombre;
    }

    public Playlist(String nombre, Usuario usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Cancion> getCanciones() { return canciones; }
    public void setCanciones(List<Cancion> canciones) { this.canciones = canciones; }
}
