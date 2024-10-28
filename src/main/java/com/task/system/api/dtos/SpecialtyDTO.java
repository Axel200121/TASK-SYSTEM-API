package com.task.system.api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class SpecialtyDTO {

    private String id;

    @NotBlank(message = "El nombre de la especialidad no puede estar vacio")
    @NotNull(message = "El nombre de la especialidad no puede estar nulo")
    private String name;

    @NotBlank(message = "La descripción no puede estar vacio")
    @NotNull(message = "La descripción no puede estar nulo")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
