package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

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
}
