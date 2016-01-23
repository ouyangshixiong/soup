package com.youai.repository;

import com.youai.domain.YaBanner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaBanner entity.
 */
public interface YaBannerRepository extends JpaRepository<YaBanner,Long> {

}
