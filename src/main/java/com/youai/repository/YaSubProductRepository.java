package com.youai.repository;

import com.youai.domain.YaSubProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaSubProduct entity.
 */
public interface YaSubProductRepository extends JpaRepository<YaSubProduct,Long> {

}
