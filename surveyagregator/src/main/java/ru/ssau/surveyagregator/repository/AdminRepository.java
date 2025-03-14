package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.surveyagregator.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
