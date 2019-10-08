package diaballik.Coordinates;

import java.util.Objects;

/**
 * @class Coordinate
 * Class used to handle the coordinates of the pawns in the game
 */
public class Coordinate {

    /**
     * The x coordinate
     */
    private int posX;
    /**
     * The y coordinate
     */
    private int posY;

    /**
     * Constructor of the class
     * @param posX x coordinate as an int
     * @param posY y coordinate as an int
     */
    public Coordinate(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Getter of x
     * @return the x coordinate as an int
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getter of y
     * @return the y coordinate as an int
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Moves the coordinates to another absolute position
     * @param c the coordinates we want to move to as a Coordinate object
     */
    public void moveTo(final Coordinate c) {
        this.posX = c.getPosX();
        this.posY = c.getPosY();
    }

    /**
     * Checks if two coordinates do have the same value
     * @param o The other coordinate Object (or something else)
     * @return true or false if it is equal or not
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Coordinate)) {
            return false;
        }
        final Coordinate that = (Coordinate) o;
        return posX == that.posX &&
                posY == that.posY;
    }

    /**
     * Computes the hash of a coordinate object
     * @return the hashcode of the coordinates
     */
    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }

    /**
     * Return the string from posX and posY
     * @return the current Coordinate in String format
     */
    @Override
    public String toString() {
        return "(" + posX + " , " + posY +
                ')';
    }
}
