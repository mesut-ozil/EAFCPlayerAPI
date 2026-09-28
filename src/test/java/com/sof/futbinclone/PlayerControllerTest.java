package com.sof.futbinclone;

import com.sof.futbinclone.Exceptions.PlayerNotFoundException;
import com.sof.futbinclone.Player.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;

@WebMvcTest(PlayerController.class) //test on the controller class
public class PlayerControllerTest {
    @MockitoBean //instead of mock so Spring can automatically inject dependencies
    private PlayerService playerService;
    @Autowired //gives existing bean, created from the WebMvcTest annotation
    private MockMvc mockMvc; //mocks a client that sends real requests. Test becomes more realistic

    @Test
    void getPlayerById_successful() throws Exception {
        PlayerResponse response = new PlayerResponse();
        response.setId(1L);
        response.setName("Test Player");
        when(playerService.getPlayerById(1L)).thenReturn(response);

        mockMvc.perform(get("/players/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1)) //dollar representing root of json
                .andExpect(jsonPath("$.name").value("Test Player"));

        verify(playerService).getPlayerById(1L);
    }

    @Test
    void getPlayerById_unsuccessful() throws Exception {
        when(playerService.getPlayerById(1L)).thenThrow(PlayerNotFoundException.class);

        mockMvc.perform(get("/players/1")).andExpect(status().isNotFound());

        verify(playerService).getPlayerById(1L);
    }

    @Test
    void getAllPlayers_successful() throws Exception {
        PlayerResponse player1 = new PlayerResponse();
        player1.setId(1L);
        PlayerResponse player2 = new PlayerResponse();
        player2.setId(2L);

        when(playerService.getAllPlayers()).thenReturn(List.of(player1, player2));

        mockMvc.perform(get("/players")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1)) //check first object has id 1
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$.length()").value(2)); //finds length of json array

        verify(playerService).getAllPlayers();
    }

    @Test
    void getAllPlayers_empty() throws Exception {
        when(playerService.getAllPlayers()).thenReturn(List.of());

        mockMvc.perform(get("/players")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(playerService).getAllPlayers();
    }

    @Test
    void getPlayerByName_successful() throws Exception {
        PlayerResponse player1 = new PlayerResponse();
        player1.setName("Test Player 1");
        PlayerResponse player2 = new PlayerResponse();
        player2.setName("Test Player 2");

        when(playerService.findByName("Pl")).thenReturn(List.of(player1, player2));

        mockMvc.perform(get("/players/search?name=Pl")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Player 1"))
                .andExpect(jsonPath("$[1].name").value("Test Player 2"));

        verify(playerService).findByName("Pl");
    }

    @Test
    void getPlayerByName_empty() throws Exception {
        when(playerService.findByName("Test")).thenReturn(List.of());

        mockMvc.perform(get("/players/search?name=Test")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(playerService).findByName("Test");
    }

    @Test
    void filterPlayer_successful() throws Exception {
        PlayerResponse player1 = new PlayerResponse();
        player1.setId(1L);
        player1.setName("Test Player 1");
        PlayerResponse player2 = new PlayerResponse();
        player2.setId(2L);
        player2.setName("Test Player 2");
        Page<PlayerResponse> page = new PageImpl<>(List.of(player1, player2)); //page is an interface implemented by PageImpl

        when(playerService.filter(any(PlayerSearchCriteria.class), eq(0))).thenReturn(page); //whenever a criteria is passed in return results as a page, page 0 is default

        mockMvc.perform(get("/players/filter?minRating=85")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1)) //pages return as content in json
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content.length()").value(2));

        verify(playerService).filter(any(PlayerSearchCriteria.class), eq(0));
    }

    @Test
    void addPlayer_successful() throws Exception {
        PlayerResponse response = new PlayerResponse();
        response.setId(1L);
        response.setName("Test Player");

        when(playerService.addPlayer(any(PlayerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON) //input is in json
                        .content("""
                            {
                                "name": "Test Player",
                                "nation": "France",
                                "position": "ST",
                                "rating": 91,
                                "pace": 97
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Player"));

        verify(playerService).addPlayer(any(PlayerRequest.class));
    }
}
