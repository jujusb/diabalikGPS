package diaballik.model;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestMarshalling {
	<T> T marshall(final T objectToMarshall) throws IOException, JAXBException {
		final Map<String, Object> properties = new HashMap<>();
		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);

		final JAXBContext ctx = JAXBContextFactory.createContext(new Class[]{objectToMarshall.getClass()}, properties);
		final Marshaller marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		final StringWriter sw = new StringWriter();
		marshaller.marshal(objectToMarshall, sw);
		marshaller.marshal(objectToMarshall, System.out);

		final Unmarshaller unmarshaller = ctx.createUnmarshaller();
		final StringReader reader = new StringReader(sw.toString());
		final T o = (T) unmarshaller.unmarshal(reader);

		sw.close();
		reader.close();

		return o;
	}

	// Player p1;

	@BeforeEach
	void setUp() {
		// p1 = new Player("foo", "red");
	}

	@Test
	void testPlayer() throws IOException, JAXBException {
		// final Player p = marshall(p1);
		// assertEquals("foo", p.getName());
		// assertEquals("red", p.getColor());
	}
}
