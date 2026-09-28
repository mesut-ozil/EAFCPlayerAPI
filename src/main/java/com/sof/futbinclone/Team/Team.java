package com.sof.futbinclone.Team;

import com.sof.futbinclone.League.League;
import jakarta.persistence.*;

import lombok.Setter;
import lombok.Getter;

@Entity //table in the database
@Setter
@Getter
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    public Team() {}

    public Team(String name, League league) {
        this.name = name;
        this.league  = league;
    }
}
