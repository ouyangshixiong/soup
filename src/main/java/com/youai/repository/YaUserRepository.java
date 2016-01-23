package com.youai.repository;

import com.youai.domain.YaUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaUser entity.
 */
public interface YaUserRepository extends JpaRepository<YaUser,Long> {

}
