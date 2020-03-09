package com.lace.repository;

import com.lace.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author centricgateway
 */
public interface CountryRepository  extends JpaRepository<Country, Long> {}
