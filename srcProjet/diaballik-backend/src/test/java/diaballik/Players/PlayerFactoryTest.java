package diaballik.Players;

import diaballik.Players.Algorithms.EAiType;
import diaballik.Players.Algorithms.NoobAlgo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnit;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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