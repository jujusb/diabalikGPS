package diaballik.Players.Algorithms;

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
        if (scenario instanceof NoobAlgo) {
            return "Noob";
        }
        if (scenario instanceof StartingAlgo) {
            return "Starting";
        }
        // etc.
        return "Progressive";
    }
}
