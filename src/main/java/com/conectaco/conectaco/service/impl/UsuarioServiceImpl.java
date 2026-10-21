package com.conectaco.conectaco.service.impl;

import com.conectaco.conectaco.dto.UsuarioDTO;
import com.conectaco.conectaco.entity.NegocioEntity;
import com.conectaco.conectaco.entity.TuristaEntity;
import com.conectaco.conectaco.entity.UsuarioEntity;
import com.conectaco.conectaco.enums.EstadoVerificacion;
import com.conectaco.conectaco.enums.TipoUsuario;
import com.conectaco.conectaco.exception.ApiException;
import com.conectaco.conectaco.mapper.UsuarioMapper;
import com.conectaco.conectaco.repository.NegocioRepository;
import com.conectaco.conectaco.repository.TuristaRepository;
import com.conectaco.conectaco.repository.UsuarioRepository;
import com.conectaco.conectaco.service.UsuarioService;
import com.conectaco.conectaco.util.ValidadorPassword;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final TuristaRepository turistaRepository;
    private final NegocioRepository negocioRepository;

    public UsuarioServiceImpl(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository,
                              TuristaRepository turistaRepository, NegocioRepository negocioRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
        this.turistaRepository = turistaRepository;
        this.negocioRepository = negocioRepository;
    }

    // 1. Guarda el usuario en la tabla Usuarios.
    // 2. Segun su tipo, crea su registro en Turistas o en Negocios con el id del usuario.
    // @Transactional: si falla el paso 2, tampoco se guarda el usuario (todo o nada).
    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {

        validar(usuarioDTO);

        UsuarioEntity usuarioEntity = usuarioMapper.toUsuarioEntity(usuarioDTO);
        usuarioEntity = usuarioRepository.save(usuarioEntity);

        if (usuarioEntity.getTipoUsuario() == TipoUsuario.Turista) {
            TuristaEntity turistaEntity = new TuristaEntity();
            turistaEntity.setIdUsuario(usuarioEntity.getIdUsuario());
            turistaEntity.setIdiomaPreferido("es");
            turistaEntity.setNivel(1);
            turistaEntity.setPuntosExperiencia(0);
            turistaRepository.save(turistaEntity);
        } else if (usuarioEntity.getTipoUsuario() == TipoUsuario.Negocio) {
            NegocioEntity negocioEntity = new NegocioEntity();
            negocioEntity.setIdUsuario(usuarioEntity.getIdUsuario());
            negocioEntity.setEstadoVerificacion(EstadoVerificacion.PENDIENTE);
            negocioRepository.save(negocioEntity);
        }

        return usuarioMapper.toUsuarioDTO(usuarioEntity);
    }

    @Override
    public List<UsuarioDTO> findAll() {
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : usuarioEntities) {
            usuarioDTOs.add(usuarioMapper.toUsuarioDTO(usuarioEntity));
        }

        return usuarioDTOs;
    }

    // Revisa los datos antes de guardar; si algo esta mal responde 400 o 409 con el motivo
    private void validar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        String email = usuarioDTO.getEmail();
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Escribe un email valido");
        }
        if (usuarioDTO.getTipoUsuario() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El tipoUsuario debe ser Turista o Negocio");
        }
        List<String> faltantes = ValidadorPassword.reglasIncumplidas(usuarioDTO.getPassword());
        if (!faltantes.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "La contrasena debe " + String.join(", ", faltantes));
        }
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una cuenta con ese email");
        }
    }
}
