package diaballik.players.algorithms;

import diaballik.players.AiPlayer;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AiAlgoAdaptor extends XmlAdapter<String, Algo> {
    Algo a;

    @Override
    public Algo unmarshal(final String scenarioId) {
        return a;
    }

    @Override
    public String marshal(final Algo scenario) {
        a = scenario;
        if (((AiPlayer) a.player).getTURNS_BEFORE_SWAP() == -1) {
            if (scenario instanceof NoobAlgo) {
                return "Noob";
            } else if (scenario instanceof StartingAlgo) {
                return "Starting";
            } else if (scenario instanceof MonteCarloAlgo) {
                return "MonteCarlo";
            }
        }
        return "Progressive";
    }
}
