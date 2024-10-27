package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.LoginDTO;
import com.task.system.api.dtos.UserDTO;
import org.springframework.validation.BindingResult;

public interface UserService {

    ApiResponseDTO getAllUsers();
    ApiResponseDTO getUser(String idUser);
    ApiResponseDTO saveUser(UserDTO userDto, BindingResult bindingResult);
    ApiResponseDTO updateUser(String idUser, UserDTO userDTO, BindingResult bindingResult);
    ApiResponseDTO deleteUser(String idUser);
    ApiResponseDTO login(LoginDTO loginDto);

}
