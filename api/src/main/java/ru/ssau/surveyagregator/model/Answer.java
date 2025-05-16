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
@Table(name = "answers_table")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "answer_text")
    private String answerText;
    @Builder.Default
    @Column(name = "answer_quantity")
    private BigDecimal answerQuantity = BigDecimal.valueOf(0);

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
