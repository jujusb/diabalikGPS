package diaballik.gameElements;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.players.HumanPlayer;
import diaballik.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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
    void testConstructor() {
        assertTrue(board.getPawn(new Coordinate(3, 0)).get().isBallOwner()); //check the ball for player 1
        assertTrue(board.getPawn(new Coordinate(3, 6)).get().isBallOwner()); //check the ball for player 2
        assertFalse(board.getPawn(new Coordinate(2, 6)).get().isBallOwner()); //check if another pawn does not have the ball

        System.out.println(board);

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

        System.out.println(board.playerPawnCoordinates());

        assertTrue(board.getPawn(new Coordinate(3, 3)).isEmpty()); // check an empty position
    }

    @Test
    void moveAndCheck() {
        final Coordinate source = new Coordinate(1, 0);
        final Coordinate dest = new Coordinate(1, 0);

        List<ActionCoord> list = new ArrayList<>();

        // fails because there is a pawn on (2,0)
        board.moveAndCheck(1, 0, list, dest, source);
        assertTrue(list.isEmpty());

        // succeeds because there is no pawn on (1,1)
        board.moveAndCheck(-1, 1, list, dest, source);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(new ActionCoord(new Coordinate(1, 0), new Coordinate(1, 1)), list.get(0));

        // fails because (9,1) is not valid as it is out of bounds
        list.clear();
        board.moveAndCheck(8, 0, list, dest, source);
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetPawn() {
        assertEquals(board.getPlayer1().getPawns().get(0), board.getPawn(board.getPlayer1().getPawns().get(0).getPosition()).get());
        assertEquals(p2.getPawns().get(0), board.getPawn(p2.getPawns().get(0).getPosition()).get());

        assertEquals(board.getPawn(new Coordinate(3,3)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(-1,3)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(7,3)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(3,-1)), Optional.empty());
        assertEquals(board.getPawn(new Coordinate(3,7)), Optional.empty());

    }

    @Test
    void testGetHumanPlayer(){
        assertEquals(p1,board.getPlayer1());
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
        assertEquals(1,board.getUndoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)),board.getUndoable_mode().pop());
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
        board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 0)));
        assertEquals(board.getPawn(new Coordinate(2, 0)).get(), board.getPlayer1().getBall());
        assertTrue(board.getPawn(new Coordinate(2, 0)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void testMoveBallVertical() {
        board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 0)));
        assertEquals(board.getPawn(new Coordinate(2, 0)).get(), board.getPlayer1().getBall());
        assertTrue(board.getPawn(new Coordinate(2, 0)).get().isBallOwner());
        assertFalse(board.getPawn(new Coordinate(3, 0)).get().isBallOwner());
    }

    @Test
    void testMoveBallDiagonal() {
        board.move(p1, new ActionCoord(new Coordinate(2, 0), new Coordinate(2, 1)));
        board.move(p1, new ActionCoord(new Coordinate(3, 0), new Coordinate(2, 1)));
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
        board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)));
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertEquals(0,board.getUndoable_mode().size());
        assertEquals(1,board.getRedoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 0)),board.getRedoable_mode().pop());
    }

    @Test
    void testRedo() {
        board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)));
        board.undo();
        board.redo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isEmpty());
        assertEquals(0,board.getRedoable_mode().size());
        assertEquals(1,board.getUndoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)), board.getUndoable_mode().pop());
    }

    @Test
    void testUndoAfterRedo() {
        board.move(p1, new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)));
        board.undo();
        board.redo();
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(board.getPawn(new Coordinate(0, 0)).isPresent());
        assertEquals(0,board.getUndoable_mode().size());
        assertEquals(1,board.getRedoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 0)), board.getRedoable_mode().pop());
    }
    @Test
    void testThreeUndo(){
        board.move(p1,new ActionCoord(new Coordinate(0,0),new Coordinate(0,1)));
        board.move(p1,new ActionCoord(new Coordinate(0,1),new Coordinate(0,2)));
        board.move(p1,new ActionCoord(new Coordinate(0,2),new Coordinate(0,3)));
        board.undo();
        board.undo();
        board.undo();
        assertTrue(board.getPawn(new Coordinate(0,0)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0,3)).isEmpty());
        assertEquals(0,board.getUndoable_mode().size());
        assertEquals(3,board.getRedoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0,1),new Coordinate(0,0)),board.getRedoable_mode().pop());
        assertEquals(new ActionCoord(new Coordinate(0,2),new Coordinate(0,1)),board.getRedoable_mode().pop());
        assertEquals(new ActionCoord(new Coordinate(0,3),new Coordinate(0,2)),board.getRedoable_mode().pop());
    }

    @Test
    void testThreeRedo(){
        board.move(p1,new ActionCoord(new Coordinate(0,0),new Coordinate(0,1)));
        board.move(p1,new ActionCoord(new Coordinate(0,1),new Coordinate(0,2)));
        board.move(p1,new ActionCoord(new Coordinate(0,2),new Coordinate(0,3)));
        board.undo();
        board.undo();
        board.undo();
        board.redo();
        board.redo();
        board.redo();
        System.out.println(board.playerPawnCoordinates());
        assertTrue(board.getPawn(new Coordinate(0,3)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0,0)).isEmpty());
        assertEquals(0,board.getRedoable_mode().size());
        assertEquals(3,board.getUndoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0,2),new Coordinate(0,3)),board.getUndoable_mode().pop());
        assertEquals(new ActionCoord(new Coordinate(0,1),new Coordinate(0,2)),board.getUndoable_mode().pop());
        assertEquals(new ActionCoord(new Coordinate(0,0),new Coordinate(0,1)),board.getUndoable_mode().pop());
    }

    @Test
    void testUndoRedoMove(){
        board.move(p1,new ActionCoord(new Coordinate(0,0),new Coordinate(0,1)));
        board.move(p1,new ActionCoord(new Coordinate(0,1),new Coordinate(0,2)));
        board.undo();
        board.redo();
        System.out.println(board.playerPawnCoordinates());
        assertTrue(board.getPawn(new Coordinate(0,2)).isPresent());
        assertTrue(board.getPawn(new Coordinate(0,1)).isEmpty());
        assertEquals(0,board.getRedoable_mode().size());
        assertEquals(2,board.getUndoable_mode().size());
        assertEquals(new ActionCoord(new Coordinate(0,1),new Coordinate(0,2)),board.getUndoable_mode().pop());
        assertEquals(new ActionCoord(new Coordinate(0,0),new Coordinate(0,1)),board.getUndoable_mode().pop());
    }


    @Test
    void testCheckIfWonP1() {
        assertFalse(board.checkIfWon());
        assertFalse(p1.isWinner());
        assertFalse(p2.isWinner());
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
        assertTrue(p1.isWinner());
        assertFalse(p2.isWinner());
    }

    @Test
    void testCheckIfWonP2() {
        assertFalse(board.checkIfWon());
        assertFalse(p1.isWinner());
        assertFalse(p2.isWinner());
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
        assertFalse(p1.isWinner());
        assertTrue(p2.isWinner());
    }

}
