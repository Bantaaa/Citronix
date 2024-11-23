package org.banta.citronix.mapper;

import org.banta.citronix.domain.Sale;
import org.banta.citronix.dto.sale.SaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleDTO toDto(Sale sale);

    Sale toEntity(SaleDTO dto);
}