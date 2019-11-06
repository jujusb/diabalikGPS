package diaballik.GameElements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Deque;

public class RedoableDequeAdaptor extends XmlAdapter<Deque, Deque> {
    Deque redoableDeque;

    @Override
    public Deque unmarshal(final Deque s) throws Exception {
        return redoableDeque;
    }

    @Override
    public Deque marshal(final Deque deque) throws Exception {
        redoableDeque = deque;
        return deque;
    }
}