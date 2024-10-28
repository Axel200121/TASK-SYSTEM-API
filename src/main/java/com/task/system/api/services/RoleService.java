package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.entities.Role;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface RoleService {

    ApiResponseDTO getAllRoles();
    ApiResponseDTO getRole(String idRole);
    ApiResponseDTO saveRole(RoleDTO roleDTO, BindingResult bindingResult);
    ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO,BindingResult bindingResult);
    ApiResponseDTO deleteRole(String idRole);
    List<Role> getRolesById(List<RoleDTO> rolesDto);

}
