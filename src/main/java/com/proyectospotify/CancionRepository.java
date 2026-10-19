package com.proyectospotify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {

    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    
    List<Cancion> findByArtistaNombreContainingIgnoreCase(String nombreArtista);
    
    List<Cancion> findByGeneroContainingIgnoreCase(String genero);

    List<Cancion> findByFavoritoTrue();

    List<Cancion> findByUltimaReproduccionIsNotNullOrderByUltimaReproduccionDesc();

}