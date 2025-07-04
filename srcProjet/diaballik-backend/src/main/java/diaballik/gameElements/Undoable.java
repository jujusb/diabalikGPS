package diaballik.gameElements;

/**
 * interface Undoable
 * Interface used to specify the behaviour of an action
 */
public interface Undoable {
    boolean undo();
    boolean redo();
}
