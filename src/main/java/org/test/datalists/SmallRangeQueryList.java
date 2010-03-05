package org.test.datalists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SmallRangeQueryList<T> implements List<T> {
	private List<Object[]> internalList = null;
	private long maxSize = 0;

	public SmallRangeQueryList(List<Object[]> internalList) {
		setInternalList(internalList);
	}

	protected Object[] createFromElement(T e) {
		Object[] obj = new Object[2];
		obj[0] = maxSize;
		obj[1] = e;

		return obj;
	}

	public boolean add(T e) {
		return getInternalList().add(createFromElement(e));
	}

	public void add(int index, T element) {
		getInternalList().add(index, createFromElement(element));
	}

	public boolean addAll(Collection<? extends T> c) {
		throw new NotImplementedException();
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		throw new NotImplementedException();
	}

	public void clear() {
		getInternalList().clear();
	}

	public boolean contains(Object o) {
		for (Object[] arr : getInternalList()) {
			if (arr[1].equals(o)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		Object[] o = getInternalList().get(index);
		return (T) o[1];
	}

	public int indexOf(Object o) {
		int count = 0;
		for (Object[] arr : getInternalList()) {
			if (arr[1].equals(o)) {
				return count;
			}

			++count;
		}

		return -1;
	}

	public boolean isEmpty() {
		return getInternalList().isEmpty();
	}

	public Iterator<T> iterator() {
		return (Iterator<T>) Arrays.asList(toArray()).iterator();
	}

	public int lastIndexOf(Object o) {
		throw new NotImplementedException();
	}

	public ListIterator<T> listIterator() {
		throw new NotImplementedException();
	}

	public ListIterator<T> listIterator(int index) {
		throw new NotImplementedException();
	}

	public boolean remove(Object o) {
		for (Object[] arr : getInternalList()) {
			if (arr[1].equals(o)) {
				return getInternalList().remove(arr);
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public T remove(int index) {
		return (T) getInternalList().remove(index)[1];
	}

	public boolean removeAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	public boolean retainAll(Collection<?> c) {
		throw new NotImplementedException();
	}

	public T set(int index, T element) {
		return (T) getInternalList().set(index, createFromElement(element))[1];
	}

	public int size() {
		return getInternalList().size();
	}

	public List<T> subList(int fromIndex, int toIndex) {
		throw new NotImplementedException();
	}

	public Object[] toArray() {
		Object[] ret = new Object[getInternalList().size()];
		int count = 0;

		for (Object[] arr : getInternalList()) {
			ret[count] = arr[1];
			++count;
		}

		return ret;
	}

	public <T> T[] toArray(T[] a) {
		throw new NotImplementedException();
	}

	public void setInternalList(List<Object[]> internalList) {
		setMaxSize(internalList.size() > 0 ? (Long) internalList.get(0)[0] : 0);
		this.internalList = internalList;
	}

	public List<Object[]> getInternalList() {
		if (internalList == null) {
			setInternalList(new ArrayList<Object[]>());
		}

		return internalList;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public long getMaxSize() {
		return maxSize;
	}

}
