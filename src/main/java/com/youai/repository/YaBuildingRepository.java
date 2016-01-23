package com.youai.repository;

import com.youai.domain.YaBuilding;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaBuilding entity.
 */
public interface YaBuildingRepository extends JpaRepository<YaBuilding,Long> {

}
