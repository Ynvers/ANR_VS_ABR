package arbre;


public class App {
	public static void main(String[] args) {
		System.out.println("============Debut des test============");
		ABR<Integer> arbre_entier = new ABR<>(50);
		// Construire un petit arbre pour démonstration
		arbre_entier.add(30);
		arbre_entier.add(70);
		arbre_entier.add(20);
		arbre_entier.add(40);
		arbre_entier.add(60);
		arbre_entier.add(80);
		arbre_entier.add(35);
		arbre_entier.add(25);
		System.out.println(arbre_entier);
		System.out.println(arbre_entier.get_taille());
		System.out.println(arbre_entier.contains(10));
		arbre_entier.remove(50);
		System.out.println(arbre_entier);
	}
}
