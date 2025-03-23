package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerid")
    private Integer answerId;
    @Column(name = "answertext")
    private String answerText;
    @Builder.Default
    @Column(name = "answerquantity")
    private Integer answerQuantity = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "questionid", nullable = false)
    private Question question;

}
