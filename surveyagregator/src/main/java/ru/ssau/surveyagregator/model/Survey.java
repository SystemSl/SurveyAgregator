package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Builder.Default
    @OneToMany(mappedBy="survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "surveyCreators",
            joinColumns = { @JoinColumn(name = "surveyid") },
            inverseJoinColumns = { @JoinColumn(name = "adminid") })
    private Set<Admin> admins = new HashSet<>();

    public void addQuestion(Question question) {
        question.setSurvey(this);
        questions.add(question);
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }
}
