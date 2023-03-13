package me.litovskiy.parabot.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "timetable")
@Data
@Accessors(chain = true)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int day;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "end_time")
    private Time endTime;
    private String name;
    private String teacher;
    private String cabinet;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    private boolean numerator;
}
