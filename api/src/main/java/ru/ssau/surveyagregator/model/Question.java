package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questions_table")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "question_text")
    private String questionText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @Builder.Default
    @OneToMany(mappedBy="question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Answer> answers = new HashSet<>();

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        answers.add(answer);
    }
}
