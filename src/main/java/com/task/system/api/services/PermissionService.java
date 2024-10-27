package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.PermissionDTO;

public interface PermissionService {

    ApiResponseDTO getAllPermissions();
    ApiResponseDTO getPermission(String idPermission);
    ApiResponseDTO savePermission(PermissionDTO permissionDTO);
    ApiResponseDTO updatePermission(String idPermission, PermissionDTO permissionDTO);
    ApiResponseDTO deletePermission(String idPermission);
}
