package com.conectaco.conectaco.mapper.impl;

import com.conectaco.conectaco.dto.TuristaDTO;
import com.conectaco.conectaco.entity.TuristaEntity;
import com.conectaco.conectaco.mapper.TuristaMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TuristaMapperImpl implements TuristaMapper {

    @Override
    public TuristaDTO toTuristaDTO(TuristaEntity turistaEntity) {
        TuristaDTO turistaDTO = new TuristaDTO();

        BeanUtils.copyProperties(turistaEntity, turistaDTO);

        return turistaDTO;
    }
}
