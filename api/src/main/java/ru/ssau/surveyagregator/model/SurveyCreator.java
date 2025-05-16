package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "surveycreators_table")
public class SurveyCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
