package com.booleanuk.simpleapi.team;

import com.booleanuk.simpleapi.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "established_year")
    private int establishedYear;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"team", "id"})
    private List<Player> players;

    public Team(int id){
        this.id = id;
    }

    public Team(String name, int establishedYear, String country){
        this.name = name;
        this.establishedYear = establishedYear;
        this.country = country;
    }

}
