package diaballik.GameElements;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.Players.HumanPlayer;
import diaballik.Players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard board;
    Player p1 = new HumanPlayer("Toto", false);
    Player p2 = new HumanPlayer("Tata", true);

    @BeforeEach
    void init() {
        board = new GameBoard(p1, p2);
    }

    @Test
    void testCheckCoord() {
        assertTrue(board.checkCoord(new Coordinate(0,0)));
        assertTrue(board.checkCoord(new Coordinate(6,6)));
        assertTrue(board.checkCoord(new Coordinate(3,5)));
        assertFalse(board.checkCoord(new Coordinate(-1,5)));
        assertFalse(board.checkCoord(new Coordinate(7,5)));
        assertFalse(board.checkCoord(new Coordinate(2,-1)));
        assertFalse(board.checkCoord(new Coordinate(2,7)));
        assertFalse(board.checkCoord(new Coordinate(7,7)));
        assertFalse(board.checkCoord(new Coordinate(-1,-1)));
    }

    @Test
    void testGetPawn() {
        assertEquals(board.getHumanPlayer().getPawns().get(0), board.getPawn(board.getHumanPlayer().getPawns().get(0).getPosition()).get());
        assertEquals(p2.getPawns().get(0), board.getPawn(p2.getPawns().get(0).getPosition()).get());

        assertEquals(board.getPawn(new Coordinate(3,3)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(-1,3)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(7,3)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(3,-1)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(3,7)), Optional.empty());

    }

    @Test
    void testConstructor() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner()); //check the ball for player 1
        assertTrue(board.getPawn(new Coordinate(3, 6)).get().isBallOwner()); //check the ball for player 2
        assertFalse(board.getPawn(new Coordinate(2, 6)).get().isBallOwner()); //check if another pawn has not the ball

        assertTrue(board.getPawn( new Coordinate(0,0)).isPresent());    //check all the pawns of player1
        assertTrue(board.getPawn( new Coordinate(1,0)).isPresent());
        assertTrue(board.getPawn( new Coordinate(2,0)).isPresent());
        assertTrue(board.getPawn( new Coordinate(3,0)).isPresent());
        assertTrue(board.getPawn( new Coordinate(4,0)).isPresent());
        assertTrue(board.getPawn( new Coordinate(5,0)).isPresent());
        assertTrue(board.getPawn( new Coordinate(6,0)).isPresent());

        assertTrue(board.getPawn( new Coordinate(0,6)).isPresent());    //check all the pawns of player 2
        assertTrue(board.getPawn( new Coordinate(1,6)).isPresent());
        assertTrue(board.getPawn( new Coordinate(2,6)).isPresent());
        assertTrue(board.getPawn( new Coordinate(3,6)).isPresent());
        assertTrue(board.getPawn( new Coordinate(4,6)).isPresent());
        assertTrue(board.getPawn( new Coordinate(5,6)).isPresent());
        assertTrue(board.getPawn( new Coordinate(6,6)).isPresent());

        assertTrue(board.getPawn(new Coordinate(3, 3)).isEmpty()); // check an empty position
        assertTrue(board.getPawn(new Coordinate(7,0)).isEmpty()); // check an invalid position
    }

    @Test
    void testCanMove() {
        assertTrue(board.canMove(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.canMove(p2, new ActionCoord(new Coordinate(0,6), new Coordinate(0,5))));
    }

    @Test
    void testCannotMove() {
        assertFalse(board.canMove(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(1, 1))));
        assertFalse(board.canMove(p2, new ActionCoord(new Coordinate(2,6),new Coordinate(3,6))));
    }

    @Test
    void testCanMoveBall() {
        assertTrue(board.canMoveBall(board.getPawn(new Coordinate(3,6)).get(),board.getPawn(new Coordinate(4,6)).get()));
        assertTrue(board.canMoveBall(board.getPawn(new Coordinate(3,0)).get(),board.getPawn(new Coordinate(4,0)).get()));
    }

    @Test
    void testCannotMoveBall() {
        //one pawn between
        assertFalse(board.canMoveBall(board.getPawn(new Coordinate(3,0)).get(),board.getPawn(new Coordinate(1,0)).get()));
        assertFalse(board.canMoveBall(board.getPawn(new Coordinate(3,6)).get(), board.getPawn(new Coordinate(1,6)).get()));

        //is not a valid ball move
        assertFalse(board.canMoveBall(board.getPawn(new Coordinate(3,0)).get(),board.getPawn(new Coordinate(1,6)).get()));
        assertFalse(board.canMoveBall(board.getPawn(new Coordinate(3,6)).get(),board.getPawn(new Coordinate(1,0)).get()));

    }

    @Test
    void testMovePawn() {
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
    }

    @Test
    void testMovePawnFalse() {
        assertFalse(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(1, 1))));
    }

    @Test
    void testMovePawnOutOfBound() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(-1, 0)));
        });
    }

    @Test
    void testMoveBallHorizontal() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 0))));
        assertEquals(board.getPawn(new Coordinate(2, 0)).get(), board.getHumanPlayer().getBall());
        assertTrue(board.getPawn(new Coordinate(2, 0)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void testMoveBallVertical() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 0))));
        assertEquals(board.getPawn(new Coordinate(2, 0)).get(), board.getHumanPlayer().getBall());
        assertTrue(board.getPawn(new Coordinate(2, 0)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void testMoveBallDiagonal() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(2, 0), new Coordinate(2, 1))));
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 1))));
        assertTrue(board.getPawn(new Coordinate(2, 1)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void testMoveBallFalse() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
        assertFalse(board.move(p1,new ActionCoord( p1.getBall().getPosition(), p2.getBall().getPosition())));
        assertFalse(board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 1))));
    }


    @Test
    void testUndo() {
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
    }

    @Test
    void testRedo() {
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        board.redo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
    }

    @Test
    void testUndoAfterRedo() {
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1))));
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        board.redo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
    }

    @Test
    void testCheckIfWonP1() {
        assertFalse(board.checkIfWon());
        //Mouvement du pion du joueur 2 de (0,6) en (1,5).
        board.move(p2, new ActionCoord(new Coordinate(0, 6), new Coordinate(0, 5)));
        board.move(p2, new ActionCoord(new Coordinate(0, 5), new Coordinate(1, 5)));
        //Avancement du pion du joueur 1 de (0,0) en (0,6)
        board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)));
        board.move(p1, new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 2)));
        board.move(p1, new ActionCoord(new Coordinate(0, 2), new Coordinate(0, 3)));
        board.move(p1, new ActionCoord(new Coordinate(0, 3), new Coordinate(0, 4)));
        board.move(p1, new ActionCoord(new Coordinate(0, 4), new Coordinate(0, 5)));
        board.move(p1, new ActionCoord(new Coordinate(0, 5), new Coordinate(0, 6)));
        //Avancement du point du joueur 1 de (1,0) en (0,0)
        board.move(p1, new ActionCoord(new Coordinate(1, 0), new Coordinate(0, 0)));
        //Avancement du point du joueur 1 de (2,0) en (2,1)
        board.move(p1, new ActionCoord(new Coordinate(2, 0), new Coordinate(2, 1)));
        //Déplacement de la balle de (3,0) en (0,0), puis de (0,0) en (0,6).
        board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(0, 0)));
        board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 6)));
        assertTrue(board.checkIfWon());
    }

    @Test
    void testCheckIfWonP2() {
        assertFalse(board.checkIfWon());
        //Mouvement du pion du joueur 1 de (0,1) en (1,1).
        board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)));
        board.move(p1, new ActionCoord(new Coordinate(0, 1), new Coordinate(1, 1)));
        //Avancement du pion du joueur 2 de (0,6) en (0,0)
        board.move(p2, new ActionCoord(new Coordinate(0, 6), new Coordinate(0, 5)));
        board.move(p2, new ActionCoord(new Coordinate(0, 5), new Coordinate(0, 4)));
        board.move(p2, new ActionCoord(new Coordinate(0, 4), new Coordinate(0, 3)));
        board.move(p2, new ActionCoord(new Coordinate(0, 3), new Coordinate(0, 2)));
        board.move(p2, new ActionCoord(new Coordinate(0, 2), new Coordinate(0, 1)));
        board.move(p2, new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 0)));
        //Avancement du point du joueur 2 de (1,6) en (0,6)
        board.move(p2, new ActionCoord(new Coordinate(1, 6), new Coordinate(0, 6)));
        //Avancement du point du joueur 2 de (2,6) en (2,5)
        board.move(p2, new ActionCoord(new Coordinate(2, 6), new Coordinate(2, 5)));
        //Déplacement de la balle de (3,6) en (0,6), puis de (0,6) en (0,0).
        board.move(p2, new ActionCoord(new Coordinate(3, 6), new Coordinate(0, 6)));
        board.move(p2, new ActionCoord(new Coordinate(0, 6), new Coordinate(0, 0)));
        assertTrue(board.checkIfWon());
    }

}
