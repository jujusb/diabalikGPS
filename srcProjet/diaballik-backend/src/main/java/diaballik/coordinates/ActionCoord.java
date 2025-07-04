package diaballik.coordinates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * class ActionCoord
 * A pair of Coordinate representing a move on the GameBoard.
 *
 * @see Coordinate
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ActionCoord implements Cloneable {

    /**
     * The Coordinate of the Pawn to move
     */
    private Coordinate source;

    /**
     * The Coordinate where we want the Pawn to go
     */
    private Coordinate target;

    /**
     * Constructor of the class
     *
     * @param s the Coordinate of the Pawn to move
     * @param t the Coordinate where we want the Pawn to go
     */
    public ActionCoord(final Coordinate s, final Coordinate t) {
        source = s;
        target = t;
    }

    public ActionCoord() {
    }

    /**
     * Getter of source
     *
     * @return source as a Coordinate
     * @link ActionCoord.source
     */
    public Coordinate getSource() {
        return source;
    }

    /**
     * Getter of target
     *
     * @return target as a Coordinate
     * @link ActionCoord.target
     */
    public Coordinate getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "ActionCoord {" +
                "source =" + source +
                " ; target=" + target +
                '}';
    }


    /**
     * Invert the source and the target
     */
    public void invert() {
        final Coordinate tmp = getSource();
        this.source = target;
        this.target = tmp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ActionCoord that = (ActionCoord) o;
        return source.equals(that.getSource()) &&
                target.equals(that.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    /**
     * Clones an actionCoord
     *
     * @return a new actionCoord with the same source and dest
     */
    @Override
    public Object clone() {
        try {
            final ActionCoord res = (ActionCoord) super.clone();
            res.source = (Coordinate) source.clone();
            res.target = (Coordinate) target.clone();
            return res;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
