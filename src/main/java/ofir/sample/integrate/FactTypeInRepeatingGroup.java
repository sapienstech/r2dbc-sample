package ofir.sample.integrate;

import com.google.common.collect.Sets;

import java.util.Set;

public class FactTypeInRepeatingGroup {
	private Integer id;
	private String name;
	private FactDataType dataType;
	private Set<String> allowedValues = Sets.newHashSet();
	private boolean isList;

	public FactTypeInRepeatingGroup() {
	}

	public FactTypeInRepeatingGroup(Integer id, String name, FactDataType dataType, Set<String> allowedValues, boolean isList) {
		this.id = id;
		this.name = name;
		this.dataType = dataType;
		this.allowedValues = allowedValues;
		this.isList = isList;
	}

	public Integer getId() {
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

	public FactDataType getDataType() {
		return dataType;
	}

	public void setDataType(FactDataType dataType) {
		this.dataType = dataType;
	}

	public Set<String> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(Set<String> allowedValues) {
		this.allowedValues = allowedValues;
	}

	public boolean getIsList() {
		return isList;
	}

	public void setList(boolean list) {
		isList = list;
	}

	@Override
	public String toString() {
		return "FactTypeInRepeatingGroup{" +
				"id=" + id +
				", name='" + name + '\'' +
				", dataType=" + dataType +
				", allowedValues=" + allowedValues +
				", isList=" + isList +
				'}';
	}
}
