package arbre;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;

public class ANR<E> extends AbstractCollection<E> {

	private Noeud racine;
	private int taille;
	private Noeud feuillNoeud;
	private Comparator<? super E> cmp;
	private static final boolean ROUGE = true;
	private static final boolean NOIR = false;

	// Classe interne pour la gestionn des noeuds de l'arbre
	private class Noeud {

		E valeur;
		Noeud gauche;
		Noeud droit;
		Noeud pere;
		boolean couleur;

		Noeud(E valeur) {
			this.valeur = valeur;
			this.droit = feuillNoeud;
			this.gauche = feuillNoeud;
			this.pere = feuillNoeud;
			this.couleur = ROUGE;
		}

		//Constructeur pour le noeud nul
		Noeud() {
			this.valeur = null;
			this.droit = this;
			this.gauche = this;
			this.pere = this;
			this.couleur = NOIR;
		}

		Noeud minimum() {
			Noeud mini;
			mini = this;
			while (mini.gauche != feuillNoeud) {
				mini = mini.gauche;
			}
			return mini;
		}

		Noeud suivant() {
			if (this.droit != feuillNoeud) {
				return this.droit.minimum();
			}

			Noeud p = this.pere;
			Noeud enfant = this;
			while (p != feuillNoeud && enfant == p.droit) {
				enfant = p;
				p = p.pere;
			}
			return p;
		}
	}

	@SuppressWarnings("unchecked")
	public ANR(E valeur) {
		this.feuillNoeud = new Noeud();
		this.racine = new Noeud(valeur);
		this.racine.couleur = NOIR;
		this.taille = 1;
		this.cmp = (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
	}

	@SuppressWarnings("unchecked")
	public ANR() {
		this.feuillNoeud = new Noeud();
		this.racine = this.feuillNoeud;
		this.taille = 0;
		this.cmp = (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
	}

	public ANR(Comparator<E> cmp) {
		this.feuillNoeud = new Noeud();
		this.racine = this.feuillNoeud;
		this.taille = 0;
		this.cmp = cmp;
	}

	private void ajouterCorrection(Noeud new_noeud) {
		while (new_noeud.pere.couleur == ROUGE) {
			if (new_noeud.pere == new_noeud.pere.pere.gauche) {
				Noeud oncle = new_noeud.pere.pere.droit;
				if (oncle.couleur == ROUGE) {
					new_noeud.pere.couleur = NOIR;
					oncle.couleur = NOIR;
					new_noeud.pere.pere.couleur = ROUGE;
					new_noeud = new_noeud.pere.pere;
				} else {
					if (new_noeud == new_noeud.pere.droit) {
						new_noeud = new_noeud.pere;
						rotationGauche(new_noeud);
					}
					new_noeud.pere.couleur = NOIR;
					new_noeud.pere.pere.couleur = ROUGE;
					rotationDroite(new_noeud.pere.pere);
				}
			} else {
				Noeud oncle = new_noeud.pere.pere.gauche;
				if (oncle.couleur == ROUGE) {
					new_noeud.pere.couleur = NOIR;
					oncle.couleur = NOIR;
					new_noeud.pere.pere.couleur = ROUGE;
					new_noeud = new_noeud.pere.pere;
				} else {
					if (new_noeud == new_noeud.pere.gauche) {
						new_noeud = new_noeud.pere;
						rotationDroite(new_noeud);
					}
					new_noeud.pere.couleur = NOIR;
					new_noeud.pere.pere.couleur = ROUGE;
					rotationGauche(new_noeud.pere.pere);
				}
			}
		}
		this.racine.couleur = NOIR;
	}

	private void	supprmierCorrection(Noeud enfantNoeud)
	{
		while (enfantNoeud != this.racine && enfantNoeud.couleur == NOIR)
		{
			if (enfantNoeud == enfantNoeud.pere.gauche)
			{
				Noeud	enfantFrere = enfantNoeud.pere.droit;
				if (enfantFrere.couleur == ROUGE)
				{
					enfantFrere.couleur = NOIR;
					enfantNoeud.couleur = ROUGE;
					rotationGauche((enfantNoeud.pere));
					enfantFrere = enfantNoeud.pere.droit;
				}
				if (enfantFrere.gauche.couleur == NOIR && enfantFrere.droit.couleur == NOIR)
				{
					enfantFrere.couleur = ROUGE;
					enfantNoeud = enfantNoeud.pere;
				}
				else
				{
					if (enfantFrere.droit.couleur == NOIR)
					{
						enfantFrere.gauche.couleur = NOIR;
						enfantFrere.couleur = ROUGE;
						rotationDroite(enfantFrere);
						enfantFrere = enfantNoeud.pere.droit;
					}
					enfantFrere.couleur = enfantNoeud.pere.couleur;
					enfantNoeud.pere.couleur = NOIR;
					enfantFrere.droit.couleur = NOIR;
					rotationGauche(enfantNoeud.pere);
					enfantNoeud = this.racine;
				}
			}
			else
			{
				Noeud enfantFrere = enfantNoeud.pere.gauche;
				if (enfantFrere.couleur == ROUGE)
				{
					enfantFrere.couleur = NOIR;
					enfantNoeud.pere.couleur = ROUGE;
					rotationDroite(enfantNoeud.pere);
					enfantFrere = enfantNoeud.pere.gauche;
				}
				if (enfantFrere.droit.couleur == NOIR && enfantFrere.gauche.couleur == NOIR)
				{
					enfantFrere.couleur = ROUGE;
					enfantNoeud = enfantNoeud.pere;
				}
				else
				{
					if (enfantFrere.gauche.couleur == NOIR)
					{
						enfantFrere.droit.couleur = NOIR;
						enfantFrere.couleur = ROUGE;
						rotationGauche(enfantFrere);
						enfantFrere = enfantNoeud.pere.gauche;
					}
					enfantFrere.couleur = enfantNoeud.pere.couleur;
					enfantNoeud.pere.couleur = NOIR;
					enfantFrere.gauche.couleur = NOIR;
					rotationDroite(enfantNoeud.pere);
					enfantNoeud = this.racine;
				}
			}
		}
		enfantNoeud.couleur = NOIR;
	}

	private void rotationGauche(Noeud courant) {
		Noeud filsDroit = courant.droit;
		courant.droit = filsDroit.gauche;

		if (filsDroit.gauche != this.feuillNoeud) {
			filsDroit.gauche.pere = courant;
		}
		filsDroit.pere = courant.pere;
		if (courant.pere == this.feuillNoeud) {
			this.racine = filsDroit;
		} else if (courant.pere.gauche == courant) {
			courant.pere.gauche = filsDroit;
		} else {
			courant.pere.droit = filsDroit;
		}
		filsDroit.gauche = courant;
		courant.pere = filsDroit;
	}

	private void rotationDroite(Noeud courant) {
		Noeud filsGauche = courant.gauche;
		courant.gauche = filsGauche.droit;

		if (filsGauche.droit != this.feuillNoeud) {
			filsGauche.droit.pere = courant;
		}
		filsGauche.pere = courant.pere;
		if (courant.pere == this.feuillNoeud) {
			this.racine = filsGauche;
		} else if (courant.pere.gauche == courant) {
			courant.pere.gauche = filsGauche;
		} else {
			courant.pere.droit = filsGauche;
		}
		filsGauche.droit = courant;
		courant.pere = filsGauche;
	}

	@Override
	public boolean add(E nouvelle_valeur) {
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
			int order = this.cmp.compare(nouvelle_valeur, courant.valeur);
			if (order == 0) {
				return false;
			} else if (order > 0) {
				if (courant.droit == this.feuillNoeud) {
					courant.droit = new_noeud;
					new_noeud.pere = courant;
					this.taille++;
					ajouterCorrection(new_noeud);
					return true;
				}
				courant = courant.droit;
			} else {
				if (courant.gauche == this.feuillNoeud) {
					courant.gauche = new_noeud;
					new_noeud.pere = courant;
					this.taille++;
					ajouterCorrection(new_noeud);
					return true;
				}
				courant = courant.gauche;
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean contains(Object obj) {
		E element = (E) obj;
		Noeud courant = this.racine;

		while (courant != this.feuillNoeud) {
			int order = this.cmp.compare(element, courant.valeur);
			if (order == 0) {
				return true;
			} else if (order > 0) {
				courant = courant.droit;
			} else {
				courant = courant.gauche;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object obj) {
		E element = (E) obj;
		Noeud courant = racine;

		while (courant != this.feuillNoeud) {
			int order = this.cmp.compare(element, courant.valeur);
			if (order == 0) {
				break;
			}
			if (order < 0) {
				courant = courant.gauche;
			} else {
				courant = courant.droit;
			}
		}
		if (courant == this.feuillNoeud) {
			return false;
		}

		if (courant.gauche != this.feuillNoeud && courant.droit != this.feuillNoeud) {
			Noeud succ = courant.droit.minimum();
			courant.valeur = succ.valeur;
			courant = succ;
		}

		Noeud child = (courant.gauche != this.feuillNoeud) ? courant.gauche : courant.droit;
		if (child != this.feuillNoeud) {
			child.pere = courant.pere;
		}

		if (courant.pere == this.feuillNoeud) {
			racine = child;
		} else if (courant == courant.pere.gauche) {
			courant.pere.gauche = child;
		} else {
			courant.pere.droit = child;
		}
		supprmierCorrection(child);
		taille--;
		return true;
	}

	public int get_taille() {
		return this.taille;
	}

	@Override
	public Iterator<E> iterator() {
		return new ANRIterator();
	}

	@Override
	public int size() {
		return taille;
	}

	private class ANRIterator implements Iterator<E> {

		private Noeud prochain;
		private Noeud dernier_retournee;

		public ANRIterator() {
			if (racine != feuillNoeud) {
				prochain = racine.minimum();
			} else {
				prochain = feuillNoeud;
			}
			dernier_retournee = feuillNoeud;
		}

		@Override
		public boolean hasNext() {
			return prochain != feuillNoeud;
		}

		@Override
		public E next() {
			if (prochain == feuillNoeud) {
				return null;
			}
			dernier_retournee = prochain;
			prochain = prochain.suivant();
			return dernier_retournee.valeur;
		}

		@Override
		public void remove() {
			if (dernier_retournee == null || dernier_retournee == feuillNoeud) return;
			ANR.this.remove(dernier_retournee.valeur);
			dernier_retournee = feuillNoeud;
		}
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		toString(racine, buf, "", maxStrLen(racine));
		return buf.toString();
	}

	private void toString(Noeud x, StringBuffer buf, String path, int len) {
		if (x == feuillNoeud) {
			return;
		}
		toString(x.droit, buf, path + "D", len);
		for (int i = 0; i < path.length(); i++) {
			for (int j = 0; j < len + 6; j++) {
				buf.append(' ');
			}
			char c = ' ';
			if (i == path.length() - 1) {
				c = '+'; 
			}else if (path.charAt(i) != path.charAt(i + 1)) {
				c = '|';
			}
			buf.append(c);
		}
		buf.append("-- " + x.valeur.toString() + (x.couleur == ROUGE ? "(R)" : "(N)"));
		if (x.gauche != feuillNoeud || x.droit != feuillNoeud) {
			// buf.append(" --");
			for (int j = x.valeur.toString().length(); j < len; j++) {
				buf.append('-');
			}
			buf.append('|');
		}
		buf.append("\n");
		toString(x.gauche, buf, path + "G", len);
	}

	private int maxStrLen(Noeud x) {
		return x == feuillNoeud ? 0 : Math.max(x.valeur.toString().length(),
				Math.max(maxStrLen(x.gauche), maxStrLen(x.droit)));
	}

	/* min / max / hauteur pour ANR */
	public E min() {
		if (racine == feuillNoeud) return null;
		return racine.minimum().valeur;
	}

	public E max() {
		if (racine == feuillNoeud) return null;
		Noeud cur = racine;
		while (cur.droit != feuillNoeud) cur = cur.droit;
		return cur.valeur;
	}

	public E	getracine(){
		return this.racine.valeur;
	}

	/**
	 * Hauteur noire (black-height) : nombre de noeuds noirs rencontrés
	 * sur un chemin racine -> feuille sentinelle (exclut la sentinelle finale).
	 * Renvoie 0 pour un arbre vide.
	 */
	public int blackHeight() {
		return blackHeight(this.racine);
	}

	public int blackHeight(Noeud courant) {
		if (courant == feuillNoeud) return 0;
		int count = 0;
		Noeud cur = courant;
		while (cur != feuillNoeud) {
			if (cur.couleur == NOIR) count++;
			cur = cur.gauche;
		}
		return count;
	}
}
