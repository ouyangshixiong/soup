package com.youai.repository;

import com.youai.domain.YaCoupon;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaCoupon entity.
 */
public interface YaCouponRepository extends JpaRepository<YaCoupon,Long> {

}
