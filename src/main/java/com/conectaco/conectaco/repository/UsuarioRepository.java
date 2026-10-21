package com.conectaco.conectaco.repository;

import com.conectaco.conectaco.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    // Spring arma la consulta a partir del nombre del metodo
    boolean existsByEmailIgnoreCase(String email);
}
