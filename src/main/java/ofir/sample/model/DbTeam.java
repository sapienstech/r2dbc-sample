package ofir.sample.model;

import ofir.sample.integrate.Team;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("team")
@AccessType(AccessType.Type.PROPERTY)
public class DbTeam extends Team {

	private Integer captainId;

	public DbTeam() {
	}

	public DbTeam(Integer id, String name, Integer captainId) {
		this.id = id;
		this.name = name;
		this.captainId = captainId;
	}

	@Id
	public Integer getId() {
		return id;
	}

	public Integer getCaptainId() {
		return captainId;
	}

	public void setCaptainId(Integer captainId) {
		this.captainId = captainId;
	}

	@Override
	public String toString() {
		return "DbTeam{" +
				"captainId=" + captainId +
				", id=" + id +
				", name='" + name + '\'' +
				", jsonAsMap=" + jsonAsMap +
				", bim=" + bim +
				'}';
	}
}
