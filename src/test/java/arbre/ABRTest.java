package arbre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ABRTest {
	
	@Test
	void testInsertionRecherche() {
		ABR<Integer> abr = new ABR<>();
		abr.add(10);
		abr.add(5);
		abr.add(15);
		abr.add(15);
		abr.add(1);

		assertTrue(abr.contains(10));
		assertTrue(abr.contains(5));
		assertTrue(abr.contains(15));
		assertTrue(!abr.contains(0));
	}

	@Test
	void testInsertionDoublons() {
		ABR<Integer> abr = new ABR<>();
		abr.add(10);
		abr.add(10);

		assertEquals(1, abr.size());
	}

	@Test
	void testremove(){

	}
	
	@Test
	void testToString() {
		ABR<Integer> abr = new ABR<>(Integer::compareTo);
		abr.add(30);
		abr.add(70);
		abr.add(2);
		abr.add(40);
		abr.add(60);
		abr.add(80);
		abr.add(35);
		abr.add(25);

		String txt = abr.toString();
		assertNotNull(txt);
		assertTrue(txt.contains("60"));
		assertTrue(txt.contains("2"));
		assertTrue(txt.contains("35"));
	}

}
