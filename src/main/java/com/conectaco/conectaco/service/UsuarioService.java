package com.conectaco.conectaco.service;

import com.conectaco.conectaco.dto.UsuarioDTO;
import com.conectaco.conectaco.enums.TipoUsuario;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO save(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> findAll();

    // Filtros opcionales (null = sin filtro)
    List<UsuarioDTO> buscar(String nombre, String email, TipoUsuario tipoUsuario);

    UsuarioDTO findById(Integer idUsuario);

    // PUT: reemplaza todos los datos (hay que mandar nombre, email y password)
    UsuarioDTO update(Integer idUsuario, UsuarioDTO usuarioDTO);

    // PATCH: cambia solo los datos que se manden
    UsuarioDTO patch(Integer idUsuario, UsuarioDTO usuarioDTO);

    void delete(Integer idUsuario);
}
