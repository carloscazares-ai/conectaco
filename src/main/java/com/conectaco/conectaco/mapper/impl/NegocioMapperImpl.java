package com.conectaco.conectaco.mapper.impl;

import com.conectaco.conectaco.dto.NegocioDTO;
import com.conectaco.conectaco.entity.NegocioEntity;
import com.conectaco.conectaco.mapper.NegocioMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class NegocioMapperImpl implements NegocioMapper {

    @Override
    public NegocioDTO toNegocioDTO(NegocioEntity negocioEntity) {
        NegocioDTO negocioDTO = new NegocioDTO();

        BeanUtils.copyProperties(negocioEntity, negocioDTO);

        return negocioDTO;
    }
}
