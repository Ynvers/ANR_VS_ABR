package arbre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

		//cas 1 : suppresion d'un arbre vide
		assertFalse(abr.remove(10), "L'arbre est vide");

		//cas 2 : suppression de la racine
		
		// 2.a racine sans enfant)
		abr.add(10);
		assertTrue(abr.remove(10));
		assertEquals(0, abr.size());

		// 2.b Racine avec enfant droit uniquement 
		abr.add(10);
		abr.add(20); 
		assertTrue(abr.remove(10));
		assertTrue(abr.contains(20));
		assertEquals(1, abr.size());
		
		// 2.c Racine avec enfant gauche uniquement
		abr = new ABR<>(); // Reset
		abr.add(10);
		abr.add(5);
		assertTrue(abr.remove(10));
		assertTrue(abr.contains(5));

		// cas 3 : Noeuds internes
		abr = new ABR<>(50);
		abr.add(30);
		abr.add(70);
		abr.add(20);
		abr.add(40);
		abr.add(80);

		// 3.a Suppression élément inexistant dans un arbre non vide
		assertFalse(abr.remove(99));

		// 3.b Suppression feuille enfant gauche
		assertTrue(abr.remove(20));
		assertFalse(abr.contains(20));

		// 3.c Suppression feuille enfant droit
		assertTrue(abr.remove(80)); 
		assertFalse(abr.contains(80));

		// 3.d Suppression noeud interne avec 1 enfant 
		abr.add(60); 
		assertTrue(abr.remove(70)); 
		assertTrue(abr.contains(60));

		// 3.e Suppression noeud avec 2 enfants (racine)
		assertTrue(abr.remove(50));
		assertFalse(abr.contains(50));
		assertTrue(abr.contains(30));
		assertEquals(60, abr.getracine());;
		}
	
	@Test
	void testmax(){
		// cas normal
		ABR<Integer> abr = new ABR<>();
		abr.add(7);
		abr.add(5);
		abr.add(15);
		abr.add(1);
		assertEquals(15, abr.max());

		// Cas arbre vide
		ABR<Integer> abr2 = new ABR<>();
		assertEquals(null, abr2.max());
	}

	@Test
	void testmin(){
		// cas normal
		ABR<Integer> abr = new ABR<>();
		abr.add(7);
		abr.add(5);
		abr.add(15);
		abr.add(1);
		assertEquals(1, abr.min());

		// Cas arbre vide
		ABR<Integer> abr2 = new ABR<>();
		assertEquals(null, abr2.min());
	}

	@Test
	void testhauteur(){
		ABR<Integer> abr = new ABR<>();

		// cas 1 : arbre vide 
		assertEquals(0, abr.hauteur());
		
		// cas 2 : racine seule
		abr.add(10);
		assertEquals(1, abr.hauteur());
		
		// cas 3 : noeud interne
		// 3.a : arbez un pzu equilibree
		abr = new ABR<>();
    	abr.add(7);
		abr.add(5);
		abr.add(15);
		abr.add(1);
		assertEquals(3, abr.hauteur());

		// 3.b : arbre desequilibre
		abr = new ABR<>();
		abr.add(1);
		abr.add(2);
		abr.add(3);
		assertEquals(3, abr.hauteur());
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
