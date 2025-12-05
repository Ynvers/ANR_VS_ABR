package arbre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class ANRTest {
	@Test
	void testInsertionRecherche() {
		ANR<Integer> abr = new ANR<>();
		abr.add(10);
		abr.add(5);
		abr.add(15);
		abr.add(1);

		assertTrue(abr.contains(10));
		assertTrue(abr.contains(5));
		assertTrue(abr.contains(15));
		assertTrue(!abr.contains(0));
	}

	@Test
	void testremove(){
		ANR<Integer> aNr = new ANR<>();

		//cas 1 : suppresion d'un arbre vide
		assertFalse(aNr.remove(10), "L'arbre est vide");

		//cas 2 : suppression de la racine
		
		// 2.a racine sans enfant)
		aNr.add(10);
		assertTrue(aNr.remove(10));
		assertEquals(0, aNr.size());

		// 2.b Racine avec enfant droit uniquement 
		aNr.add(10);
		aNr.add(20); 
		assertTrue(aNr.remove(10));
		assertTrue(aNr.contains(20));
		assertEquals(1, aNr.size());
		
		// 2.c Racine avec enfant gauche uniquement
		aNr = new ANR<>(); // Reset
		aNr.add(10);
		aNr.add(5);
		assertTrue(aNr.remove(10));
		assertTrue(aNr.contains(5));

		// cas 3 : Noeuds internes
		aNr = new ANR<>(50);
		aNr.add(30);
		aNr.add(70);
		aNr.add(20);
		aNr.add(40);
		aNr.add(80);

		// 3.a Suppression élément inexistant dans un arbre non vide
		assertFalse(aNr.remove(99));

		// 3.b Suppression feuille enfant gauche
		assertTrue(aNr.remove(20));
		assertFalse(aNr.contains(20));

		// 3.c Suppression feuille enfant droit
		assertTrue(aNr.remove(80)); 
		assertFalse(aNr.contains(80));

		// 3.d Suppression noeud interne avec 1 enfant 
		aNr.add(60); 
		assertTrue(aNr.remove(70)); 
		assertTrue(aNr.contains(60));

		// 3.e Suppression noeud avec 2 enfants (racine)
		assertTrue(aNr.remove(50));
		assertFalse(aNr.contains(50));
		assertTrue(aNr.contains(30));
		assertEquals(60, aNr.getracine());;
	}

	@Test
	void testget_taille() {
		ANR<Integer> anr = new ANR<>();
		
		// Arbre vide
		assertEquals(0, anr.get_taille());
		
		// Après ajouts
		anr.add(20);
		assertEquals(1, anr.get_taille());
		
		anr.add(10);
		assertEquals(2, anr.get_taille());
		
		anr.add(30);
		assertEquals(3, anr.get_taille());
		
		anr.add(5);
		anr.add(15);
		anr.add(25);
		anr.add(35);
		assertEquals(7, anr.get_taille());
		
		// Après suppressions
		anr.remove(5);
		assertEquals(6, anr.get_taille());
		
		anr.remove(35);
		assertEquals(5, anr.get_taille());
		
		anr.remove(20);
		assertEquals(4, anr.get_taille());
	}

	@Test
	void testAjouterCorrection() {
		ANR<Integer> anr = new ANR<>();
		
		// Cas 1: Insertions simples avec recoloration
		anr.add(50);
		anr.add(25);
		anr.add(75);
		anr.add(10);
		anr.add(30);
		anr.add(60);
		anr.add(80);
		assertEquals(7, anr.get_taille());
		
		// Cas 2: Insertions qui déclenchent des rotations (oncle noir)
		anr.add(5);
		anr.add(15);
		anr.add(27);
		anr.add(35);
		assertEquals(11, anr.get_taille());
		
		// Vérifier que tous les éléments sont présents et accessibles
		assertTrue(anr.contains(50));
		assertTrue(anr.contains(25));
		assertTrue(anr.contains(75));
		assertTrue(anr.contains(5));
		assertTrue(anr.contains(35));
		
		// Vérifier que la hauteur noire est cohérente
		int blackHeight = anr.blackHeight();
		assertTrue(blackHeight > 0);
	}

	@Test
	void	testsupprimerCorrection(){
		ANR<Integer> anr = new ANR<>();
		
		// cas 1 : le frere est rouge : 
		anr.add(7);
		anr.add(5);
		anr.add(15);
		anr.add(1);
		anr.add(6);
		anr.add(0);
		anr.add(50);
		anr.remove(15);
	}

	@Test
	void testmax(){
		// cas normal
		ANR<Integer> aNr = new ANR<>();
		aNr.add(7);
		aNr.add(5);
		aNr.add(15);
		aNr.add(1);
		assertEquals(15, aNr.max());

		// Cas arbre vide
		ANR<Integer> aNr2 = new ANR<>();
		assertEquals(null, aNr2.max());
	}

	

	@Test
	void testmin(){
		// cas normal
		ANR<Integer> abr = new ANR<>();
		abr.add(7);
		abr.add(5);
		abr.add(15);
		abr.add(1);
		assertEquals(1, abr.min());

		// Cas arbre vide
		ANR<Integer> abr2 = new ANR<>();
		assertEquals(null, abr2.min());
	}

	@Test
	void testRotationGauche() {
		ANR<Integer> anr = new ANR<>();
		
		// cas 1: Insertion en ordre croissant pour forcer déséquilibre et rotations gauche
		anr.add(10);
		anr.add(20);
		anr.add(30);
		anr.add(40);
		anr.add(50);
		
		// Cas 2: Rotation gauche avec enfant intermédiaire
		anr = new ANR<>();
		anr.add(10);
		anr.add(30);
		anr.add(20);
		anr.add(35);
		anr.add(25);
		assertEquals(5, anr.get_taille());
		
		for (int val : new int[]{10, 20, 25, 30, 35}) {
			assertTrue(anr.contains(val), "L'arbre doit contenir " + val);
		}
		
		Iterator<Integer> iterator = anr.iterator();
		Integer prev = null;
		while (iterator.hasNext()) {
			Integer curr = iterator.next();
			if (prev != null) {
				assertTrue(prev < curr, "Ordre en-ordre doit être préservé après rotation");
			}
			prev = curr;
		}
	}

	@Test
	void testRotationDroite() {
		ANR<Integer> anr = new ANR<>();
		
		// Cas 1: Insertion en ordre décroissant pour forcer déséquilibre et rotations droite
		anr.add(50);
		anr.add(40);
		anr.add(30);
		anr.add(20);
		anr.add(10);

		// Cas 2: Rotation droite avec enfant intermédiaire
		anr = new ANR<>();
		anr.add(50);
		anr.add(30);
		anr.add(40);
		anr.add(25);
		anr.add(35);
		assertEquals(5, anr.get_taille());
		
		for (int val : new int[]{35, 25, 30, 40, 50}) {
			assertTrue(anr.contains(val), "L'arbre doit contenir " + val);
		}
		
		Iterator<Integer> iterator = anr.iterator();
		Integer prev = null;
		while (iterator.hasNext()) {
			Integer curr = iterator.next();
			if (prev != null) {
				assertTrue(prev < curr, "Ordre en-ordre doit être préservé après rotation");
			}
			prev = curr;
		}
	}

	@Test
	void	testiterator()
	{
		ANR<Integer> aNr = new ANR<>();
		Iterator<Integer> it;
	
		// cas 1 : arbre vide
		it = aNr.iterator();
		assertFalse(it.hasNext());
		assertNull(it.next());

		// cas 2 : aNre simple
		aNr.add(50);
		aNr.add(25);
		aNr.add(75);
		aNr.add(10);
		aNr.add(90);
		aNr.add(30);
		it = aNr.iterator();
		assertTrue(it.hasNext());
		assertEquals(10, it.next());
		assertEquals(25, it.next());
		assertEquals(30, it.next());
		assertEquals(50, it.next());
		assertEquals(75, it.next());
		assertEquals(90, it.next());
		assertFalse(it.hasNext());
		assertNull(it.next());

		// cas 3 : test de remove
		aNr = new ANR<>();
		aNr.add(20);
		aNr.add(10);
		aNr.add(30);
		it = aNr.iterator();

		// 3.a : remove avant next
		it.remove();
		assertEquals(3, aNr.size());

		// 3.b : remove accepte
		assertEquals(10, it.next());
		it.remove();
		assertEquals(2, aNr.size());
		assertFalse(aNr.contains(10));

		// 3.c : double appel;
		it.remove(); 
		assertEquals(2, aNr.size());
	}

	@Test
	void testToString() {
		ANR<Integer> abr = new ANR<>(Integer::compareTo);
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
