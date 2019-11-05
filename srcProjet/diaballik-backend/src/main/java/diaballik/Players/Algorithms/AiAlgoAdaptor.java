package diaballik.Players.Algorithms;

import diaballik.Players.Player;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AiAlgoAdaptor extends XmlAdapter<Integer, Algo> {
    Player p;

    @Override
    public Algo unmarshal(final Integer scenarioId) {
        switch (scenarioId) {
            case 1:
                return new StartingAlgo(p);
            // etc.
            default:
                return new NoobAlgo(p);
        }
    }

    @Override
    public Integer marshal(final Algo scenario) {
        if (scenario instanceof NoobAlgo) {
            p = scenario.player;
            return 0;
        }
        if (scenario instanceof StartingAlgo) {
            p = scenario.player;
            return 1;
        }
        // etc.
        p = scenario.player;
        return 0;
    }
}
