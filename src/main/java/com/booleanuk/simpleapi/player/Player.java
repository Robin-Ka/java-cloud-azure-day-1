package com.booleanuk.simpleapi.player;


import com.booleanuk.simpleapi.team.Team;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnoreProperties({"players", "id"})
    private Team team;

    public Player(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Player(int id){
        this.id = id;
    }
}
