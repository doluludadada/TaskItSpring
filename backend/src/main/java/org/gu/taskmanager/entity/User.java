package org.gu.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 254, unique = true)
    private String email;


    @OneToMany(mappedBy = "user")
    private Set<PersonalTask> personalTasks;
}

