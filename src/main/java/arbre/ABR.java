package arbre;

import java.util.AbstractCollection;
import java.util.Iterator;

public class ABR<E> extends AbstractCollection<E>{
	private int	taille;

	@Override
	public int	size()
	{
		return taille;
	}
}
