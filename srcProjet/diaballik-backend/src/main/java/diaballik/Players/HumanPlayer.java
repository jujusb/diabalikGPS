package diaballik.players;

import diaballik.coordinates.ActionCoord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HumanPlayer extends Player {
    @XmlTransient
    ActionCoord currentAction;

    /**
     * Constructor of HumanPlayer
     *
     * @param n the name of the HumanPlayer
     * @param c the colour of the HumanPlayer
     */
    public HumanPlayer(final String n, final boolean c) {
        super(n, c);
    }

    public HumanPlayer() {
        super();
    }

    /**
     * set the action that the player will announce in the getMove
     *
     * @param currentAction
     */
    public void setCurrentAction(final ActionCoord currentAction) {
        this.currentAction = currentAction;
    }
}
