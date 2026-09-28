package com.sof.futbinclone.Player;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRequest {

    private String name;
    private String nation;

    private CardType cardType;

    private Position position;

    private long price;
    private int rating;

    private String league;
    private String team; //takes string instead of object to be changed

    private int pace;
    private int shooting;
    private int passing;
    private int dribbling;
    private int defending;
    private int physical;
    private int sm;
    private int wf;

    public PlayerRequest() {}
}
