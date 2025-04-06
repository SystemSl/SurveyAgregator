package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ssau.surveyagregator.model.Survey;

import java.util.List;
import java.util.UUID;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, UUID> {
    @Query("SELECT s FROM Survey s JOIN s.users u WHERE u.id = :userId")
    List<Survey> findSurveysByAdminId(@Param("userId") UUID userId);
}
