package com.youai.repository;

import com.youai.domain.YaPot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaPot entity.
 */
public interface YaPotRepository extends JpaRepository<YaPot,Long> {

}
