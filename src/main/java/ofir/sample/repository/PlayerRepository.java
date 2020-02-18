package ofir.sample.repository;

import ofir.sample.model.DbPlayer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveCrudRepository<DbPlayer, Integer> {

	@Query("select id, name, age from player where name = :name")
	Flux<DbPlayer> findAllByName(String name);

	@Query("select * from player where age = :age")
	Flux<DbPlayer> findByAge(int age);

	@Query("select * from player where team_id = :teamId")
	Flux<DbPlayer> findTeamPlayers(int teamId);

	@Query("select * from player join team on team.captain_id = player.id where team.id = :teamId")
	Flux<DbPlayer> findTeamCaptain(int teamId);
}
