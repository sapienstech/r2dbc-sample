package ofir.sample.integrate;

import java.util.Map;

public class Team {
	protected Integer id;
	protected String name;
	protected Map<String, Object> jsonAsMap;
	protected Bim bim;


	public Integer getId(){
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getJsonAsMap() {
		return jsonAsMap;
	}

	public void setJsonAsMap(Map<String, Object> jsonAsMap) {
		this.jsonAsMap = jsonAsMap;
	}

	public Bim getBim() {
		return bim;
	}

	public void setBim(Bim bim) {
		this.bim = bim;
	}
}
