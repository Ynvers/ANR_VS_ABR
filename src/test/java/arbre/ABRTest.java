package arbre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
		ABR<Integer> abr = new ABR<>();
		abr.add(10);
		abr.add(7);
		abr.add(5);
		abr.add(15);
		abr.add(1);

		// suppression feuille
		assertTrue(abr.remove(1));
		assertFalse(abr.contains(1));
		assertEquals(4, abr.size());

		// suppression noeud avec un enfant (racine)
		assertTrue(abr.remove(7));
		assertFalse(abr.contains(7));
		assertEquals(3, abr.size());

		// suppression noeud avec deux enfants (racine)
		assertTrue(abr.remove(10));
		assertFalse(abr.contains(10));
		assertEquals(2, abr.size());
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
