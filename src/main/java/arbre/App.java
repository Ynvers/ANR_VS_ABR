package arbre;


public class App {
	public static void main(String[] args) {
		System.out.println("============Debut des test============");
		ABR<Integer> abre_entier = new ABR<>(50);
		// Construire un petit arbre pour démonstration
		abre_entier.add(30);
		abre_entier.add(70);
		abre_entier.add(20);
		abre_entier.add(40);
		abre_entier.add(60);
		abre_entier.add(80);
		System.out.println(abre_entier);
	}
}
