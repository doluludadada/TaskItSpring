package org.gu.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "personal_tasks")
@Data
public class PersonalTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String taskName;

    @Column(length = 200)
    private String description;

    private LocalDate startTime;

    private LocalDate endTime;

    private Integer status = 0;
}

