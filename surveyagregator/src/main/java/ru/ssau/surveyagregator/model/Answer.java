package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerid;

    private String answertext;

    private Integer answerquantity;

    public Answer() {
    }

    public Answer(String answertext, Integer answerquantity) {
        this.answertext = answertext;
        this.answerquantity = answerquantity;
    }

    public Integer getAnswerid() {
        return answerid;
    }

    public void setAnswerid(Integer answerid) {
        this.answerid = answerid;
    }

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext;
    }

    public Integer getAnswerquantity() {
        return answerquantity;
    }

    public void setAnswerquantity(Integer answerquantity) {
        this.answerquantity = answerquantity;
    }
    @ManyToOne
    private Question questionid;
}
