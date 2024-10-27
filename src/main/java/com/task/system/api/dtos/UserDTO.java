package com.task.system.api.dtos;

import com.task.system.api.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

public class UserDTO {
    private String id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede ser vacio")
    private String name;

    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede ser vacio")
    private String lastName;

    @NotNull(message = "El telefono no puede ser nulo")
    @NotBlank(message = "El telefono no puede ser vacio")
    private String phone;

    @NotNull(message = "El dirección no puede ser nulo")
    @NotBlank(message = "El dirección no puede ser vacio")
    private String address;

    @NotNull(message = "El correo electronico no puede ser nulo")
    @NotBlank(message = "El correo electronico no puede ser vacio")
    @Email(message = "No cumple el formato de un correo electronico")
    private String email;

    @NotNull(message = "La contraseña no puede ser nulo")
    @NotBlank(message = "La contraseña no puede ser vacio")
    @Size(min = 8, max = 8,message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "La confirmación de contraseña no puede ser nulo")
    @NotBlank(message = "La confirmación de contraseña no puede ser vacio")
    @Size(min = 8, max = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String confirmPassword;

    @NotNull(message = "El status no puede ser nulo")
    @NotBlank(message = "El status no puede ser vacio")
    private String status;

    @NotNull(message = "La asignación de rol no puede ser nulo")
    @Size(min = 1, message = "Debes asignar por lo menos un rol al usuario")
    private List<RoleDTO> roles;

    private Date createdAt;
    private Date updatedAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
