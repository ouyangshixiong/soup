package com.youai.repository;

import com.youai.domain.YaAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaAddress entity.
 */
public interface YaAddressRepository extends JpaRepository<YaAddress,Long> {

}
