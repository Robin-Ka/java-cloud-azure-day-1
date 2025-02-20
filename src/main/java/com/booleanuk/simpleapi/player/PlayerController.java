package com.booleanuk.simpleapi.player;


import com.booleanuk.simpleapi.team.Team;
import com.booleanuk.simpleapi.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("players")
@CrossOrigin
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(){
        return ResponseEntity.ok(playerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getOnePlayer(@PathVariable int id){
        return this.playerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player){
        Team team = teamRepository.findById(player.getTeam().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found!"));

        player.setTeam(team);

        return new ResponseEntity<Player>(this.playerRepository.save(player), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePLayer(@PathVariable int id, @RequestBody Player player){
        Player playerToUpdate = this.playerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );

        playerToUpdate.setFirstName(player.getFirstName());
        playerToUpdate.setLastName(player.getLastName());
        playerToUpdate.setTeam(player.getTeam());

        return new ResponseEntity<Player>(this.playerRepository.save(playerToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable int id){
        Player playerToDelete = this.playerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );
        Team team = playerToDelete.getTeam();
        if (team != null) {
            team.getPlayers().remove(playerToDelete);
        }

        this.playerRepository.delete(playerToDelete);
        return ResponseEntity.ok(playerToDelete);
    }
}
