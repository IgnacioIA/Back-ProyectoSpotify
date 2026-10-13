package com.proyectospotify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {

    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    List<Cancion> findByArtistaContainingIgnoreCase(String artista);
    List<Cancion> findByGeneroContainingIgnoreCase(String genero); 

}