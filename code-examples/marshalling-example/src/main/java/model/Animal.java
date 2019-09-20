package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
// Should consider the attributes during the marshalling
@XmlAccessorType(XmlAccessType.FIELD)
// We have to identify the sub-classes to help the marshalling
// If several sub-classes: @XmlSeeAlso({Cow.class, Dog.class})
@XmlSeeAlso(Cow.class)
public abstract class Animal {
	// why not
	protected String name;

	// Will not marshal this attribute
	@XmlTransient
	protected int attributeToIgnore;

	public Animal(final String animalName) {
		super();
		name = animalName;
	}

	// The marshalling library we use require a default constructor (no arg).
	Animal() {
		this("");
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
