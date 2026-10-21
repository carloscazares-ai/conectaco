package com.conectaco.conectaco.mapper;

import com.conectaco.conectaco.dto.TuristaDTO;
import com.conectaco.conectaco.entity.TuristaEntity;

public interface TuristaMapper {
    TuristaDTO toTuristaDTO(TuristaEntity turistaEntity);
}
