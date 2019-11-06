package diaballik.GameElements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Deque;

public class UndoableDequeAdaptor extends XmlAdapter<Deque, Deque> {
    Deque undoableDeque;

    @Override
    public Deque unmarshal(final Deque s) throws Exception {
        return undoableDeque;
    }

    @Override
    public Deque marshal(final Deque deque) throws Exception {
        undoableDeque = deque;
        return deque;
    }
}
