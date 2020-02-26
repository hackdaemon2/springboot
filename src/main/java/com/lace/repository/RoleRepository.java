package com.lace.repository;

import com.lace.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hackdaemon
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Page<Role> findByUserOrderByCreatedAtDesc(Role role, Pageable pageable);
  Page<Role> findAllOrderByCreatedAtDesc(Pageable pageable);
  Role findByRole(@Param("role") String role);
}
