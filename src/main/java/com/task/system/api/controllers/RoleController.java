package com.task.system.api.controllers;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.RoleDTO;
import com.task.system.api.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> roleSave(@Valid @RequestBody RoleDTO roleDTO, BindingResult bindingResult){
        ApiResponseDTO response = roleService.saveRole(roleDTO,bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDTO> getAllRoles(){
        ApiResponseDTO response = roleService.getAllRoles();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO> getRole(@PathVariable String id){
        ApiResponseDTO response = roleService.getRole(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> roleUpdate(@PathVariable String id, @Valid @RequestBody RoleDTO roleDTO,BindingResult bindingResult){
        ApiResponseDTO response = roleService.updateRole(id, roleDTO,bindingResult);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> deleteRole(@PathVariable String id){
        ApiResponseDTO response = roleService.deleteRole(id);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

}
