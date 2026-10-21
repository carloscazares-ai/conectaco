package com.conectaco.conectaco.service.impl;

import com.conectaco.conectaco.dto.TuristaDTO;
import com.conectaco.conectaco.entity.TuristaEntity;
import com.conectaco.conectaco.mapper.TuristaMapper;
import com.conectaco.conectaco.repository.TuristaRepository;
import com.conectaco.conectaco.service.TuristaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TuristaServiceImpl implements TuristaService {

    private final TuristaMapper turistaMapper;
    private final TuristaRepository turistaRepository;

    public TuristaServiceImpl(TuristaMapper turistaMapper, TuristaRepository turistaRepository) {
        this.turistaMapper = turistaMapper;
        this.turistaRepository = turistaRepository;
    }

    @Override
    public List<TuristaDTO> findAll() {
        List<TuristaDTO> turistaDTOs = new ArrayList<>();

        for (TuristaEntity turistaEntity : turistaRepository.findAll()) {
            turistaDTOs.add(turistaMapper.toTuristaDTO(turistaEntity));
        }

        return turistaDTOs;
    }
}
