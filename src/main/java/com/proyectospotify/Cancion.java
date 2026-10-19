package com.proyectospotify;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "canciones")
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista; 

    private String genero;
    private Integer duracion;
    private String portada;
    private Boolean favorito = false;
    private LocalDateTime ultimaReproduccion;

    public Cancion() {
    }

    public Cancion(String titulo, Artista artista, String genero, Integer duracion, String portada) {
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.portada = portada;
        this.favorito = false;
        this.ultimaReproduccion = null;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Artista getArtista() { return artista; }
    public void setArtista(Artista artista) { this.artista = artista; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public String getPortada() { return portada; }
    public void setPortada(String portada) { this.portada = portada; }

    public Boolean getFavorito() { return favorito; }
    public void setFavorito(Boolean favorito) { this.favorito = favorito; }

    public LocalDateTime getUltimaReproduccion() { return ultimaReproduccion; }
    public void setUltimaReproduccion(LocalDateTime ultimaReproduccion) { this.ultimaReproduccion = ultimaReproduccion; }
}