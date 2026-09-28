package com.sof.futbinclone.Player;

import com.sof.futbinclone.Team.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.smartcardio.Card;

@Entity
@Getter @Setter
public class Player {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String nation;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING) //tells hibernate to store position as a string rather than a smallint
    private Position position;

    private long price;
    private int rating;

    @ManyToOne //many players for one team
    @JoinColumn(name = "team_id") //foreign key column
    private Team team;

    private int pace;
    private int shooting;
    private int passing;
    private int dribbling;
    private int defending;
    private int physical;
    private int sm;
    private int wf;

    public Player() {} //empty constructor recommended so hibernate can create entities from database

    public Player(String name, String nation, CardType cardType, Position position, long price, int rating, Team team, int pace, int shooting, int passing, int dribbling, int defending, int physical, int sm, int wf) {
        this.name = name;
        this.nation = nation;
        this.cardType = cardType;
        this.position = position;
        this.price = price;
        this.team = team;
        this.rating = rating;
        this.pace = pace;
        this.shooting = shooting;
        this.passing = passing;
        this.dribbling = dribbling;
        this.defending = defending;
        this.physical = physical;
        this.sm = sm;
        this.wf = wf;
    }

    public Player(PlayerRequest request) {
        this.name = request.getName();
        this.rating = request.getRating();
        this.cardType = request.getCardType();
        this.position = request.getPosition();
        this.pace = request.getPace();
        this.shooting = request.getShooting();
        this.passing = request.getPassing();
        this.dribbling = request.getDribbling();
        this.defending = request.getDefending();
        this.physical = request.getPhysical();
        this.price = request.getPrice();
        this.sm = request.getSm();
        this.wf = request.getWf();
        this.nation = request.getNation();
    }
}
