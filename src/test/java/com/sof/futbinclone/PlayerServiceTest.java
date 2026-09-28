package com.sof.futbinclone;

import com.sof.futbinclone.Exceptions.DuplicatePlayerException;
import com.sof.futbinclone.Exceptions.PlayerNotFoundException;
import com.sof.futbinclone.League.League;
import com.sof.futbinclone.League.LeagueRepository;
import com.sof.futbinclone.Player.*;
import com.sof.futbinclone.Team.Team;
import com.sof.futbinclone.Team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository; //creates a mock version of the repository that communicated with the database
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private PlayerService playerService; //injects mocks with a real player service to be tested

    @Test
    void addPlayer_throwsWhenDuplicate() {
        League league = new League("Premier League");
        when(leagueRepository.findByName("Premier League")).thenReturn(Optional.of(league)); //when method is called, pretend premier league already exists

        Team team = new Team("Arsenal", league);
        when(teamRepository.findByNameAndLeague("Arsenal", league)).thenReturn(Optional.of(team)); //pretend team already exists

        when(playerRepository.existsByNameAndTeamAndRatingAndPositionAndCardType("Test Player", team, 85, Position.ST, CardType.GOLD))
                .thenReturn(true); //pretend player exists and return true

        PlayerRequest request = new PlayerRequest();
        request.setName("Test Player");
        request.setTeam("Arsenal");
        request.setRating(85);
        request.setLeague("Premier League");
        request.setPosition(Position.ST);
        request.setCardType(CardType.GOLD); //pad request with test attributes defined above

        assertThrows(DuplicatePlayerException.class, () -> playerService.addPlayer(request)); //assert that the correct exception is thrown

    }

    @Test
    void addPlayer_savesWhenNotDuplicate() {
        League league = new League("Premier League");
        when(leagueRepository.findByName("Premier League")).thenReturn(Optional.of(league)); //when method is called, pretend premier league already exists

        Team team = new Team("Arsenal", league);
        when(teamRepository.findByNameAndLeague("Arsenal", league)).thenReturn(Optional.of(team)); //pretend team already exists

        when(playerRepository.existsByNameAndTeamAndRatingAndPositionAndCardType("Test Player", team, 85, Position.ST, CardType.GOLD))
                .thenReturn(false); //pretend player does not exist and return false

        when(playerRepository.save(any(Player.class))).thenAnswer(x-> x.getArgument(0));
        //returns the player that was passed into the save to prevent null pointer exception further in the test

        PlayerRequest request = new PlayerRequest();
        request.setName("Test Player");
        request.setTeam("Arsenal");
        request.setRating(85);
        request.setLeague("Premier League");
        request.setPosition(Position.ST);
        request.setCardType(CardType.GOLD);

        playerService.addPlayer(request);

        verify(playerRepository).save((any(Player.class))); //check that save called the repository with any Player (specific player is long and complex; test is good enough)
    }

    @Test
    void getPlayerById() {
        League league = new League();
        league.setName("Test League");
        Team team = new Team();
        team.setName("Test Team");
        team.setLeague(league);

        Player player = new Player();
        player.setId(1);
        player.setTeam(team);
        player.setName("Test Player");
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player)); //arranging the mock repo

        PlayerResponse result = playerService.getPlayerById(1L); //acting by creating player

        assertEquals(1L, result.getId());
        assertEquals("Test Player", result.getName()); //asserting the called player is what its meant to be

        verify(playerRepository).findById(1L); //verify that the method was actually called
    }

    @Test
    void getPlayerById_throwsWhenNotFound() {
        when(playerRepository.findById(999L)).thenReturn(Optional.empty()); //make finding by id 999 return nothing

        assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayerById(999L));

        verify(playerRepository).findById(999L); //verify that the method was actually called
    }

    @Test
    void getAllPlayers() {
        League league = new League();
        league.setName("Test League");
        Team team = new Team();
        team.setName("Test Team");
        team.setLeague(league); //creating teams and leagues as PlayerResponse requires a non null value for both

        Player player1 = new Player();
        player1.setId(1L);
        player1.setTeam(team);
        Player player2 = new Player();
        player2.setId(2L);
        player2.setTeam(team);
        Player player3 = new Player();
        player3.setId(3L); //3 player objects
        player3.setTeam(team);
        when(playerRepository.findAll()).thenReturn(List.of(player1, player2, player3));

        List<PlayerResponse> result = playerService.getAllPlayers();

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(3L, result.get(2).getId())
        ); //service method uses streams so preserves order so can just call in their order

        verify(playerRepository).findAll();
    }

    @Test
    void getAllPlayers_emptyCase() {
        when(playerRepository.findAll()).thenReturn(List.of()); //return empty list

        List<PlayerResponse> result = playerService.getAllPlayers();

        assertEquals(0, result.size());

        verify(playerRepository).findAll();
    }

    @Test
    void searchByPlayerName() {
        League league = new League();
        league.setName("Test League");
        Team team = new Team();
        team.setName("Test Team");
        team.setLeague(league);

        Player player1 = new Player();
        player1.setName("Test Player");
        player1.setTeam(team);
        Player player2 = new Player();
        player2.setName("Test Player 2");
        player2.setTeam(team);
        when(playerRepository.findByNameContainingIgnoreCase("Te")).thenReturn(List.of(player1, player2));

        List<PlayerResponse> result = playerService.findByName("Te");

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("Test Player", result.get(0).getName()),
                () -> assertEquals("Test Player 2", result.get(1).getName())
        );

        verify(playerRepository).findByNameContainingIgnoreCase("Te");
    }

    @Test
    void searchByPlayerName_noResults() {
        when(playerRepository.findByNameContainingIgnoreCase("Te")).thenReturn(List.of());

        List<PlayerResponse> result = playerService.findByName("Te");

        assertEquals(0, result.size());

        verify(playerRepository).findByNameContainingIgnoreCase("Te");
    }
}
