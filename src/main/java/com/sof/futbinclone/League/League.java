package com.sof.futbinclone.League;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Setter;
import lombok.Getter;

@Entity //table in database
@Setter
@Getter
public class League {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public League() {}

    public League(String name) {
        this.name = name;
    }
}
