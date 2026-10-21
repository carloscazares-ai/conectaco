package com.conectaco.conectaco.repository;

import com.conectaco.conectaco.entity.UsuarioEntity;
import com.conectaco.conectaco.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    // Spring arma la consulta a partir del nombre del metodo
    boolean existsByEmailIgnoreCase(String email);

    // Igual, pero sin contar al propio usuario (para cuando lo modificas)
    boolean existsByEmailIgnoreCaseAndIdUsuarioNot(String email, Integer idUsuario);

    // GET /v1/usuarios?nombre=...&email=...&tipoUsuario=...
    // Cada filtro es opcional: si viene null, no se toma en cuenta.
    @Query("select u from UsuarioEntity u " +
            "where (:nombre is null or lower(u.nombre) like lower(concat('%', :nombre, '%'))) " +
            "and (:email is null or lower(u.email) = lower(:email)) " +
            "and (:tipoUsuario is null or u.tipoUsuario = :tipoUsuario)")
    List<UsuarioEntity> buscar(String nombre, String email, TipoUsuario tipoUsuario);
}
