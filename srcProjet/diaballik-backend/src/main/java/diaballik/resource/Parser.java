package diaballik.resource;

import diaballik.Coordinates.Coordinate;

public final class Parser {

    private Parser() {
    }

    public static Coordinate parseCoordinate(final String x, final String y) {
        return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
    }
}
