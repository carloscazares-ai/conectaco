package com.conectaco.conectaco.service.impl;

import com.conectaco.conectaco.dto.UsuarioDTO;
import com.conectaco.conectaco.entity.UsuarioEntity;
import com.conectaco.conectaco.mapper.UsuarioMapper;
import com.conectaco.conectaco.repository.UsuarioRepository;
import com.conectaco.conectaco.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {

        UsuarioEntity usuarioEntity = usuarioMapper.toUsuarioEntity(usuarioDTO);
        usuarioEntity = usuarioRepository.save(usuarioEntity);
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
}
