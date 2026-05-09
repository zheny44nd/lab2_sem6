package org.example.lab2.repository;

import org.example.lab2.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l WHERE l.date >= :startDate AND l.date <= :endDate " +
           "AND (:groupId IS NULL OR l.group.id = :groupId) " +
           "AND (:lectorId IS NULL OR l.lector.id = :lectorId)")
    Page<Lesson> findLessonsWithFilters(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("groupId") Long groupId,
            @Param("lectorId") Long lectorId,
            Pageable pageable
    );
}

