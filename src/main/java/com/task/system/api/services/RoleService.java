package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;

public interface RoleService {

    ApiResponseDTO getAllRoles();
    ApiResponseDTO getRole(String idRole);
    ApiResponseDTO saveRole(RoleDTO roleDTO);
    ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO);
    ApiResponseDTO deleteRole(String idRole);

}
