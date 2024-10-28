package com.task.system.api.services.impl;

import com.task.system.api.controllers.PermissionController;
import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.dtos.ValidateFieldDTO;
import com.task.system.api.entities.Permission;
import com.task.system.api.entities.Role;
import com.task.system.api.mappers.RoleMapper;
import com.task.system.api.repositories.RoleRepository;
import com.task.system.api.services.PermissionService;
import com.task.system.api.services.RoleService;
import com.task.system.api.utils.StatusRegister;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResponseDTO getAllRoles() {
        List<Role> rolesBD = roleRepository.findAll();

        if(rolesBD.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No tienes roles almacenados",null);

        List<RoleDTO> roleDTOList = rolesBD.stream().map(this::convertRoleToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),"Lista de Roles",roleDTOList);
    }

    @Override
    public ApiResponseDTO getRole(String idRole) {
        Role getRole = getRoleById(idRole);

        if (getRole==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este rol en la base de datos",null);

        return new ApiResponseDTO(HttpStatus.OK.value(),"Rol encontrado", convertRoleToDTO(getRole));
    }


    @Override
    public ApiResponseDTO saveRole(RoleDTO roleDTO, BindingResult bindingResult) {

        List<ValidateFieldDTO> validateFieldDTOList = validateInputs(bindingResult);
        if (!validateFieldDTOList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), "Campos inválidos, verifica por favor", validateFieldDTOList);

        List<Permission> assigmentPermissions = permissionService.getPermissionsById(roleDTO.getPermissions());
        if (assigmentPermissions == null || assigmentPermissions.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existen los permisos que quieres asignarle al Rol",null);

        Role role = convertRoleToEntity(roleDTO);
        role.setPermissions(assigmentPermissions);
        Role roleSave =roleRepository.save(role);
        return new ApiResponseDTO(HttpStatus.CREATED.value(),"Rol creado exitosamente",convertRoleToDTO(roleSave));
    }

    @Override
    public ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO,BindingResult bindingResult) {

        List<ValidateFieldDTO> validateFieldDTOList = validateInputs(bindingResult);
        if (!validateFieldDTOList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), "Campos inválidos, verifica por favor", validateFieldDTOList);

        Role getRole = getRoleById(idRole);
        if (getRole==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este rol en la base de datos",null);

        List<Permission> permissionList= permissionService.getPermissionsById(roleDTO.getPermissions());
        if (permissionList==null || permissionList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"Los permisos a asignar no existen o estan desactivados, verifica por favor",null);

        getRole.setName(roleDTO.getName());
        getRole.setDescription(roleDTO.getDescription());
        getRole.setStatus(StatusRegister.valueOf(roleDTO.getStatus()));
        getRole.setPermissions(permissionList);
        Role roleSave = roleRepository.save(getRole);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Rol actualizado correctamente", convertRoleToDTO(roleSave));
    }

    @Override
    public ApiResponseDTO deleteRole(String idRole) {
        Role getRole = getRoleById(idRole);

        if (getRole==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este rol en la base de datos",null);

        roleRepository.deleteById(idRole);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Rol eliminado correctamente",null);
    }

    @Override
    public List<Role> getRolesById(List<RoleDTO> rolesDto) {
        if (rolesDto.isEmpty()) return null;
        List<Role> assigmentRoleUser = new ArrayList<>();
        for (RoleDTO searchRole : rolesDto) {
            Role getRole = getRoleById(searchRole.getId());
            assigmentRoleUser.add(getRole);
        }
        return assigmentRoleUser;
    }

    private Role getRoleById(String idRole){
        Optional<Role> roleBd = roleRepository.findById(idRole);
        return roleBd.orElse(null);
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

    public RoleDTO convertRoleToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role convertRoleToEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }
}
