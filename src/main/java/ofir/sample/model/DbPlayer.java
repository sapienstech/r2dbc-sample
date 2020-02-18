package ofir.sample.model;


import ofir.sample.integrate.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("player")
public class DbPlayer extends Player {

	private Integer teamId;

	public DbPlayer() {
	}


	public DbPlayer(Integer id, String name, Integer age, Integer teamId) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.teamId = teamId;
	}

	@Id
	public Integer getId() {
		return id;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		return "DbPlayer{" +
				"teamId=" + teamId +
				", id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
