package inf5;

import java.util.*;

/**
* Aminov Niaz, 11-903. First course, second semester. 
* Student of Kazan Federal University, faculty of the Higher School of Information Technology and Intelligent Systems.
* 
* This class implements the NavigableSet interface and is designed to be able to find the closest matches by a given value.
* 
* 11.03.2020
*/

public class NavSet<T> extends AbstractSet<T> implements NavigableSet<T>{
	private T[] data;
	
	private Comparator<T> comp;
	
	public NavSet(Comparator<T> comparator) {
		this.data = (T[]) new Object[0];
		this.comp = comparator;
	}
	
	/**
	 * The NavSet class constructor assigns an array and a predefined comparator.
	 * Elements of array must be sorted initially.
	*/
	
	public NavSet(T[] arr, Comparator<T> comparator) {
		this.data = arr;
		this.comp = comparator;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrIterator(data);
	}
	
	/**
	 * Returns the length of the list.
	 */

	@Override
	public int size() {
		return data.length;
	}
	
	/**
	 * Adds an element to the set; if there is no such element, it returns true, otherwise it returns false.
	 */
	
	public boolean add(T obj) {
		if (!has(obj)) {
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
		return false;
	}
	
	/**
	 * Checks if the set contains a given object.
	 */
	
	public boolean has(T obj) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].equals(obj)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Comparator<? super T> comparator() {
		return comp;
	}
	
	/**
	 * Returns the first element of a set
	 */

	@Override
	public T first() {
		return data[0];
	}
	
	/**
	 * Returns the last element of a set
	 */

	@Override
	public T last() {
		return data[data.length - 1];
	}
	
	/**
	 * Returns the next element of the set.
	 */

	@Override
	public T ceiling(T obj) {
		int index = indexOf(obj);
		if (index < data.length - 1 & index != -1) {
			return data[index + 1];
		}
		return null;
	}
	
	/**
	 * Returns an iterator for a set with values in reverse order.
	 */

	@Override
	public Iterator<T> descendingIterator() {
		int size = size();
		T[] newData = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			newData[i] = data[size - i];
		}
		return new ArrIterator(newData);
	}
	
	/**
	 * Returns the original set with values in reverse order
	 */

	@Override
	public NavigableSet<T> descendingSet() {
		int size = size();
		T[] newData = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			newData[i] = data[size - i];
		}
		return new NavSet<T>(newData, comp);
	}
	
	/**
	 * Returns the previous element of the set.
	 */

	@Override
	public T floor(T obj) {
		int index = indexOf(obj);
		if (index > 0) {
			return data[index - 1];
		}
		return null;
	}
	
	/**
	 * Returns a SortedSet that begins with this element
	 */

	@Override
	public SortedSet<T> headSet(T obj) {
		int index = indexOf(obj);
		T[] newData = (T[]) new Object[index];
		for (int i = 0; i < newData.length; i++) {
			newData[i] = data[i];
		}
		return new NavSet<T>(newData, comp);
	}
	
	/**
	 * Returns a Navigable that begins with this element. If the 'inclusive' is true - including, if false - exclusively.
	 */

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
	
	/**
	 * Returns the maximum element of the set after the given object.
	 */

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
	
	/**
	 * Returns the minimum element of the set before this object.
	 */

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
	
	/**
	 * Removes the first element of the set and returns it.
	 */

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
	
	/**
	 * Removes the last element of the set and returns it.
	 */

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
	
	/**
	 * Returns a SortedSet whose elements are in the specified range.
	 */

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
	
	/**
	 * Returns a NavigableSet whose elements are in the specified range. 
	 * Inclusive, depending on the values of lowerInclusive and higherInclusive
	 */

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
	
	/**
	 * Returns a set whose elements are larger than the passed object.
	 */

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
	
	/**
	 * Returns a set whose elements are larger than the passed object. 
	 * The element is inclusive depending on the value of the 'inclusive'.
	 */

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
	
	/**
	 * Returns the index of this element in the set.
	 */
	
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
	
	/**
	 * testing...
	 */
	
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
