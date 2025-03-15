package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surveyid")
    private Integer surveyId;
    @Column(name = "surveytitle")
    private String surveyTitle;
    @Column(name = "surveydescription")
    private String surveyDescription;

    public Survey() {
    }

    public Survey(String surveyTitle, String surveyDescription) {
        this.surveyTitle = surveyTitle;
        this.surveyDescription = surveyDescription;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
    }

    @OneToMany(mappedBy="survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "surveyCreators",
            joinColumns = { @JoinColumn(name = "surveyid") },
            inverseJoinColumns = { @JoinColumn(name = "adminid") })
    private Set<Admin> admins = new HashSet<>();

    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }

    public void addQuestion(Question question) {
        question.setSurvey(this);
        questions.add(question);
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }
}
