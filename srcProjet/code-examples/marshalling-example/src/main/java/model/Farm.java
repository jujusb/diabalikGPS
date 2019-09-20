package model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Farm {
	private final List<Animal> animals;

	public Farm() {
		super();
		animals = new ArrayList<>();
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void addAnimal(final Animal animal) {
		animals.add(animal);
	}
}
