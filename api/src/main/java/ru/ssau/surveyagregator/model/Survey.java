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
@Table(name = "surveys_table")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "survey_title")
    private String surveyTitle;
    @Column(name = "survey_description")
    private String surveyDescription;

    @Builder.Default
    @OneToMany(mappedBy="survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "surveycreators_table",
            joinColumns = {@JoinColumn(name = "survey_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users = new HashSet<>();

    public void addQuestion(Question question) {
        question.setSurvey(this);
        questions.add(question);
    }

    public void addUser(User user) {
        users.add(user);
    }
}
