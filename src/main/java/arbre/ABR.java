package arbre;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;

public class ABR<E> extends AbstractCollection<E>{
	private Noeud racine;
	private int	taille;
	private Comparator<? super E> cmp;

	private class Noeud {
		E valeur;
		Noeud gauche;
		Noeud droit;
		Noeud pere;

		Noeud(E valeur){
			this.valeur = valeur;
			this.droit = null;
			this.gauche = null;
			this.pere = null;
		}

		Noeud minimum(){
			Noeud mini;
			mini = this;
			while (mini.gauche != null){
				mini = mini.gauche;
			}
			return mini;
		}

		Noeud suivant(){
			if (this.droit != null){
				return this.droit.minimum();
			}

			Noeud p = this.pere;
			Noeud enfant = this;
			while(p != null && enfant == pere.droit){
				enfant = p;
				p = p.pere;
			}
			return p;
		}
	}

	public ABR(){

	}

	@Override
	public int	size()
	{
		return taille;
	}
}
