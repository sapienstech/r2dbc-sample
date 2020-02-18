package ofir.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import io.r2dbc.h2.H2ConnectionFactory;
import ofir.sample.integrate.Bim;
import ofir.sample.model.DbPlayer;
import ofir.sample.model.DbTeam;
import ofir.sample.repository.PlayerRepository;
import ofir.sample.repository.TeamRepository;
import ofir.sample.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class R2dbcApplicationIntegrationTest {


	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	TeamRepository teamRepository;


	@Autowired
	DatabaseClient client;

	@Autowired
	H2ConnectionFactory factory;


	@Before
	public void setup() {

		Hooks.onOperatorDebug();

		List<String> statements = Arrays.asList(//
												"DROP TABLE IF EXISTS player;",
												"DROP TABLE IF EXISTS team;",
												"CREATE table player (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2, age INT NOT NULL, team_id INT);",
												"CREATE table team (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2, captain_id INT, json_as_map VARCHAR2, bim VARCHAR2);"
		);

		statements.forEach(it -> client.execute(it) //
									   .fetch() //
									   .rowsUpdated() //
									   .as(StepVerifier::create) //
									   .expectNextCount(1) //
									   .verifyComplete());

	}


	@Test
	public void testHappy() {

		insertData();

		Flux<DbTeam> allTeams = teamRepository.findAll();
		List<DbTeam> teams = allTeams.collectList().block();
		System.out.println("teams: " + teams);

		Flux<DbPlayer> allPlayers = playerRepository.findAll();
		List<DbPlayer> players = allPlayers.collectList().block();
		System.out.println("players: " + players);
	}

	@Test
	public void testPlayerQueries(){
		insertData();
		List<DbPlayer> byAge = playerRepository.findByAge(32).collectList().block();
		System.out.println("byAge: " + byAge);

		List<DbPlayer> byTeam = playerRepository.findTeamPlayers(1).collectList().block();
		System.out.println("byTeam: " + byTeam);

		List<DbPlayer> byCaptain = playerRepository.findTeamCaptain(1).collectList().block();
		System.out.println("byCaptain: " + byCaptain);

	}

	@Test
	public void testTeamQueries(){
		insertData();
		List<DbTeam> playerTeam = teamRepository.findPlayerTeam(3).collectList().block();
		System.out.println("playerTeam: " + playerTeam);
	}

	@Test
	public void testUpdate(){
		insertData();
		DbTeam team = teamRepository.findById(1).block();
		System.out.println("before: " + team);
		team.setName("changed");

		Mono<DbTeam> save = teamRepository.save(team);
		System.out.println("after: " + save.block());

	}

	@Test
	public void testUpdateQuery(){
		insertData();
		Mono<Integer> updated = teamRepository.updatePlayerTeamName(Lists.newArrayList(1,2), "changed");
		System.out.println("updated: " + updated.block());

		Flux<DbTeam> allTeams = teamRepository.findAll();
		List<DbTeam> teams = allTeams.collectList().block();
		System.out.println("teams: " + teams);
	}

	@Test
	public void testUpdateJsonAsMap() throws JsonProcessingException {
		DbTeam team = new DbTeam();
		team.setJsonAsMap(JsonUtil.toMap("{\n" +
								  "  \"Blood Relationship Type1\": [\"Spouse\", \"Child\", \"Other\"],\n" +
								  "  \"Family Vehicle Budget Amount1\": [\"5000\", \"1000\"],\n" +
								  "  \"Weekly Food Cost1\": [\"100\", \"200\"],\n" +
								  "  \"Vehicle Ownership Percent1\": [\"0\", \"0.15\", \"0.25\", \"0.5\", \"0.75\", \"1\"],\n" +
								  "  \"Vehicle Status1\": [\"Valid\", \"Invalid\"],\n" +
								  "  \"Good Family Indicator\": [\"Yes\", \"No\"],\n" +
								  "  \"Living at Home Indicator1\": [\"Yes\", \"No\"],\n" +
								  "  \"Vehicle Cost1\": [\"500\", \"1000\", \"1500\", \"2000\", \"2500\"],\n" +
				                  " \"name\": \"my name\"\n" +
								  "}\n"));
		DbTeam saved = teamRepository.save(team).block();
		System.out.println("after: " + saved);
	}

	@Test
	public void testUpdateJsonAsPlayer() throws JsonProcessingException {
		DbTeam team = new DbTeam();
		team.setBim(JsonUtil.toObject("{\n" +
												 "  \"id\": \"1\",\n" +
												 "  \"name\": \"root\",\n" +
												 "  \"factTypes\": [\n" +
												 "    {\n" +
												 "      \"id\": \"11\",\n" +
												 "      \"name\": \"ab\",\n" +
												 "      \"dataType\": \"NUMERIC\",\n" +
												 "      \"allowedValues\": [\n" +
												 "        \"1\",\n" +
												 "        \"2\",\n" +
												 "        \"3\"\n" +
												 "      ],\n" +
												 "      \"isList\": \"false\"\n" +
												 "    },\n" +
												 "    {\n" +
												 "      \"id\": \"12\",\n" +
												 "      \"name\": \"cd\",\n" +
												 "      \"dataType\": \"TEXT\",\n" +
												 "      \"allowedValues\": [\n" +
												 "        \"a\",\n" +
												 "        \"b\",\n" +
												 "        \"c\"\n" +
												 "      ],\n" +
												 "      \"isList\": \"true\"\n" +
												 "    }\n" +
												 "  ],\n" +
												 "  \"children\": [\n" +
												 "    {\n" +
												 "      \"id\": \"2\",\n" +
												 "      \"name\": \"2nd-level-first\",\n" +
												 "      \"factTypes\": [\n" +
												 "        {\n" +
												 "          \"id\": \"22\",\n" +
												 "          \"name\": \"xy\",\n" +
												 "          \"dataType\": \"DATE\",\n" +
												 "          \"isList\": \"false\"\n" +
												 "        },\n" +
												 "        {\n" +
												 "          \"id\": \"23\",\n" +
												 "          \"name\": \"qrs\",\n" +
												 "          \"dataType\": \"TEXT\",\n" +
												 "          \"allowedValues\": [\n" +
												 "            \"q\",\n" +
												 "            \"w\",\n" +
												 "            \"e\"\n" +
												 "          ],\n" +
												 "          \"isList\": \"true\"\n" +
												 "        }\n" +
												 "      ]\n" +
												 "    },\n" +
												 "    {\n" +
												 "      \"id\": \"3\",\n" +
												 "      \"name\": \"2nd-level-second\",\n" +
												 "      \"factTypes\": [\n" +
												 "        {\n" +
												 "          \"id\": \"33\",\n" +
												 "          \"name\": \"aggam\",\n" +
												 "          \"dataType\": \"TEXT\",\n" +
												 "          \"isList\": \"false\"\n" +
												 "        },\n" +
												 "        {\n" +
												 "          \"id\": \"34\",\n" +
												 "          \"name\": \"shakked\",\n" +
												 "          \"dataType\": \"TEXT\",\n" +
												 "          \"allowedValues\": [\n" +
												 "            \"qq\",\n" +
												 "            \"ww\",\n" +
												 "            \"ee\"\n" +
												 "          ],\n" +
												 "          \"isList\": \"false\"\n" +
												 "        }\n" +
												 "      ],\n" +
												 "      \"children\": [\n" +
												 "        {\n" +
												 "          \"id\": \"4\",\n" +
												 "          \"name\": \"3rd-level\",\n" +
												 "          \"factTypes\": [\n" +
												 "            {\n" +
												 "              \"id\": \"44\",\n" +
												 "              \"name\": \"maor\",\n" +
												 "              \"dataType\": \"TEXT\",\n" +
												 "              \"isList\": \"false\"\n" +
												 "            },\n" +
												 "            {\n" +
												 "              \"id\": \"45\",\n" +
												 "              \"name\": \"shakked\",\n" +
												 "              \"dataType\": \"NUMERIC\",\n" +
												 "              \"allowedValues\": [\n" +
												 "                \"77\",\n" +
												 "                \"22\",\n" +
												 "                \"999\"\n" +
												 "              ],\n" +
												 "              \"isList\": \"true\"\n" +
												 "            }\n" +
												 "          ]\n" +
												 "        }\n" +
												 "      ]\n" +
												 "    }\n" +
												 "  ]\n" +
												 "}\n", Bim.class));
		DbTeam saved = teamRepository.save(team).block();
		System.out.println("after: " + saved);
	}


	@Test
	public void whenBatchHas2Operations_then2AreExpected() {
		Mono.from(factory.create())
			.flatMapMany(connection -> Flux.from(connection
														 .createBatch()
														 .add("select * from player")
														 .add("select * from player")
														 .execute()))
			.as(StepVerifier::create)
			.expectNextCount(2)
			.verifyComplete();

	}

	private void insertData() {
		List<DbPlayer> players = Arrays.asList(
				new DbPlayer(null, "Kaka", 37, 1),
				new DbPlayer(null, "Messi", 32, 1),
				new DbPlayer(null, "Mbappé", 20, 1),
				new DbPlayer(null, "CR7", 34, 2),
				new DbPlayer(null, "Lewandowski", 30, 2),
				new DbPlayer(null, "Cavani", 32, 2)
		);

		DbTeam team1 = new DbTeam();
		team1.setName("first team");
		team1.setCaptainId(1);

		DbTeam team2 = new DbTeam();
		team2.setName("second team");
		team2.setCaptainId(4);


		teamRepository.saveAll(Lists.newArrayList(team1, team2)).subscribe();
		playerRepository.saveAll(players).subscribe();
	}
}
