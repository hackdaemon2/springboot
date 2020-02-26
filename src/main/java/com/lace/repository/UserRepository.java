package com.lace.repository;

import com.lace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hackdaemon
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
