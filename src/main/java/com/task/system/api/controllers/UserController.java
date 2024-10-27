package com.task.system.api.controllers;

import com.task.system.api.dtos.ApiResponseDTO;
import com.task.system.api.dtos.UserDTO;
import com.task.system.api.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> saveUser(@Valid @RequestBody UserDTO userDto,BindingResult bindingResult){
        ApiResponseDTO response = userService.saveUser(userDto,bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> updateUser(@PathVariable String id, @Valid  @RequestBody UserDTO userDTO, BindingResult bindingResult){
        ApiResponseDTO response = userService.updateUser(id, userDTO,bindingResult);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDTO> getAllUsers(){
        ApiResponseDTO response = userService.getAllUsers();
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO> getUser(@PathVariable String id){
        ApiResponseDTO response = userService.getUser(id);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponseDTO> deleteUser(@PathVariable String id){
        ApiResponseDTO response = userService.deleteUser(id);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
