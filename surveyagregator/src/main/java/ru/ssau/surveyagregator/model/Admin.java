package ru.ssau.surveyagregator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="adminid")
    private UUID adminId;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
}
