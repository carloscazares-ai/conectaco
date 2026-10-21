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

    // POST /v1/usuarios
    // 1. Guarda el usuario en la tabla Usuarios.
    // 2. Segun su tipo, crea su registro en Turistas o en Negocios con el id del usuario.
    // @Transactional: si falla el paso 2, tampoco se guarda el usuario (todo o nada).
    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {

        validarNombre(usuarioDTO.getNombre());
        validarEmail(usuarioDTO.getEmail(), null);
        validarPassword(usuarioDTO.getPassword());
        if (usuarioDTO.getTipoUsuario() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El tipoUsuario debe ser Turista o Negocio");
        }

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

    // GET /v1/usuarios (todos)
    @Override
    public List<UsuarioDTO> findAll() {
        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : usuarioEntities) {
            usuarioDTOs.add(usuarioMapper.toUsuarioDTO(usuarioEntity));
        }

        return usuarioDTOs;
    }

    // GET /v1/usuarios?nombre=...&email=...&tipoUsuario=...
    @Override
    public List<UsuarioDTO> buscar(String nombre, String email, TipoUsuario tipoUsuario) {
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        for (UsuarioEntity usuarioEntity : usuarioRepository.buscar(vacioANull(nombre), vacioANull(email), tipoUsuario)) {
            usuarioDTOs.add(usuarioMapper.toUsuarioDTO(usuarioEntity));
        }

        return usuarioDTOs;
    }

    // GET /v1/usuarios/{id}
    @Override
    public UsuarioDTO findById(Integer idUsuario) {
        return usuarioMapper.toUsuarioDTO(buscarUsuario(idUsuario));
    }

    // PUT /v1/usuarios/{id}: reemplaza nombre, email y password (los tres son obligatorios)
    @Override
    @Transactional
    public UsuarioDTO update(Integer idUsuario, UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = buscarUsuario(idUsuario);

        validarNombre(usuarioDTO.getNombre());
        validarEmail(usuarioDTO.getEmail(), idUsuario);
        validarPassword(usuarioDTO.getPassword());
        validarMismoTipo(usuarioEntity, usuarioDTO);

        usuarioEntity.setNombre(usuarioDTO.getNombre().trim());
        usuarioEntity.setEmail(usuarioDTO.getEmail().trim());
        usuarioEntity.setPassword(usuarioDTO.getPassword());

        return usuarioMapper.toUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    // PATCH /v1/usuarios/{id}: solo cambia los campos que vienen en el JSON
    @Override
    @Transactional
    public UsuarioDTO patch(Integer idUsuario, UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = buscarUsuario(idUsuario);
        validarMismoTipo(usuarioEntity, usuarioDTO);

        if (usuarioDTO.getNombre() != null) {
            validarNombre(usuarioDTO.getNombre());
            usuarioEntity.setNombre(usuarioDTO.getNombre().trim());
        }
        if (usuarioDTO.getEmail() != null) {
            validarEmail(usuarioDTO.getEmail(), idUsuario);
            usuarioEntity.setEmail(usuarioDTO.getEmail().trim());
        }
        if (usuarioDTO.getPassword() != null) {
            validarPassword(usuarioDTO.getPassword());
            usuarioEntity.setPassword(usuarioDTO.getPassword());
        }

        return usuarioMapper.toUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    // DELETE /v1/usuarios/{id}
    // Primero se borra su registro de Turistas o Negocios (apunta al usuario con fk_id_usuario)
    // y despues el usuario. Si se borrara primero el usuario, MySQL no lo dejaria por la llave foranea.
    @Override
    @Transactional
    public void delete(Integer idUsuario) {
        UsuarioEntity usuarioEntity = buscarUsuario(idUsuario);

        turistaRepository.deleteByIdUsuario(idUsuario);
        negocioRepository.deleteByIdUsuario(idUsuario);
        usuarioRepository.delete(usuarioEntity);
    }

    private UsuarioEntity buscarUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No existe el usuario " + idUsuario));
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
    }

    // idUsuario = null cuando es un usuario nuevo; si no, se ignora su propio email al revisar repetidos
    private void validarEmail(String email, Integer idUsuario) {
        if (email == null || !email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Escribe un email valido");
        }
        boolean repetido = idUsuario == null
                ? usuarioRepository.existsByEmailIgnoreCase(email.trim())
                : usuarioRepository.existsByEmailIgnoreCaseAndIdUsuarioNot(email.trim(), idUsuario);
        if (repetido) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una cuenta con ese email");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La contrasena es obligatoria");
        }
        List<String> faltantes = ValidadorPassword.reglasIncumplidas(password);
        if (!faltantes.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La contrasena debe " + String.join(", ", faltantes));
        }
    }

    // El tipo no se cambia: un Turista tiene su registro en Turistas y un Negocio en Negocios
    private void validarMismoTipo(UsuarioEntity usuarioEntity, UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getTipoUsuario() != null && usuarioDTO.getTipoUsuario() != usuarioEntity.getTipoUsuario()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se puede cambiar el tipoUsuario de una cuenta");
        }
    }

    private static String vacioANull(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
