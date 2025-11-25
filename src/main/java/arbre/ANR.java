package arbre;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;

public class ANR<E> extends AbstractCollection<E>{
	private Noeud					racine;
	private int						taille;
	private Noeud					feuillNoeud;
	private Comparator<? super E>	cmp;
	private static final boolean	ROUGE = true;
	private static final boolean	NOIR = false;

	private class Noeud {
		E		valeur;
		Noeud	gauche;
		Noeud	droit;
		Noeud	pere;
		boolean	couleur;

		Noeud(E valeur){
			this.valeur = valeur;
			this.droit = feuillNoeud;
			this.gauche = feuillNoeud;
			this.pere = null;
			this.couleur = ROUGE;
		}

		//Constructeur pour le noeud nul
		Noeud(){
			this.valeur = null;
			this.droit = this;
			this.gauche = this;
			this.pere = null;
			this.couleur = NOIR;
		}

		Noeud	minimum(){
			Noeud	mini;
			mini = this;
			while (mini.gauche != feuillNoeud)
				mini = mini.gauche;
			return mini;
		}

		Noeud	suivant(){
			if (this.droit != feuillNoeud)
				return this.droit.minimum();

			Noeud	p = this.pere;
			Noeud	enfant = this;
			while(p != null && enfant == p.droit){
				enfant = p;
				p = p.pere;
			}
			return p;
		}
	}

	public ANR(E valeur){
		this.feuillNoeud = new Noeud();
		this.racine = new Noeud(valeur);
		this.racine.couleur = NOIR;
		this.taille = 1;
	}

	public ANR(){
		this.feuillNoeud = new Noeud();
		this.racine = this.feuillNoeud;
		this.taille = 0;
	}

	public ANR(Comparator<? super E> cmp){
		this.feuillNoeud = new Noeud();
		this.racine = this.feuillNoeud;
		this.taille = 0;
		this.cmp = cmp;
	}

	@Override
	public boolean	add(E nouvelle_valeur){
		Noeud new_noeud = new Noeud(nouvelle_valeur);
		
		// Cas où l'arbre est vide
		if (this.racine == this.feuillNoeud) {
			this.racine = new_noeud;
			this.racine.couleur = NOIR;
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
				if (courant.droit == this.feuillNoeud){
					courant.droit = new_noeud;
					new_noeud.pere = courant;
					this.taille++;
					return true;
				}
				courant = courant.droit;
			}
			else if (order < 0) {
				if (courant.gauche == this.feuillNoeud){
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
		Noeud courant = this.racine;

		while (courant != this.feuillNoeud){
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

		while (courant != this.feuillNoeud) {
			int order = compare(element, courant.valeur);
			if (order == 0) break;
			if (order < 0) courant = courant.gauche; else courant = courant.droit;
		}
		if (courant == this.feuillNoeud) return false;

		if (courant.gauche != this.feuillNoeud && courant.droit != this.feuillNoeud) {
			Noeud succ = courant.droit.minimum();
			courant.valeur = succ.valeur;
			courant = succ;
		}

		Noeud child = (courant.gauche != this.feuillNoeud) ? courant.gauche : courant.droit;
		if (child != this.feuillNoeud)
			child.pere = courant.pere;

		if (courant.pere == this.feuillNoeud) {
			racine = child;
		} else if (courant == courant.pere.gauche) {
			courant.pere.gauche = child;
		} else {
			courant.pere.droit = child;
		}

		taille--;
		return true;
	}

	private void	rotationGauche(Noeud courant)
	{
		Noeud filsDroit = courant.droit;
		courant.droit = filsDroit.gauche;

		if (filsDroit.gauche != this.feuillNoeud)
			filsDroit.gauche.pere = courant;
		filsDroit.pere = courant.pere;
		if (courant.pere == null)
		{
			this.racine = filsDroit;
		}else if (courant.pere.gauche == courant)
		{
			courant.pere.gauche = filsDroit;
		}else{
			courant.pere.droit = filsDroit;
		}
		filsDroit.gauche = courant;
		courant.pere = filsDroit;
	}

	private void	rotationDroite(Noeud courant)
	{
		Noeud filsGauche = courant.gauche;
		courant.gauche = filsGauche.droit;

		if (filsGauche.droit != this.feuillNoeud)
			filsGauche.droit.pere = courant;
		filsGauche.pere = courant.pere;
		if (courant.pere == null)
		{
			this.racine = filsGauche;
		}else if (courant.pere.gauche == courant)
		{
			courant.pere.gauche = filsGauche;
		}else{
			courant.pere.droit = filsGauche;
		}
		filsGauche.droit = courant;
		courant.pere = filsGauche;
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
		return new ANRIterator();
	}

	@Override
	public int	size()
	{
		return taille;
	}

	private class ANRIterator implements Iterator<E>{
		private Noeud prochain;
		private Noeud dernier_retournee;

		public	ANRIterator() {
			if (racine != null) {
				prochain = racine.minimum();
			} else {
				prochain = null;
			}
			dernier_retournee = null;
		}

		public boolean	hasNext(){
			return prochain != null;
		}

		public E	next(){
			if (prochain != null){
				dernier_retournee = prochain;
				prochain = prochain.suivant();
				return dernier_retournee.valeur;
			}
			return null;
		}

		public void	remove(){
			ANR.this.remove(dernier_retournee.valeur);
			dernier_retournee = null;
		}
	}

	@Override
	public String	toString() {
		StringBuffer buf = new StringBuffer();
		toString(racine, buf, "", maxStrLen(racine));
		return buf.toString();
	}

	private void	toString(Noeud x, StringBuffer buf, String path, int len) {
		if (x == feuillNoeud)
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
		buf.append("-- " + x.valeur.toString() + (x.couleur == ROUGE ? "(R)" : "(N)") );
		if (x.gauche != feuillNoeud || x.droit != feuillNoeud) {
			// buf.append(" --");
			for (int j = x.valeur.toString().length(); j < len; j++)
				buf.append('-');
			buf.append('|');
		}
		buf.append("\n");
		toString(x.gauche, buf, path + "G", len);
	}

	private int	maxStrLen(Noeud x) {
		return x == feuillNoeud ? 0 : Math.max(x.valeur.toString().length(),
				Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
	}
}
