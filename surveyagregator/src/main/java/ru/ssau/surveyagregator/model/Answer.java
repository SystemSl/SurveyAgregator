package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answerid")
    private Integer answerId;
    @Column(name="answertext")
    private String answerText;
    @Column(name="answerquantity")
    private Integer answerQuantity;

    public Answer() {
    }

    public Answer(String answerText, Integer answerQuantity) {
        this.answerText = answerText;
        this.answerQuantity = answerQuantity;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getAnswerQuantity() {
        return answerQuantity;
    }

    public void setAnswerQuantity(Integer answerQuantity) {
        this.answerQuantity = answerQuantity;
    }
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="questionid", nullable = false)
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
