package com.task.system.api.mappers;

import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.entities.Role;
import com.task.system.api.utils.StatusRegister;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RoleMapper {

    //Covierte un Rol a RoleDto
    public RoleDTO toDto(Role role) {
        if (role == null)
            return null;

        RoleDTO roleDto = new RoleDTO();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setDescription(role.getDescription());
        roleDto.setStatus(String.valueOf(role.getStatus()));
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
        role.setDescription(roleDto.getDescription());
        role.setStatus(role.getStatus());
        role.setCreatedAt(roleDto.getCreatedAt());
        role.setUpdatedAt(roleDto.getUpdatedAt());
        return role;
    }


    public List<RoleDTO> listToDto(List<Role> roleList){
        if (roleList.isEmpty())
            return null;
        List<RoleDTO> roleDtoList = new ArrayList<>();
        for (Role role : roleList){
            RoleDTO roleDto = new RoleDTO();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDto.setDescription(role.getDescription());
            roleDto.setStatus(String.valueOf(role.getStatus()));
            roleDto.setCreatedAt(role.getCreatedAt());
            roleDto.setUpdatedAt(role.getUpdatedAt());
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }

    public List<Role> listToEntity(List<RoleDTO> roleDtoList) {
        if (roleDtoList == null || roleDtoList.isEmpty()) {
            return Collections.emptyList(); // Retorna una lista vacía si no hay DTOs
        }

        List<Role> roleList = new ArrayList<>();
        for (RoleDTO roleDto : roleDtoList) {
            Role role = toEntity(roleDto); // Llama al método toEntity
            if (role != null) {
                roleList.add(role);
            }
        }
        return roleList;
    }


}
