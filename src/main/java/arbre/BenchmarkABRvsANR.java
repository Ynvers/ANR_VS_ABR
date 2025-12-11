package arbre;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BenchmarkABRvsANR {
	private static final int[] TAILLES = {1000, 2500, 5000, 7500, 10000, 15000, 20000, 50000, 100000};

	public static void	main(String[] args) throws IOException{
		System.out.println("Début de l'étude expérimentale...");

		try (FileWriter resulsts = new FileWriter("resultats_arbres.csv")){
			resulsts.write("N,ABR_Const_Rand,ARN_Const_Rand,ABR_Search_Rand,ARN_Search_Rand,ABR_Const_Sorted,ARN_Const_Sorted,ABR_Search_Sorted,ARN_Search_Sorted\n");

			for (int n : TAILLES){
				System.out.println("Traitement pour n = " + n + "...");

				List<Integer> donneesTriees = new ArrayList<>(n);
				for (int i = 0; i < n; i++)
					donneesTriees.add(i);

				List<Integer> donneesAleatoires = new ArrayList<>(donneesTriees);
				Collections.shuffle(donneesAleatoires, new Random(42));

				// Cas 1 : Les insertions aléatoires

				// ABR Aléatoire
				ABR<Integer> abrAleatoire = new ABR<>();
				long tempsDebut = System.nanoTime();
				for (Integer val : donneesAleatoires)
					abrAleatoire.add(val);
				long abrtempsDeConstructionAl = System.nanoTime() - tempsDebut;

				// ARN Aléatoire
				ANR<Integer> anrAleatoire = new ANR<>();
				tempsDebut = System.nanoTime();
				for (Integer val : donneesAleatoires)
					anrAleatoire.add(val);
				long anrtempsDeConstructionAl = System.nanoTime() - tempsDebut;

				// Recherche Aléatoire (0 à 2n-1)
				tempsDebut = System.nanoTime();
				for (int i = 0; i < 2 * n; i++)
					abrAleatoire.contains(i);
				long abrtempsRechercheAl = System.nanoTime() - tempsDebut;

				tempsDebut = System.nanoTime();
				for (int i = 0; i < 2 * n; i++)
					anrAleatoire.contains(i);
				long anrtempsRechercheAl = System.nanoTime() - tempsDebut;

				// // Cas 2 : Les insertions dans l'ordre
				// ABR Ordre
				ABR<Integer> abrOrdre = new ABR<>();
				tempsDebut = System.nanoTime();
				for (Integer val : donneesTriees)
					abrOrdre.add(val);
				long abrtempsDeConstructionOd = System.nanoTime() - tempsDebut;

				// ARN Ordre
				ANR<Integer> anrOrdre = new ANR<>();
				tempsDebut = System.nanoTime();
				for (Integer val : donneesTriees)
					anrOrdre.add(val);
				long anrtempsDeConstructionOd = System.nanoTime() - tempsDebut;

				// Recherche trié (0 à 2n-1)
				tempsDebut = System.nanoTime();
				for (int i = 0; i < 2 * n; i++)
					abrOrdre.contains(i);
				long abrtempsRechercheOd = System.nanoTime() - tempsDebut;

				tempsDebut = System.nanoTime();
				for (int i = 0; i < 2 * n; i++)
					anrOrdre.contains(i);
				long anrtempsRechercheOd = System.nanoTime() - tempsDebut;

				// --- Écriture dans le CSV ---
				String line = String.format(Locale.US,
											"%d,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f\n",
											n,
											abrtempsDeConstructionAl / 1e6, anrtempsDeConstructionAl / 1e6,
											abrtempsRechercheAl / 1e6, anrtempsRechercheAl / 1e6,
											abrtempsDeConstructionOd / 1e6, anrtempsDeConstructionOd / 1e6,
											abrtempsRechercheOd / 1e6, anrtempsRechercheOd / 1e6
				);
				resulsts.write(line);
			}
			System.out.println("Terminé ! Résultats écrits dans 'resultats_arbres.csv'.");
		}
		catch (IOException e) {
			System.err.println(e.toString());
		}
	}
}
