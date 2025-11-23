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

		Noeud	minimum(){
			Noeud	mini;
			mini = this;
			while (mini.gauche != null){
				mini = mini.gauche;
			}
			return mini;
		}

		Noeud	suivant(){
			if (this.droit != null){
				return this.droit.minimum();
			}

			Noeud	p = this.pere;
			Noeud	enfant = this;
			while(p != null && enfant == p.droit){
				enfant = p;
				p = p.pere;
			}
			return p;
		}
	}

	public ABR(E valeur){
		this.racine = new Noeud(valeur);
		this.taille = 1;
	}

	public ABR(){
		this.racine = null;
		this.taille = 0;
	}

	public ABR(Comparator<? super E> cmp){
		this.racine = null;
		this.taille = 0;
		this.cmp = cmp;
	}

	@Override
	public boolean	add(E nouvelle_valeur){
		Noeud new_noeud = new Noeud(nouvelle_valeur);
		
		// Cas où l'arbre est vide
		if (this.racine == null) {
			this.racine = new_noeud;
			this.taille = 1;
			return true;
		}

		Noeud courant = this.racine;
		while (true) {
			int order = compare(nouvelle_valeur, courant.valeur);
			if (order == 0){
				return false;
			}
			else if (order > 0){
				if (courant.droit == null){
					courant.droit = new_noeud;
					new_noeud.pere = courant;
					this.taille++;
					return true;
				}
				courant = courant.droit;
			}
			else if (order < 0) {
				if (courant.gauche == null){
					courant.gauche = new_noeud;
					new_noeud.pere = courant;
					this.taille++;
					return true;
				}
				courant = courant.gauche;
			}
			else{
				return false;
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean	contains(Object obj){
		E element = (E) obj;
		// Noeud new_noeud = new Noeud((E) obj);
		Noeud courant = this.racine;

		while (courant != null){
			int	order = compare(element, courant.valeur);
			if (order == 0){
				return true;
			}
			else if (order > 0){
				courant = courant.droit;
			}
			else{
				courant = courant.gauche;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean	remove(Object obj){
		E element = (E) obj;
		Noeud courant = racine;

		while (courant != null) {
			int order = compare(element, courant.valeur);
			if (order == 0) break;
			if (order < 0) courant = courant.gauche; else courant = courant.droit;
		}

		if (courant == null) return false;

		if (courant.gauche != null && courant.droit != null) {
			Noeud succ = courant.droit.minimum();
			courant.valeur = succ.valeur;
			courant = succ;
		}

		Noeud child = (courant.gauche != null) ? courant.gauche : courant.droit;
		if (child != null)
			child.pere = courant.pere;

		if (courant.pere == null) {
			racine = child;
		} else if (courant == courant.pere.gauche) {
			courant.pere.gauche = child;
		} else {
			courant.pere.droit = child;
		}

		taille--;
		return true;
	}

	public int	get_taille(){
		return this.taille;
	}

	@SuppressWarnings("unchecked")
	public int	compare(E element1, E element2){
		if (this.cmp != null){
			return (cmp.compare(element1, element2));
		}
		return ((Comparable<? super E>) element1).compareTo(element2);
	}

	@Override
	public Iterator<E> iterator(){
		return new ABRIterator();
	}

	@Override
	public int	size()
	{
		return taille;
	}

	private class ABRIterator implements Iterator<E>{
		private Noeud prochain;
		private Noeud dernier_retournee;

		public ABRIterator() {
			if (racine != null) {
				prochain = racine.minimum();
			} else {
				prochain = null;
			}
			dernier_retournee = null;
		}

		public boolean hasNext(){
			return prochain != null;
		}

		public E next(){
			if (prochain != null){
				dernier_retournee = prochain;
				prochain = prochain.suivant();
				return dernier_retournee.valeur;
			}
			return null;
		}

		public void remove(){
			ABR.this.remove(dernier_retournee.valeur);
			dernier_retournee = null;
		}
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		toString(racine, buf, "", maxStrLen(racine));
		return buf.toString();
	}

	private void toString(Noeud x, StringBuffer buf, String path, int len) {
		if (x == null)
			return;
		toString(x.droit, buf, path + "D", len);
		for (int i = 0; i < path.length(); i++) {
			for (int j = 0; j < len + 6; j++)
				buf.append(' ');
			char c = ' ';
			if (i == path.length() - 1)
				c = '+';
			else if (path.charAt(i) != path.charAt(i + 1))
				c = '|';
			buf.append(c);
		}
		buf.append("-- " + x.valeur.toString());
		if (x.gauche != null || x.droit != null) {
			buf.append(" --");
			for (int j = x.valeur.toString().length(); j < len; j++)
				buf.append('-');
			buf.append('|');
		}
		buf.append("\n");
		toString(x.gauche, buf, path + "G", len);
	}

	private int maxStrLen(Noeud x) {
		return x == null ? 0 : Math.max(x.valeur.toString().length(),
				Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
	}
}
