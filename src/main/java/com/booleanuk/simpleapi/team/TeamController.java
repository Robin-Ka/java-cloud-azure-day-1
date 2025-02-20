package com.booleanuk.simpleapi.team;

import com.booleanuk.simpleapi.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("teams")
@CrossOrigin
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams(){
        return ResponseEntity.ok(teamRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
        return new ResponseEntity<Team>(this.teamRepository.save(team), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getOneTeam(@PathVariable int id){
        return this.teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable int id, @RequestBody Team team){
        Team teamToUpdate = this.teamRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );

        teamToUpdate.setName(team.getName());

        return new ResponseEntity<Team>(this.teamRepository.save(teamToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Team> deleteTeam(@PathVariable int id){
        Team teamToDelete = this.teamRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );
        this.teamRepository.delete(teamToDelete);
        return ResponseEntity.ok(teamToDelete);
    }
}
