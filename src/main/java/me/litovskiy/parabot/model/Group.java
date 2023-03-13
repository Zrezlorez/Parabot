package me.litovskiy.parabot.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Table(name = "groups")
@Data
@Accessors(chain = true)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
