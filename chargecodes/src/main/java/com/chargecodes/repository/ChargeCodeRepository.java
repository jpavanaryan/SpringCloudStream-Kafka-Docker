package com.chargecodes.repository;

import com.chargecodes.domain.ChargeCode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ChargeCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargeCodeRepository extends JpaRepository<ChargeCode, Long> {

}
