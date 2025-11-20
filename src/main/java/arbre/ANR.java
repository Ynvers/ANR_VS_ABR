package arbre;

import java.util.AbstractCollection;
import java.util.Iterator;

public class ANR<E> extends AbstractCollection<E>{
	private int	hauteur;

	@Override
	public Iterator<E> iterator(){
		return new ABRIterator();
	}

	public int	size()
	{
		return hauteur;
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
}
