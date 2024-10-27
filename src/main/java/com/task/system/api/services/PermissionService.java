package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.PermissionDTO;
import com.task.system.api.entities.Permission;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PermissionService {

    ApiResponseDTO getAllPermissions();
    ApiResponseDTO getPermission(String idPermission);
    ApiResponseDTO savePermission(PermissionDTO permissionDTO, BindingResult bindingResult);
    ApiResponseDTO updatePermission(String idPermission, PermissionDTO permissionDTO,BindingResult bindingResult);
    ApiResponseDTO deletePermission(String idPermission);
    List<Permission> getPermissionsById(List<PermissionDTO> permissionDTO);
}
