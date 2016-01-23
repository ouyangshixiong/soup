package com.youai.repository;

import com.youai.domain.YaCouponOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaCouponOrder entity.
 */
public interface YaCouponOrderRepository extends JpaRepository<YaCouponOrder,Long> {

}
