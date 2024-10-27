package com.task.system.api.services.impl;

import com.task.system.api.controllers.PermissionController;
import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.PermissionDTO;
import com.task.system.api.dtos.UserDTO;
import com.task.system.api.dtos.ValidateFieldDTO;
import com.task.system.api.entities.Permission;
import com.task.system.api.entities.User;
import com.task.system.api.repositories.PermissionRepository;
import com.task.system.api.services.PermissionService;
import com.task.system.api.utils.StatusRegister;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponseDTO getAllPermissions() {
        List<Permission> permissionList = permissionRepository.findAll();
        if (permissionList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No tienes permisos registrados",null);

        List<PermissionDTO> permissionListDto = permissionList.stream().map(this::convertPermissionToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),"Lista de permisos para el sistema",permissionListDto);
    }

    @Override
    public ApiResponseDTO getPermission(String idPermission) {
        Permission permissionBd = this.getPermissionById(idPermission);
        if (permissionBd == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este permiso",null);

        return new ApiResponseDTO(HttpStatus.OK.value(),"Información del permiso",convertPermissionToDTO(permissionBd));
    }

    @Override
    public ApiResponseDTO savePermission(PermissionDTO permissionDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> validateFieldDTOList = validateInputs(bindingResult);
        if (!validateFieldDTOList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), "Campos inválidos, verifica por favor", validateFieldDTOList);

        Permission permissionSave = permissionRepository.save(convertPermissionToEntity(permissionDTO));
        return new ApiResponseDTO(HttpStatus.CREATED.value(),"Permiso creado correctamente",convertPermissionToDTO(permissionSave));
    }

    @Override
    public ApiResponseDTO updatePermission(String idPermission, PermissionDTO permissionDTO, BindingResult bindingResult) {

        List<ValidateFieldDTO> validateFieldDTOList = validateInputs(bindingResult);
        if (!validateFieldDTOList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), "Campos inválidos, verifica por favor", validateFieldDTOList);

        Permission permission = this.getPermissionById(idPermission);
        if (permission == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe el permiso que quieres actualizar",null);

        permission.setName(permissionDTO.getName());
        permission.setDescription(permissionDTO.getDescription());
        permission.setStatus(StatusRegister.valueOf(permissionDTO.getStatus()));

        Permission permissionEdit = permissionRepository.save(permission);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Permiso se actualizo correctamente",convertPermissionToDTO(permissionEdit));
    }

    @Override
    public ApiResponseDTO deletePermission(String idPermission) {
        Permission permission = this.getPermissionById(idPermission);
        if (permission == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"El permiso no existe",null);

        permissionRepository.deleteById(idPermission);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Permiso eliminado correctamente",null);
    }

    @Override
    public List<Permission> getPermissionsById(List<PermissionDTO> permissionDTO) {
        if (permissionDTO.isEmpty()) return null;
        List<Permission> assigmentPermissions = new ArrayList<>();
        for (PermissionDTO permission: permissionDTO){
            Permission getPermission = getPermissionById(permission.getId());
            assigmentPermissions.add(getPermission);
        }
        return assigmentPermissions;
    }

    private List<ValidateFieldDTO> validateInputs(BindingResult bindingResult){
        List<ValidateFieldDTO> validateFieldDTOList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            LOGGER.info("UN CAMPO NO CUMPLE LA VALIDACIÓN");
            bindingResult.getFieldErrors().forEach(fieldError -> {
                ValidateFieldDTO validateFieldDTO = new ValidateFieldDTO();
                validateFieldDTO.setFieldValidated(fieldError.getField());
                validateFieldDTO.setFieldValidatedMessage(fieldError.getDefaultMessage());
                validateFieldDTOList.add(validateFieldDTO); // Agregar a la lista
            });
        }
        return validateFieldDTOList;
    }

    private Permission getPermissionById(String id){
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.orElse(null);
    }

    public PermissionDTO convertPermissionToDTO(Permission permission) {
        return modelMapper.map(permission, PermissionDTO.class);
    }

    public Permission convertPermissionToEntity(PermissionDTO permissionDTO) {
        return modelMapper.map(permissionDTO, Permission.class);
    }
}
