package diaballik.Players;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PlayerAdapter extends XmlAdapter<String, Player> {
    Player p;

    @Override
    public Player unmarshal(final String color) throws Exception {
        return p;
    }

    @Override
    public String marshal(final Player player) throws Exception {
        p = player;
        return player.getColor() ? "white" : "black";
    }
}
