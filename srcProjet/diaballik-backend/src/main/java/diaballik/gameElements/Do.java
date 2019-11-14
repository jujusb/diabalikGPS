package diaballik.gameElements;

import diaballik.coordinates.ActionCoord;
import diaballik.players.Player;

public abstract class Do implements Undoable {
    public abstract boolean move(Player p, ActionCoord coords);
    public abstract boolean canMove(Player p, ActionCoord coords);
}
