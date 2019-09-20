package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cow extends Animal {
	private boolean horns;

	// Why not
	@XmlJavaTypeAdapter(BehaviorAdapter.class)
	private final Behavior behavior;

	public Cow(final String name, final boolean hasHorns, final Behavior behavior) {
		super(name);
		this.horns = hasHorns;
		this.behavior = behavior;
	}

	Cow() {
		this("", false, new StupidBehavior());
	}

	public boolean isHorns() {
		return horns;
	}

	public void setHorns(final boolean horns) {
		this.horns = horns;
	}
}
