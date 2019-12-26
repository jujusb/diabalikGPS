package diaballik.resource;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.gameElements.Pawn;
import diaballik.supervisors.Game;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.json.JSONArray;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {
    private Game game = new Game();

    public GameResource() {
        super();
    }

    /**
     * POST /game/newPvP/{name1}/{name2}/{colour1}
     * Creates a new Player vs Player game with the name of both players and the colour of the first one (the other will have the opposite)
     */
    @POST
    @Path("newPvP/{name1}/{name2}/{colour1}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGamePvP(@PathParam("name1") final String name1, @PathParam("colour1") final String colour1, @PathParam("name2") final String name2) {
        final Map<String, String> gameDescriptor = new HashMap<>();
        gameDescriptor.put("namePlayer1", name1);
        gameDescriptor.put("colourPlayer1", colour1);
        gameDescriptor.put("namePlayer2", name2);

        game.initializeGame(gameDescriptor);

        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * POST /game/newPvE/{name1}/{colour1}/{level}
     * Creates a new Player vs Entity game with the name and colour of the first player (the name of the second will be random and its colour the opposite) and the AI level
     */
    @POST
    @Path("newPvE/{name1}/{colour1}/{level}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGamePvE(@PathParam("name1") final String name1, @PathParam("colour1") final String colour1, @PathParam("level") final String level) {
        final Map<String, String> gameDescriptor = new HashMap<>();
        gameDescriptor.put("namePlayer1", name1);
        gameDescriptor.put("aiLevel", level);
        gameDescriptor.put("colourPlayer1", colour1);
        game.initializeGame(gameDescriptor);

        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * POST /game/newPvE/{name1}/{name2}/{colour1}/{level}
     * Creates a new Player vs Entity game with the name of both players and the colour of the first one (the other will have the opposite) and the AI level
     */
    @POST
    @Path("newPvE/{name1}/{name2}/{colour1}/{level}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGamePvEWithName(@PathParam("name1") final String name1, @PathParam("colour1") final String colour1, @PathParam("name2") final String name2, @PathParam("level") final String level) {
        final Map<String, String> gameDescriptor = new HashMap<>();
        gameDescriptor.put("aiLevel", level);
        gameDescriptor.put("namePlayer1", name1);
        gameDescriptor.put("colourPlayer1", colour1);
        gameDescriptor.put("namePlayer2", name2);

        game.initializeGame(gameDescriptor);

        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * POST /game/action/move/{x1}/{y1}/{x2}/{y2}
     * Sends a move proposal to the game so that it checks if it is legal or not and, if so, it will play it
     */
    @POST
    @Path("action/move/{x1}/{y1}/{x2}/{y2}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response move(@PathParam("x1") final String x1, @PathParam("y1") final String y1, @PathParam("x2") final String x2, @PathParam("y2") final String y2) {
        final ActionCoord move = new ActionCoord(
                Parser.parseCoordinate(x1, y1),
                Parser.parseCoordinate(x2, y2));
        final boolean moveOk = game.moveOfPlayer(move);
        // tells the player to try this action and let it tell the game
        if (moveOk) {
            return Response.status(Response.Status.OK).entity(game).build();
        } else {
            // code to say the move was wrong
            return Response.status(Response.Status.BAD_REQUEST).entity(game).build();
        }
    }

    /**
     * POST /game/endOfTurn
     * Notifies of the end of the turn of the current playing player
     */
    @POST
    @Path("endOfTurn")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endOfTurn() {
        game.endOfTurn();
        // free the player so that it can tells
        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * PUT /game/action/undo
     * Sends an undo request
     */
    @PUT
    @Path("action/undo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response undo() {
        game.undo();
        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * PUT /game/action/redo
     * Sends a redo request
     */
    @PUT
    @Path("action/redo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redo() {
        game.redo();
        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * PUT /game/kill
     * Kills the game
     */
    @PUT
    @Path("/kill")
    @Produces(MediaType.APPLICATION_JSON)
    public Response kill() {
        game = new Game();
        return Response.status(Response.Status.OK).entity(game).build();
    }

    /**
     * GET /game/getPossibleMovesFrom/{x}/{y}
     * Return a list of the possible moves for the pawn at x,y
     */
    @GET
    @Path("/getPossibleMovesFrom/{x}/{y}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoves(@PathParam("x") final String x, @PathParam("y") final String y) {
        final Pawn p = game.getGameBoard().getPawn(new Coordinate(Integer.valueOf(x), Integer.valueOf(y))).get();
        final List<ActionCoord> list = game.getGameBoard().getPossiblePawnMoves(p);
        list.addAll(game.getGameBoard().getPossibleBallMoves(p));
        ActionCoord[] array = new ActionCoord[list.size()];
        array = list.toArray(array);
        return Response.status(Response.Status.OK).entity(array).build();
    }
}
