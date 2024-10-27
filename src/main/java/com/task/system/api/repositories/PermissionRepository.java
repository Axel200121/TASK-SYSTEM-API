package com.task.system.api.repositories;

import com.task.system.api.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
}
