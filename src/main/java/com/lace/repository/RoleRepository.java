package com.lace.repository;

import com.lace.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hackdaemon
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {}
