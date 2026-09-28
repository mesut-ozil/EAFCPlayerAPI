package com.sof.futbinclone.Player;

import com.sof.futbinclone.Team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //annotation to show class can complete CRUD operations
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {

   List<Player> findByNameContainingIgnoreCase(String name);

   boolean existsByNameAndTeamAndRatingAndPositionAndCardType(String name, Team team, int Rating, Position position, CardType cardType); //checks for duplicate players through said parameters
}
//provides database manipulation methods without having to write implementation. Long represents ID type for player