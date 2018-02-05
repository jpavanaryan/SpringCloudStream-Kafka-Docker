package com.chargecodes.repository;

import com.chargecodes.domain.ChargeCodeProject;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ChargeCodeProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargeCodeProjectRepository extends JpaRepository<ChargeCodeProject, Long> {

}
