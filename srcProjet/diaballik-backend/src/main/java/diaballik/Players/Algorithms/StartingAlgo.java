package diaballik.Players.Algorithms;

import diaballik.Coordinates.ActionCoord;
import diaballik.Players.Player;

public class StartingAlgo extends Algo {
    public StartingAlgo(final Player p) {
        super(p);
    }

    @Override
    public ActionCoord decideMove() {
        return null;
    }
}
