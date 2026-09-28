package com.sof.futbinclone.Player;
import com.sof.futbinclone.Exceptions.DuplicatePlayerException;
import com.sof.futbinclone.Exceptions.PlayerNotFoundException;
import com.sof.futbinclone.League.League;
import com.sof.futbinclone.League.LeagueRepository;
import com.sof.futbinclone.Team.Team;
import com.sof.futbinclone.Team.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service //class represents business logic
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.playerRepository = playerRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    public List<PlayerResponse> getAllPlayers() {
        return playerRepository.findAll().stream().map(PlayerResponse::new).toList(); //returns all players in database
    }

    public PlayerResponse getPlayerById(Long id) {
        return new PlayerResponse(playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found")));
    }

    public PlayerResponse addPlayer(PlayerRequest request) {
        String leagueName = request.getLeague();
        String teamName = request.getTeam();

        League league = leagueRepository.findByName(leagueName).orElseGet(() -> leagueRepository.save(new League(leagueName))); //checks if league exists otherwise creates new
        Team team = teamRepository.findByNameAndLeague(teamName, league).orElseGet(() -> teamRepository.save(new Team(teamName, league))); //checks if team exists in said league otherwise creates new

        if(playerRepository.existsByNameAndTeamAndRatingAndPositionAndCardType(request.getName(), team, request.getRating(), request.getPosition(), request.getCardType())) {
            throw new DuplicatePlayerException("Player already exists"); //checks for duplicates when adding by key
        }

        Player player = new Player(request); //playerRequest to player
        player.setTeam(team); //set the team

        return new PlayerResponse(playerRepository.save(player)); //return player converted into a playerResponse
    }

    public List<PlayerResponse> findByName(String name) {
        return playerRepository.findByNameContainingIgnoreCase(name).stream().map(PlayerResponse::new).toList();
    }

    public Page<PlayerResponse> filter(PlayerSearchCriteria criteria, int page) {
        Specification<Player> specification = PlayerSpecification.filterBy(criteria); //filter picked by user

        Sort.Direction direction = Sort.Direction.valueOf(criteria.getSortDirection().name()); //Set sort direction to user's request
        Sort sort = Sort.by(direction, criteria.getSortBy().name().toLowerCase()); //initialises sort criteria
        Pageable pageable = PageRequest.of(page, 20, sort); //get page number specified by user and limit number of results to 20

        return playerRepository.findAll(specification, pageable).map(PlayerResponse::new); //runs SQL query based on filters inserted by user and returns relevant players
    }

}
