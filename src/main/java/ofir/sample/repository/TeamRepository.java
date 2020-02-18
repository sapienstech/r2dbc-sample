package ofir.sample.repository;

import ofir.sample.model.DbTeam;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface TeamRepository extends ReactiveCrudRepository<DbTeam, Integer> {
	@Query("select * from team join player on team.id = player.team_id and player.id = :playerId")
	Flux<DbTeam> findPlayerTeam(int playerId);

	@Query("update team set name = :name where team.id in (:ids)")
	Mono<Integer> updatePlayerTeamName(Collection<Integer> ids, String name);
}
