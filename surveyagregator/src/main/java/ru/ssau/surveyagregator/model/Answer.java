package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "answerid")
    private UUID answerId;
    @Column(name = "answertext")
    private String answerText;
    @Builder.Default
    @Column(name = "answerquantity")
    private BigDecimal answerQuantity = BigDecimal.valueOf(0);

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "questionid", nullable = false)
    private Question question;

}
