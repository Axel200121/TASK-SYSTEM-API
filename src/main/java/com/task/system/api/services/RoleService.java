package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.entities.Role;

import java.util.List;

public interface RoleService {

    ApiResponseDTO getAllRoles();
    ApiResponseDTO getRole(String idRole);
    ApiResponseDTO saveRole(RoleDTO roleDTO);
    ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO);
    ApiResponseDTO deleteRole(String idRole);
    List<Role> getRolesById(List<RoleDTO> rolesDto);

}
