package com.task.system.api.services.impl;

import com.task.system.api.controllers.PermissionController;
import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.LoginDTO;
import com.task.system.api.dtos.UserDTO;
import com.task.system.api.dtos.ValidateFieldDTO;
import com.task.system.api.entities.Role;
import com.task.system.api.entities.Specialty;
import com.task.system.api.entities.User;
import com.task.system.api.mappers.UserMapper;
import com.task.system.api.repositories.RoleRepository;
import com.task.system.api.repositories.UserRepository;
import com.task.system.api.services.RoleService;
import com.task.system.api.services.SpecialtyService;
import com.task.system.api.services.UserService;
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
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    @Autowired
    private SpecialtyService specialtyService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public ApiResponseDTO getAllUsers() {
        List<User> listUsers = userRepository.findAll();
        if (listUsers.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No tiene usuarios registrados",null);

        List<UserDTO> userListDto = listUsers.stream().map(this::convertUserToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),"Lista de usuarios",userListDto);
    }

    @Override
    public ApiResponseDTO getUser(String idUser) {
        User  userBd = this.getUserById(idUser);
        if (userBd == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este usuario",null);

        return new ApiResponseDTO(HttpStatus.OK.value(),"Información del usuario solicitado",convertUserToDTO(userBd));
    }

    @Override
    public ApiResponseDTO saveUser(UserDTO userDTO,BindingResult bindingResult) {
        List<ValidateFieldDTO> validateInputs = this.validateInputs(bindingResult);
        if (!validateInputs.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"Campos inválidos, verifica por favor",validateInputs);

        List<Role> assigmentRoles = roleService.getRolesById(userDTO.getRoles());
        if (assigmentRoles.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No se encontraron los roles que asignaste al usuario",null);

        Specialty specialty = specialtyService.getSpecialtyById(userDTO.getSpecialty().getId());
        if (specialty==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe la especialidad a la cual le quieres asignar",null);

        User user = convertUserToEntity(userDTO);
        user.setRoles(assigmentRoles);
        user.setSpecialty(specialty);
        User userSave = userRepository.save(user);

        return new ApiResponseDTO(HttpStatus.CREATED.value(),"Usuario registrado de manera exitosa",convertUserToDTO(userSave));
    }

    @Override
    public ApiResponseDTO updateUser(String idUser, UserDTO userDTO,BindingResult bindingResult) {
        List<ValidateFieldDTO> validateInputs = this.validateInputs(bindingResult);
        if (!validateInputs.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"Campos inválidos, verifica por favor",validateInputs);

        User  userBd = this.getUserById(idUser);
        if (userBd == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este usuario",null);

        List<Role> assigmentRoles = roleService.getRolesById(userDTO.getRoles());
        if (assigmentRoles.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No se encontraron los roles que asignaste al usuario",null);

        Specialty specialty = specialtyService.getSpecialtyById(userDTO.getSpecialty().getId());
        if (specialty==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe la especialidad a la cual le quieres asignar",null);

        userBd.setName(userDTO.getName());
        userBd.setLastName(userDTO.getLastName());
        userBd.setPhone(userDTO.getPhone());
        userBd.setAddress(userDTO.getAddress());
        userBd.setEmail(userDTO.getEmail());
        userBd.setPassword(userDTO.getPassword());
        userBd.setStatus(StatusRegister.valueOf(userDTO.getStatus()));
        userBd.setRoles(assigmentRoles);
        userBd.setSpecialty(specialty);

        User userUpdate = userRepository.save(userBd);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Usuario actualizado correctamente", convertUserToDTO(userUpdate));
    }

    @Override
    public ApiResponseDTO deleteUser(String idUser) {
        User  userBd = this.getUserById(idUser);
        if (userBd == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe este usuario",null);

        userRepository.deleteById(idUser);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Se elimino el usuario del sistema correctamente",null);
    }

    @Override
    public ApiResponseDTO login(LoginDTO loginDto) {
        return null;
    }


    private List<ValidateFieldDTO> validateInputs(BindingResult bindingResult){
        List<ValidateFieldDTO> validateFieldDTOList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            LOGGER.info("UN CAMPO NO CUMPLE LA VALIDACIÓN");
            bindingResult.getFieldErrors().forEach(fieldError -> {
                ValidateFieldDTO validateFieldDTO = new ValidateFieldDTO();
                validateFieldDTO.setFieldValidated(fieldError.getField());
                validateFieldDTO.setFieldValidatedMessage(fieldError.getDefaultMessage());
                validateFieldDTOList.add(validateFieldDTO);
            });
        }
        return validateFieldDTOList;
    }


    public UserDTO convertUserToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertUserToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


    private User getUserById(String id){
        Optional<User> userBd = userRepository.findById(id);
        return userBd.orElse(null);
    }
}
