package com.sof.futbinclone.Player;

import com.sof.futbinclone.League.League;
import com.sof.futbinclone.Team.Team;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Getter @Setter //automatically generates getter and setter methods for convenience using Lombok
public class PlayerSearchCriteria { //holds all filters that a user can choose from

    private Position position;
    private Team team;
    private League league;
    private String nation;
    private Gender gender;
    private PreferredFoot preferredFoot;

    private SortBy sortBy = SortBy.RATING;
    private SortDirection sortDirection = SortDirection.DESC;

    @Min(1) //minimum stat of 1
    @Max(99) //maximum stat of 99
    private Integer maxRating; //Integer instead of int so can be null
    @Min(1)
    @Max(99)
    private Integer minRating;


    @Min(1)
    @Max(15000000)
    private Integer minPrice;
    @Min(1)
    @Max(15000000) //max price of 15 million
    private Integer maxPrice;

    @Min(1)
    @Max(99)
    private Integer minPace;
    @Min(1)
    @Max(99)
    private Integer maxPace;

    @Min(1)
    @Max(99)
    private Integer minShooting;
    @Min(1)
    @Max(99)
    private Integer maxShooting;

    @Min(1)
    @Max(99)
    private Integer minPassing;
    @Min(1)
    @Max(99)
    private Integer maxPassing;

    @Min(1)
    @Max(99)
    private Integer minDribbling;
    @Min(1)
    @Max(99)
    private Integer maxDribbling;

    @Min(1)
    @Max(99)
    private Integer minDefending;
    @Min(1)
    @Max(99)
    private Integer maxDefending;

    @Min(1)
    @Max(99)
    private Integer minPhysical;
    @Min(1)
    @Max(99)
    private Integer maxPhysical;

    @Min(1)
    @Max(5)
    private Integer wf;
    @Min(1)
    @Max(5)
    private Integer sm;

    private CardType cardType;
}
