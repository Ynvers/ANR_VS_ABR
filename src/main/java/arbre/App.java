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
		System.out.println(arbre_entier.size());
		System.out.println(arbre_entier.contains(10));
		arbre_entier.remove(50);
		System.out.println(arbre_entier);

System.out.println("============Debut des test============");
		ANR<Integer> rouge_noir = new ANR<>(1);
		rouge_noir.add(2);
		rouge_noir.add(3);
		rouge_noir.add(4);
		rouge_noir.add(5);
		rouge_noir.add(6);
		rouge_noir.remove(2);
		rouge_noir.add(7);
		System.out.println(rouge_noir);
	}
}
