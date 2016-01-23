package com.youai.repository;

import com.youai.domain.YaOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaOrder entity.
 */
public interface YaOrderRepository extends JpaRepository<YaOrder,Long> {

}
