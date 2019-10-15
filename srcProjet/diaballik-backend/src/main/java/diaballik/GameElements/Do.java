package diaballik.GameElements;

import diaballik.Coordinates.ActionCoord;
import diaballik.Players.Player;

public abstract class Do implements Undoable {
    public abstract boolean move(Player p, ActionCoord coords);
    public abstract boolean canMove(Player p, ActionCoord coords);
}
