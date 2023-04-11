package day26workshop.gameapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import day26workshop.gameapp.model.Game;
import day26workshop.gameapp.model.Games;
import day26workshop.gameapp.repository.BoardgameRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class BoardgameController {
    
    @Autowired
    private BoardgameRepository bgRepo;
    
    // to fetch boardgames
    @GetMapping(path="/games")
    public ResponseEntity<String> getBoardgame(@RequestParam Integer offset, 
            @RequestParam Integer limit){
        
        List<Game> gamesList = bgRepo.getAllGames(offset, limit);
        Games games = new Games();
        games.setGames(gamesList);
        games.setOffset(offset);
        games.setLimit(limit);
        games.setTotal(gamesList.size());
        games.setTimestamp(LocalDate.now());

        JsonObject result = Json.createObjectBuilder()
                .add("boardgames", games.toJson())
                .build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    @GetMapping(path="/games/rank")
    public ResponseEntity<String> getSortedGamesRanking(@RequestParam Integer offset, 
            @RequestParam Integer limit) {

        List<Game> gamesList = bgRepo.getSortedBoardGames(offset, limit);
        Games games = new Games();
        games.setGames(gamesList);
        games.setOffset(offset);
        games.setLimit(limit);
        games.setTotal(gamesList.size());
        games.setTimestamp(LocalDate.now());

        JsonObject result = Json.createObjectBuilder()
                .add("boardgames", games.toJson())
                .build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());

    }

    @GetMapping(path="/game/{gameId}")
    public ResponseEntity<String> getGameById(@PathVariable Integer gameId) {
        Game game = bgRepo.getGameById(gameId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(game.toJson().toString());
    }
}
