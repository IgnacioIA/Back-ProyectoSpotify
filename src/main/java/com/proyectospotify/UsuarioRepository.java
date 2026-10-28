package com.proyectospotify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
}
