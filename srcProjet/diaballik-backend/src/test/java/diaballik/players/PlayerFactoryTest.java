package diaballik.players;

import diaballik.players.algorithms.EAiType;
import diaballik.players.algorithms.NoobAlgo;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerFactoryTest {
    PlayerFactory factory = new PlayerFactory();

    @Test
    void testCreateHuman() {
        HumanPlayer p = new HumanPlayer("Toto",true);
        assertEquals(p,factory.createHuman("Toto",true));
    }

    @Test
    void testCreateAiWithName() {
        AiPlayer p = new AiPlayer(EAiType.NOOB,"Tata",true);
        AiPlayer p2 = (AiPlayer) factory.createAi("NOOB", true, Optional.ofNullable("Tata"));
        assertEquals(p,p2);
        assertEquals(NoobAlgo.class, p2.getAlgo().getClass());
    }

    @Test
    void testCreateAiWithoutName() {
        AiPlayer p2 = (AiPlayer) factory.createAi("STARTING", true, Optional.ofNullable(null));
        assertTrue(p2.getName().matches("GlaDos|Bébert|Tuco|Toto|Isaac|Patrick|Garry Kasparov|Alex|Maître des Ténèbres|Bob"));
    }
}