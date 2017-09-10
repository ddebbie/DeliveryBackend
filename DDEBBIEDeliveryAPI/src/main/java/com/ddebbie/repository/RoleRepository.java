package com.ddebbie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ddebbie.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
