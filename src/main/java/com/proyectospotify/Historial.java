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
@Table(name = "historial")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cancion_id")
    private Cancion cancion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaReproduccion;

    public Historial() {
        this.fechaReproduccion = LocalDateTime.now();
    }

    public Historial(Cancion cancion) {
        this.cancion = cancion;
        this.fechaReproduccion = LocalDateTime.now();
    }

    public Historial(Cancion cancion, Usuario usuario) {
        this.cancion = cancion;
        this.usuario = usuario;
        this.fechaReproduccion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cancion getCancion() { return cancion; }
    public void setCancion(Cancion cancion) { this.cancion = cancion; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFechaReproduccion() { return fechaReproduccion; }
    public void setFechaReproduccion(LocalDateTime fechaReproduccion) { this.fechaReproduccion = fechaReproduccion; }
}
