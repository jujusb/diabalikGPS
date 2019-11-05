package diaballik.Players;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PlayerAdapter extends XmlAdapter<Boolean, Player> {
    Player p;

    @Override
    public Player unmarshal(final Boolean aBoolean) throws Exception {
        return p;
    }

    @Override
    public Boolean marshal(final Player player) throws Exception {
        p = player;
        return player.getColor();
    }
}
