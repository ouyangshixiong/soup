package com.youai.repository;

import com.youai.domain.YaOrderSequence;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaOrderSequence entity.
 */
public interface YaOrderSequenceRepository extends JpaRepository<YaOrderSequence,Long> {

}
