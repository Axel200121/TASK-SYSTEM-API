package com.task.system.api.services;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.SpecialtyDTO;
import com.task.system.api.entities.Specialty;
import org.springframework.validation.BindingResult;

public interface SpecialtyService {

    ApiResponseDTO getAllSpecialties();
    ApiResponseDTO getSpecialty(String idSpecialty);
    ApiResponseDTO saveSpecialty(SpecialtyDTO specialtyDTO, BindingResult bindingResult);
    ApiResponseDTO updateSpecialty(String idSpecialty, SpecialtyDTO specialtyDTO,BindingResult bindingResult);
    ApiResponseDTO deleteSpecialty(String idSpecialty);
    Specialty getSpecialtyById(String id);
}
