package diaballik.coordinates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * @class Coordinate
 * Class used to handle the coordinates of the pawns in the game
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinate implements Cloneable {

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
     *
     * @param posX x coordinate as an int
     * @param posY y coordinate as an int
     */
    public Coordinate(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public Coordinate() {
    }

    /**
     * Getter of x
     *
     * @return the x coordinate as an int
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getter of y
     *
     * @return the y coordinate as an int
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Moves the coordinates to another absolute position
     *
     * @param c the coordinates we want to move to as a Coordinate object
     */
    public void moveTo(final Coordinate c) {
        this.posX = c.getPosX();
        this.posY = c.getPosY();
    }

    /**
     * Relatively moves the coordinates of (x,y)
     * @param x the delta x of the move
     * @param y the delta y of the move
     */
    public void moveOf(final int x, final int y) {
        posX += x;
        posY += y;
    }

    /**
     * Checks if two coordinates do have the same value
     *
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
     *
     * @return the hashcode of the coordinates
     */
    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }

    /**
     * Return the string from posX and posY
     *
     * @return the current Coordinate in String format
     */
    @Override
    public String toString() {
        return "(" + posX + " , " + posY +
                ')';
    }

    /**
     * Clones a coordinate
     * @return a new Coordinate with the same posX and posY
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
        //return new Coordinate(posX, posY);
    }

    /**
     * Calculates the absolute distance beetween two points
     *
     * @param c the other coordinate from which we calculate the distance
     * @return the sum of the absolute difference of the coordinates
     */
    public int absoluteDistance(final Coordinate c) {
        return Math.abs(c.getPosX() - posX) + Math.abs(c.getPosY() - posY);
    }

    /**
     * Checks if two points are in the same diagonal by checking that abs(delta X) is abs(delta Y)
     *
     * @param c the other coordinate
     * @return true if it is in the same diagonal false otherwise
     */
    public boolean sameDiagonal(final Coordinate c) {
        return Math.abs(c.getPosX() - posX) == Math.abs(c.getPosY() - posY);
    }

    /**
     * Checks if two points are in the same horizontal line by checking the equality of their y
     *
     * @param c the other coordinate
     * @return true if it is in the same horizontal false otherwise
     */
    public boolean sameHorizontal(final Coordinate c) {
        return c.getPosY() == posY;
    }

    /**
     * Checks if two points are in the same vertical line by checking the equality of their x
     *
     * @param c the other coordinate
     * @return true if it is in the same vertical false otherwise
     */
    public boolean sameVertical(final Coordinate c) {
        return c.getPosX() == posX;
    }

}
