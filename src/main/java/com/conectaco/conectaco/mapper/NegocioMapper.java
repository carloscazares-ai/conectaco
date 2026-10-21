package com.conectaco.conectaco.mapper;

import com.conectaco.conectaco.dto.NegocioDTO;
import com.conectaco.conectaco.entity.NegocioEntity;

public interface NegocioMapper {
    NegocioDTO toNegocioDTO(NegocioEntity negocioEntity);
}
