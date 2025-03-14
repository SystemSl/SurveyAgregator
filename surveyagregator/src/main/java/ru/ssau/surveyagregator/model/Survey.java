package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surveyid;

    private String surveytitle;
    private String surveydescription;

    public Survey() {
    }

    public Survey(String surveytitle, String surveydescription) {
        this.surveytitle = surveytitle;
        this.surveydescription = surveydescription;
    }

    public Integer getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(Integer surveyid) {
        this.surveyid = surveyid;
    }

    public String getSurveytitle() {
        return surveytitle;
    }

    public void setSurveytitle(String surveytitle) {
        this.surveytitle = surveytitle;
    }

    public String getSurveydescription() {
        return surveydescription;
    }

    public void setSurveydescription(String surveydescription) {
        this.surveydescription = surveydescription;
    }

    @OneToMany(mappedBy="surveyid")
    private Set<Question> questions = new HashSet<>();

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "surveycreators",
            joinColumns = { @JoinColumn(name = "surveyid") },
            inverseJoinColumns = { @JoinColumn(name = "adminid") })
    private Set<Admin> admins = new HashSet<>();

    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }
}
