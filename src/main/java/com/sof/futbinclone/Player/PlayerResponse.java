package com.sof.futbinclone.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerResponse {

    private Long id;
    private String name;
    private String nation;
    private Position position;
    private CardType cardType;

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

    public PlayerResponse() {}

    public PlayerResponse(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.nation = player.getNation();
        this.position = player.getPosition();
        this.cardType = player.getCardType();
        this.price = player.getPrice();
        this.rating = player.getRating();
        this.league = player.getTeam().getLeague().getName();
        this.team = player.getTeam().getName();
        this.pace = player.getPace();
        this.shooting = player.getShooting();
        this.passing = player.getPassing();
        this.dribbling = player.getDribbling();
        this.defending = player.getDefending();
        this.physical = player.getPhysical();
        this.sm = player.getSm();
        this.wf = player.getWf();
    }
}
