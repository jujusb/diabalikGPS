package model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BehaviorAdapter extends XmlAdapter<Integer, Behavior> {
	@Override
	public Behavior unmarshal(final Integer scenarioId) {
		switch(scenarioId) {
			case 0: return new StupidBehavior();
			case 1: return new SmartBehavior();
			// etc.
			default: return new StupidBehavior();
		}
	}

	@Override
	public Integer marshal(final Behavior scenario) {
		if(scenario instanceof StupidBehavior) {
			return 0;
		}
		if(scenario instanceof SmartBehavior) {
			return 1;
		}
		// etc.
		return 0;
	}
}
