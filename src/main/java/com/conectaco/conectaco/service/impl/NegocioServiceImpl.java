package com.conectaco.conectaco.service.impl;

import com.conectaco.conectaco.dto.NegocioDTO;
import com.conectaco.conectaco.entity.NegocioEntity;
import com.conectaco.conectaco.mapper.NegocioMapper;
import com.conectaco.conectaco.repository.NegocioRepository;
import com.conectaco.conectaco.service.NegocioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NegocioServiceImpl implements NegocioService {

    private final NegocioMapper negocioMapper;
    private final NegocioRepository negocioRepository;

    public NegocioServiceImpl(NegocioMapper negocioMapper, NegocioRepository negocioRepository) {
        this.negocioMapper = negocioMapper;
        this.negocioRepository = negocioRepository;
    }

    @Override
    public List<NegocioDTO> findAll() {
        List<NegocioDTO> negocioDTOs = new ArrayList<>();

        for (NegocioEntity negocioEntity : negocioRepository.findAll()) {
            negocioDTOs.add(negocioMapper.toNegocioDTO(negocioEntity));
        }

        return negocioDTOs;
    }
}
