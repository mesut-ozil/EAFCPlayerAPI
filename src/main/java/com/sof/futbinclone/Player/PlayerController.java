package com.sof.futbinclone.Player;

import com.sof.futbinclone.Exceptions.InvalidFilterParameterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.lang.reflect.Field;

@RestController //processes incoming HTTP requests
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping //returns all players
    public List<PlayerResponse> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public PlayerResponse findById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping("/search") //finds players by name
    public List<PlayerResponse> findByName(@RequestParam String name) {
        return playerService.findByName(name);
    }

    @GetMapping("/filter")//uses the default uri. Also, Spring luckily creates criteria class at runtime and populates its fields
    public Page<PlayerResponse> filterPlayers(@Valid PlayerSearchCriteria criteria, @Min(0) @RequestParam(defaultValue = "0") int page, HttpServletRequest request) { //allows for pagination to prevent too many returned objects
        for (String parameter : request.getParameterMap().keySet()) { //loops through fields and check the given parameters exist
            if (parameter.equals("page")) {
                continue;
            }
            try {
                PlayerSearchCriteria.class.getDeclaredField(parameter);
            } catch (NoSuchFieldException e) {
                throw new InvalidFilterParameterException("Unknown filter parameter: " + parameter);
            }
        }
        return playerService.filter(criteria, page);
    }

    @PostMapping  //adds players
    public PlayerResponse addPlayer(@RequestBody PlayerRequest player) {
        return playerService.addPlayer(player);
    }
}
