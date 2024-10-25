package com.task.system.api.services.impl;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.entities.Role;
import com.task.system.api.mappers.RoleMapper;
import com.task.system.api.repositories.RoleRepository;
import com.task.system.api.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ApiResponseDTO getAllRoles() {
        List<Role> rolesBD = roleRepository.findAll();

        if(rolesBD.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No tienes roles almacenados",null);

        List<RoleDTO> roleDTOList = rolesBD.stream().map(roleMapper::toDto).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),"Lista de Roles",roleDTOList);
    }

    @Override
    public ApiResponseDTO getRole(String idRole) {
        Role getRole = getRoleById(idRole);

        if (getRole==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este rol en la base de datos",null);

        return new ApiResponseDTO(HttpStatus.OK.value(),"Rol encontrado", roleMapper.toDto(getRole));
    }


    @Override
    public ApiResponseDTO saveRole(RoleDTO roleDTO) {
        Role roleSave =roleRepository.save(roleMapper.toEntity(roleDTO));
        return new ApiResponseDTO(HttpStatus.CREATED.value(),"Rol creado exitosamente",roleMapper.toDto(roleSave));
    }

    @Override
    public ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO) {
        Role getRole = getRoleById(idRole);
        if (getRole==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este rol en la base de datos",null);

        getRole.setName(roleDTO.getName());
        Role roleSave = roleRepository.save(roleMapper.toEntity(roleDTO));
        return new ApiResponseDTO(HttpStatus.OK.value(),"Rol actualizado correctamente", roleMapper.toDto(roleSave));
    }

    @Override
    public ApiResponseDTO deleteRole(String idRole) {
        Role getRole = getRoleById(idRole);

        if (getRole==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este rol en la base de datos",null);

        roleRepository.deleteById(idRole);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Rol eliminado correctamente",null);
    }

    private Role getRoleById(String id){
        Optional<Role> roleBd = roleRepository.findById(id);
        return roleBd.orElse(null);
    }
}
