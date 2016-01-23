package com.youai.repository;

import com.youai.domain.YaDistrict;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaDistrict entity.
 */
public interface YaDistrictRepository extends JpaRepository<YaDistrict,Long> {

}
