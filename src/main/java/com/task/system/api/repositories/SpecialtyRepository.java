package com.task.system.api.repositories;

import com.task.system.api.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty,String> {
}
