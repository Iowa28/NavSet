package asd3;

public class LinkedList<T> {
	private Node<T> head;
	private Node<T> last;
	private int size;
	
	public LinkedList() {
		this.head = null;
		this.last = null;
		this.size = 0;
	}
	
	protected class Node<T> {
		private T value;
		private Node<T> next;
		
		public Node(T value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		private LinkedList getEnclosingInstance() {
			return LinkedList.this;
		}
		
		
	}
	
	public void addFirst(T obj) {
		Node<T> node = new Node<>(obj);
		node.next = head;
		this.head = node;
		size++;
	}
	
	public void add(T obj) { // addLast
		Node<T> node = new Node<>(obj);
		if (size == 0) {
			node.next = null;
			this.head = node;
			this.last = node;
		} else {
			node.next = null;
			last.next = node;
			this.last = node;
		}
		size++;
	}
	
	public void addAfter(T obj, int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		else if (index == 0) {
			addFirst(obj);
		}
		else if (index == size) {
			add(obj);
		} else {
			Node<T> newNode = new Node<>(obj);
			Node<T> indexNode = head;
			for (int i = 0; i < index - 1; i++) {
				indexNode = indexNode.next;
			}
			Node<T> otherNode = indexNode.next;
			indexNode.next = newNode;
			newNode.next = otherNode;
			size++;
		}
	}
	
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> newNode = head;
		for (int i = 0; i < index; i++) {
			newNode = newNode.next;
		}
		return newNode.value;
	}
	
	public T remove(T obj) {
		int index = indexOf(obj);
		return remove(index);
	}
	
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			Node<T> node = head;
			head = head.next;
			size--;
			return node.value;
		}
		Node<T> node = head;
		for (int i = 0; i < index - 1; i++) {
			node = node.next;
		}
		Node<T> element = node.next;
		Node<T> elementNext = element.next;
		node.next = elementNext;
		size--;
		return element.value;
	}
	
	public T merge(LinkedList<T> linkL) {
		last.next = linkL.head;
		this.last = linkL.last;
		return this.last.value;
	}
	
	public int indexOf(T obj) {
		Node<T> newNode = head;
		for (int i = 0; i < size & newNode.next != null; i++) {
			if (newNode.value.equals(obj)) {
				return i;
			}
			newNode = newNode.next;
		}
		return -1;
	}

	public int size() {
		return size;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkedList other = (LinkedList) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public static void main(String[] args) {
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.add(12);
		ll.add(13);
		ll.add(155);
		ll.addAfter(14, 1);
		System.out.println(ll.get(1));
	}
}
