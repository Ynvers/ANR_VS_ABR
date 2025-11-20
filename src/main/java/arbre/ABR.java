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
			return this.droit;
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
