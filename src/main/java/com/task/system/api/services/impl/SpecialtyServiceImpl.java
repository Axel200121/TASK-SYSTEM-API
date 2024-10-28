package com.task.system.api.services.impl;

import com.task.system.api.controllers.PermissionController;
import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.dtos.SpecialtyDTO;
import com.task.system.api.dtos.ValidateFieldDTO;
import com.task.system.api.entities.Role;
import com.task.system.api.entities.Specialty;
import com.task.system.api.repositories.SpecialtyRepository;
import com.task.system.api.services.SpecialtyService;
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
public class SpecialtyServiceImpl implements SpecialtyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialtyServiceImpl.class);

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponseDTO getAllSpecialties() {
        List<Specialty> specialtyList = specialtyRepository.findAll();
        if (specialtyList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No tienes especialidades registrados",null);

        List<SpecialtyDTO> specialtyDTOS = specialtyList.stream().map(this::convertSpecialtyToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),"Listado de especialidades",specialtyDTOS);
    }

    @Override
    public ApiResponseDTO getSpecialty(String idSpecialty) {
        Specialty specialty = this.getSpecialtyId(idSpecialty);
        if (specialty==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe esta especialidad",null);

        return new ApiResponseDTO(HttpStatus.OK.value(),"Información de la especialidad",convertSpecialtyToDTO(specialty)) ;
    }

    @Override
    public ApiResponseDTO saveSpecialty(SpecialtyDTO specialtyDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> validateFieldDTOList = this.validateInputs(bindingResult);
        if (!validateFieldDTOList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"Campos invalidos, verifica por favor",validateFieldDTOList);

        Specialty specialty = specialtyRepository.save(convertSpecialtyToEntity(specialtyDTO));
        return new ApiResponseDTO(HttpStatus.CREATED.value(),"La especialidad se a creado correctamente",convertSpecialtyToDTO(specialty));
    }

    @Override
    public ApiResponseDTO updateSpecialty(String idSpecialty, SpecialtyDTO specialtyDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> validateFieldDTOList = this.validateInputs(bindingResult);
        if (!validateFieldDTOList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"Campos invalidos, verifica por favor",validateFieldDTOList);

        Specialty specialty = this.getSpecialtyId(idSpecialty);
        if (specialty==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe esta especialidad",null);

        specialty.setName(specialtyDTO.getName());
        specialty.setDescription(specialtyDTO.getDescription());
        Specialty specialtyEdit = specialtyRepository.save(specialty);
        return  new ApiResponseDTO(HttpStatus.OK.value(),"La especialidad se actualizo correctamente",convertSpecialtyToDTO(specialtyEdit));
    }

    @Override
    public ApiResponseDTO deleteSpecialty(String idSpecialty) {
        Specialty specialty = this.getSpecialtyId(idSpecialty);
        if (specialty==null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),"No existe esta especialidad",null);

        specialtyRepository.deleteById(idSpecialty);
        return new ApiResponseDTO(HttpStatus.OK.value(),"Especialidad eliminado correctamente",null);
    }

    @Override
    public Specialty getSpecialtyById(String id) {
        return this.getSpecialtyId(id);
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

    private Specialty getSpecialtyId(String id){
        Optional<Specialty> specialtyBd = specialtyRepository.findById(id);
        return specialtyBd.orElse(null);
    }

    public SpecialtyDTO convertSpecialtyToDTO(Specialty specialty) {
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }

    public Specialty convertSpecialtyToEntity(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, Specialty.class);
    }
}
