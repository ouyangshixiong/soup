package com.youai.repository;

import com.youai.domain.YaQuestion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YaQuestion entity.
 */
public interface YaQuestionRepository extends JpaRepository<YaQuestion,Long> {

}
