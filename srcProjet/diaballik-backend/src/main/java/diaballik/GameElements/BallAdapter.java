package diaballik.gameElements;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BallAdapter extends XmlAdapter<Pawn, Pawn> {
    Pawn b;

    @Override
    public Pawn unmarshal(final Pawn pawn) throws Exception {
        return b;
    }

    @Override
    public Pawn marshal(final Pawn ball) throws Exception {
        b = ball;
        return null;
    }
}
