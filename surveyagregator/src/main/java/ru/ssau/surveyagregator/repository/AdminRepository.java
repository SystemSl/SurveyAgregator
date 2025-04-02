package ru.ssau.surveyagregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ssau.surveyagregator.model.Admin;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

}
