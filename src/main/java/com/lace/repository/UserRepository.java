package com.lace.repository;

import com.lace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hackdaemon
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.deleted = false ORDER BY u.id ASC")
    Page<User> findAllActiveUsers(Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.mobileNumber = :mobileNumber")
    User findByMobileNumber(@Param("mobileNumber") String mobileNumber);
    
    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    int deleteUserById(@Param("id") Long id);
}
