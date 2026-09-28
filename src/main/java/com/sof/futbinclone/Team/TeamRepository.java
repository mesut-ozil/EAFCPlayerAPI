package com.sof.futbinclone.Team;

import com.sof.futbinclone.League.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByNameAndLeague(String name, League league); //ensures we check if a said team belongs to a said league
}
