package inf5;

import java.util.*;

public class NavSet<T> extends AbstractSet<T> implements NavigableSet<T>{
	private T[] data;
	
	private Comparator<T> comp;
	
	public NavSet(Comparator<T> comparator) {
		this.data = (T[]) new Object[0];
		this.comp = comparator;
	}
	
	public NavSet(T[] arr, Comparator<T> comparator) {
		this.data = arr;
		this.comp = comparator;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrIterator(data);
	}

	@Override
	public int size() {
		return data.length;
	}
	
	public boolean add(T obj) {
		T[] newArr = (T[]) new Object[data.length + 1];
		int j = 0;
		for (int i = 0; i < newArr.length; i++) {
			if (i == data.length & i == j) {
				newArr[i] = obj;
				break;
			}
			if (data[i].equals(obj) & comp.compare(data[i], obj) == 0) { 
				return false;
			} else if(comp.compare(data[i], obj) < 0){
				newArr[j] = obj;
				j++;
			} else {
				newArr[i] = data[j];
				j++;
			}
		}
		this.data = newArr;
		return true;
	}
	

	@Override
	public Comparator<? super T> comparator() {
		return comp;
	}

	@Override
	public T first() {
		return data[0];
	}

	@Override
	public T last() {
		return data[data.length - 1];
	}

	@Override
	public T ceiling(T obj) {
		int index = indexOf(obj);
		if (index < data.length - 1) {
			return data[index + 1];
		}
		return null;
	}

	@Override
	public Iterator<T> descendingIterator() {
		int size = size();
		T[] newData = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			newData[i] = data[size - i];
		}
		return new ArrIterator(newData);
	}

	@Override
	public NavigableSet<T> descendingSet() {
		int size = size();
		T[] newData = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			newData[i] = data[size - i];
		}
		return new NavSet<T>(newData, comp);
	}

	@Override
	public T floor(T obj) {
		int index = indexOf(obj);
		if (index > 0) {
			return data[index - 1];
		}
		return null;
	}

	@Override
	public SortedSet<T> headSet(T obj) {
		int index = indexOf(obj);
		T[] newData = (T[]) new Object[index];
		for (int i = 0; i < newData.length; i++) {
			newData[i] = data[i];
		}
		return new NavSet<T>(newData, comp);
	}

	@Override
	public NavigableSet<T> headSet(T obj, boolean inclusive) {
		int index = indexOf(obj);
		if (inclusive) {
			index--;
		}
		T[] newData = (T[]) new Object[index];
		for (int i = 0; i < index; i++) {
			newData[i] = data[i];
		}
		return new NavSet<T>(newData, comp);
	}

	@Override
	public T higher(T obj) {
		int index = indexOf(obj);
		if (index < data.length - 1) {
			for (int i = index + 1; i < data.length; i++) {
				if (comp.compare(data[i], obj) > 0) {
					return data[i];
				}
			}
		}
		return null;
	}

	@Override
	public T lower(T obj) {
		int index = indexOf(obj);
		if (index > 0) {
			for (int i = index - 1; i >= 0; i--) {
				if (comp.compare(data[i], obj) < 0) {
					return data[i];
				}
			}
		}
		return null;
	}

	@Override
	public T pollFirst() {
		if (data.length == 0) {
			return null;
		}
		T obj = first();
		T[] newData = (T[]) new Object[data.length - 1];
		int j = 0;
		for (int i = 1; i < data.length; i++) {
			newData[j] = data[i];
			j++;
		}
		data = newData;
		return obj;
	}

	@Override
	public T pollLast() {
		if (data.length == 0) {
			return null;
		}
		T obj = last();
		T[] newData = (T[]) new Object[data.length - 1];
		for (int i = 0; i < data.length - 1; i++) {
			newData[i] = data[i];
		}
		data = newData;
		return obj;
	}

	@Override
	public SortedSet<T> subSet(T fromEl, T toEl) {
		int size = indexOf(toEl) - indexOf(fromEl);
		if (size < 0) {
			throw new IllegalArgumentException();
		}
		T[] newData = (T[]) new Object[size];
		int j = 0;
		for (int i = indexOf(fromEl); i < indexOf(toEl); i++) {
			newData[j] = data[i];
			j++;
		}
		return new NavSet<T>(newData, comp);
	}

	@Override
	public NavigableSet<T> subSet(T fromEl, boolean lowerInclusive, T toEl, boolean higherInclusive) {
		int lowerLimit = indexOf(fromEl);
		int higherLimit = indexOf(toEl);
		if (!lowerInclusive) {
			lowerLimit--;
		}
		if (higherInclusive) {
			higherLimit++;
		}
		int size = higherLimit - lowerLimit;
		if (size < 0) {
			throw new IllegalArgumentException();
		}
		T[] newData = (T[]) new Object[size];
		int j = 0;
		for (int i = lowerLimit; i < higherLimit; i++) {
			newData[j] = data[i];
			j++;
		}
		return new NavSet<T>(newData, comp);
	}

	@Override
	public SortedSet<T> tailSet(T obj) {
		int index = indexOf(obj);
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		T[] newData = (T[]) new Object[data.length - index];
		int j = 0;
		for (int i = index; i < data.length; i++) {
			newData[j] = data[i];
		}
		return new NavSet<T>(newData, comp);
	}

	@Override
	public NavigableSet<T> tailSet(T obj, boolean inclusive) {
		int index = indexOf(obj);
		if (!inclusive) {
			index--;
		}
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		T[] newData = (T[]) new Object[data.length - index];
		int j = 0;
		for (int i = index; i < data.length; i++) {
			newData[j] = data[i];
		}
		return new NavSet<T>(newData, comp);
	}
	
	public int indexOf(T obj) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}
	
	private class ArrIterator implements Iterator{
		private int cursor;
		private T[] arr;
		
		public ArrIterator(T[] arr) {
			this.cursor = 0;
			this.arr = arr;
		}

		@Override
		public boolean hasNext() {
			for (T el: arr) {
				if (!el.equals(null)) {
					return true;
				}
			} return false;
		}

		@Override
		public Object next() {
			T obj = (T) arr[cursor];
			cursor++;
			return obj;
		}
	}
	
	public static void main(String[] args) {
		Book book1 = new Book("Lev Tolstoy", "War and Peace");
		Book book2 = new Book("Lev Tolstoy", "Anna Karenina");
		Book book3 = new Book("Michael Bulgakov", "Master and Margarite");
		Book book4 = new Book("Karl Marx", "Capital");
		
		Book[] books = new Book[]{book1, book2, book3, book4};
		new BookComparator().sort(books);
		
		NavSet<Book> ns = new NavSet<>(books, new BookComparator());
		System.out.println(ns.lower(book1).getTitle());
		
		Book book5 = new Book("Boris Pasternak", "Doctor Zhivago");
		ns.add(book5);
		System.out.println(ns.ceiling(book3).getTitle());
	}
}
