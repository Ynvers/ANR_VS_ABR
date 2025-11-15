package arbre;

import java.util.AbstractCollection;
import java.util.Iterator;

public class ANR<E> extends AbstractCollection<E>{
	private int	hauteur;

	public int	size()
	{
		return hauteur;
	}
}
