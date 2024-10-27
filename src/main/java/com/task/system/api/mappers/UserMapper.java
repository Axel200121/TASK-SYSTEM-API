package com.task.system.api.mappers;

import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.dtos.UserDTO;
import com.task.system.api.entities.Role;
import com.task.system.api.entities.User;
import com.task.system.api.utils.StatusRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    //Covierte un Rol a RoleDto
    public UserDTO toDto(User user) {
        if (user == null)
            return null;

        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setStatus(String.valueOf(user.getStatus()));
        List<RoleDTO> roleDto = roleMapper.listToDto(user.getRoles());
        userDto.setRoles(roleDto);
        return userDto;
    }

    //Covierte un RolDto a Entity
    public User toEntity(UserDTO userDto) {
        if (userDto == null)
            return null;

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setStatus(StatusRegister.valueOf(userDto.getStatus()));
        List<Role> roles = roleMapper.listToEntity(userDto.getRoles());
        user.setRoles(roles);
        return user;
    }


}
