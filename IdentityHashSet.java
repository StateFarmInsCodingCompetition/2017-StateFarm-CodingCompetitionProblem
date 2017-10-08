package sf.codingcomp.blocks.solution;

import java.util.AbstractSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * A VERY rudimentary implementation of set that uses {@linkplain System#identityHashCode(Object)}
 * to compute if two things are equal using {@linkplain IdentityHashMap}
 * 
 * @author Joshua Smith
 *
 * @param <E>
 */
public class IdentityHashSet<E> extends AbstractSet<E> implements Set<E>{

	private IdentityHashMap<E, Object> contents;	
	
	public IdentityHashSet(){
		contents = new IdentityHashMap<E, Object>();
	}
	
	@Override
	public boolean add(E e){
		return contents.put(e, true)!=null;
	}

	@Override
	public boolean remove(Object o){
		return contents.remove(o)!=null;
	}

	@Override
	public Iterator<E> iterator(){
		return contents.keySet().iterator();
	}

	@Override
	public int size(){
		return contents.size();
	}

}
