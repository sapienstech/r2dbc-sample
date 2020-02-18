package ofir.sample.integrate;

import com.google.common.collect.Sets;

import java.util.Set;


public class Bim {

	private Integer id;
	private String name;
	private Set<FactTypeInRepeatingGroup> factTypes = Sets.newHashSet();
	private Set<Bim> children = Sets.newHashSet();


	public Bim() {

	}

	public Bim(Integer id, String name, Set<FactTypeInRepeatingGroup> factTypes) {
		this.id = id;
		this.name = name;
		this.factTypes = factTypes;
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


	public Set<FactTypeInRepeatingGroup> getFactTypes() {
		return factTypes;
	}

	public void setFactTypes(Set<FactTypeInRepeatingGroup> factTypes) {
		this.factTypes = factTypes;
	}

	public Set<Bim> getChildren() {
		return children;
	}

	public void setChildren(Set<Bim> children) {
		this.children = children;
	}

	public void addRepeatingGroupChild(Bim repeatingGroupChild) {
		this.children.add(repeatingGroupChild);
	}

	public void addFactType(FactTypeInRepeatingGroup factType) {
		if (factType != null) {
			factTypes.add(factType);
		}
	}

	@Override
	public String toString() {
		return "Bim{" +
				"id=" + id +
				", name='" + name + '\'' +
				", factTypes=" + factTypes +
				", children=" + children +
				'}';
	}
}
