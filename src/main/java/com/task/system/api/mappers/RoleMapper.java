package com.task.system.api.mappers;

import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    //Covierte un Rol a RoleDto
    public RoleDTO toDto(Role role) {
        if (role == null)
            return null;

        RoleDTO roleDto = new RoleDTO();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setCreatedAt(role.getCreatedAt());
        roleDto.setUpdatedAt(role.getUpdatedAt());
        return roleDto;
    }

    //Covierte un RolDto a Entity
    public Role toEntity(RoleDTO roleDto) {
        if (roleDto == null)
            return null;

        Role role = new Role();
        role.setName(roleDto.getName());
        role.setCreatedAt(roleDto.getCreatedAt());
        role.setUpdatedAt(roleDto.getUpdatedAt());
        return role;
    }

}
