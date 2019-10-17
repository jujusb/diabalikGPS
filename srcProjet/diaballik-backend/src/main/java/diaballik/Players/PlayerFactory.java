package diaballik.Players;

import diaballik.Players.Algorithms.EAiType;

import java.util.Optional;
import java.util.Random;

public final class PlayerFactory {

    /**
     * Creates a human player with some caracteristics
     *
     * @param name  the name of the player
     * @param color the color of the player, white if true black otherwise
     * @return the new human player
     */
    public Player createHuman(final String name, final boolean color) {
        return new HumanPlayer(name, color);
    }


    /**
     * Creates an AI player with some caracteristics
     *
     * @param type  the type of the AI algorithm in String format
     * @param color the color if the player, white if true black otherwise
     * @param name  the name of the AI if it has one already defined
     * @return the new AI player
     */
    public Player createAi(final String type, final boolean color, final Optional<String> name) {
        final EAiType t = EAiType.valueOf(type);
        final boolean c = color;
        final String n;

        if (name.isEmpty()) {
            final int rndm = new Random().nextInt(10);
            switch (rndm) {
                case 0:
                    n = "GlaDos";
                    break;
                case 1:
                    n = "Bébert";
                    break;
                case 2:
                    n = "Tuco";
                    break;
                case 3:
                    n = "Toto";
                    break;
                case 4:
                    n = "Isaac";
                    break;
                case 5:
                    n = "Patrick";
                    break;
                case 6:
                    n = "Garry Kasparov";
                    break;
                case 7:
                    n = "Alex";
                    break;
                case 8:
                    n = "Maître des Ténèbres";
                    break;
                default:
                    n = "Bob";
            }
        } else {
            n = name.get();
        }
        return new AiPlayer(t, n, c);
    }
}
