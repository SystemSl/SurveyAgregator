package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.surveyagregator.model.Survey;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    @Query("SELECT s FROM Survey s JOIN s.admins a WHERE a.adminId = :adminId")
    List<Survey> findSurveysByAdminId(@Param("adminId") Integer adminId);
}
