package com.github.leomauricio7.auth.repository;

import com.github.leomauricio7.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT p FROM Permission p WHERE p.description =:description")
    Permission findByDescription(@Param("description") String description);

}
