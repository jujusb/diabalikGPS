package diaballik.Players;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CurrentPlayerAdapter extends XmlAdapter<String, Player> {
    Player p;

    @Override
    public Player unmarshal(final String color) throws Exception {
        return p;
    }

    @Override
    public String marshal(final Player player) throws Exception {
        p = player;
        return player.getName()+"a la main" ;
    }
}
