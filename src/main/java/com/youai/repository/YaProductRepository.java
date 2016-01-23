package com.youai.repository;

import com.youai.domain.YaProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaProduct entity.
 */
public interface YaProductRepository extends JpaRepository<YaProduct,Long> {

}
