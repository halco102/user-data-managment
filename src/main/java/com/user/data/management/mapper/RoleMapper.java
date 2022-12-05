package com.user.data.management.mapper;

import com.user.data.management.dto.request.role.AddRoleRequest;
import com.user.data.management.dto.response.role.RoleDto;
import com.user.data.management.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role requestToEntity(AddRoleRequest request);

    RoleDto entityToDto(Role role);

}
