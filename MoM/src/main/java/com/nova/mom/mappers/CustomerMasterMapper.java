package com.nova.mom.mappers;

import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.entities.CustomerMaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMasterMapper {
    CustomerMasterMapper INSTANCE = Mappers.getMapper(CustomerMasterMapper.class);

    CustomerMaster toEntity(CustomerDTO customerDTO);
    CustomerDTO toDto(CustomerMaster customerMaster);
}
