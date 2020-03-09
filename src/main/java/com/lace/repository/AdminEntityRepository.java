package com.lace.repository;

import com.lace.model.AdminEntity;
import com.lace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author centricgateway
 */
public interface AdminEntityRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByUser(User user);
}
