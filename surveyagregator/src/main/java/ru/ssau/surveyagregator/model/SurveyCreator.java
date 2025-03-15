package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "surveycreators")
public class SurveyCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminrelid")
    private Integer adminRelId;

    @ManyToOne
    @JoinColumn(name = "surveyid", nullable = false)
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "adminid", nullable = false)
    private Admin admin;
}
