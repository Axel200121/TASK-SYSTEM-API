package com.task.system.api.controllers;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.PermissionDTO;
import com.task.system.api.services.PermissionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> savePermission(@Valid @RequestBody PermissionDTO permissionDTO, BindingResult bindingResult){
        ApiResponseDTO response = permissionService.savePermission(permissionDTO,bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> updatePermission(@PathVariable String id, @Valid @RequestBody PermissionDTO permissionDTO,BindingResult bindingResult){
        ApiResponseDTO response = permissionService.updatePermission(id,permissionDTO,bindingResult);
        return  new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDTO> getAllPermissions(){
        ApiResponseDTO response = permissionService.getAllPermissions();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO> getPermission(@PathVariable String id){
        ApiResponseDTO response = permissionService.getPermission(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> deletePermission(@PathVariable String id){
        ApiResponseDTO response = permissionService.deletePermission(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
