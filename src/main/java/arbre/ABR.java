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

	public ABR(Comparator cmp){
		this.racine = null;
		this.taille = 0;
		this.cmp = cmp;
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
		public boolean hasNext(){
			return false;
		}

		public E next(){
			return null;
		}

		public void remove(){

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
