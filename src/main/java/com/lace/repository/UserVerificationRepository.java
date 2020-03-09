package com.lace.repository;

import com.lace.model.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author centricgateway
 */
@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerificationToken, Long> {
    UserVerificationToken findByToken(String token);
}
