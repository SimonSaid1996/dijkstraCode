/******************************************************************************
 *  Compilation:  javac Bag.java
 *  Execution:    java Bag < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A generic bag or multiset, implemented using a singly-linked list.
 *
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java Bag < tobe.txt
 *  size of bag = 14
 *  is
 *  -
 *  -
 *  -
 *  that
 *  -
 *  -
 *  be
 *  -
 *  to
 *  not
 *  or
 *  be
 *  to
 *
 ******************************************************************************/
 
// Modified for CS 1501 Summer 2016
// i have added a delete method for deleting the items in the bag, the delete function takes an Item parameter and is void type

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code Bag} class represents a bag (or multiset) of 
 *  generic items. It supports insertion and iterating over the 
 *  items in arbitrary order.
 *  <p>
 *  This implementation uses a singly-linked list with a static nested class Node.
 *  See {@link LinkedBag} for the version from the
 *  textbook that uses a non-static nested class.
 *  See {@link ResizingArrayBag} for a version that uses a resizing array.
 *  The <em>add</em>, <em>isEmpty</em>, and <em>size</em> operations
 *  take constant time. Iteration takes time proportional to the number of items.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Item> the generic type of an item in this bag
 */
public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of bag
    private int n;               // number of elements in bag

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty bag.
     */
    public Bag() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this bag is empty.
     *
     * @return {@code true} if this bag is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this bag.
     *
     * @return the number of items in this bag
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this bag.
     *
     * @param  item the item to add to this bag
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

//////////////////////////////////////////		
	 /**
     * delete the item from this bag.
     *
     * @param  item the item to delete from this bag
     */	
	public void delete(Item item) {		//i assume we already know this item will be in the link-list
        Node<Item> curN = first;
		Node<Item> prevN = first;		//to store the previous node
		boolean firstChecked = false;
		if(curN == null){		//if the first node is null, skip it
			
		}
		else{
			while(curN.next != null){
				if(curN == first && curN.item == item){		//situation when removing the first node only
					first = curN.next;	//directly set the first node to null
					//System.out.println("first remove the item "+item);
					firstChecked = true;
					break;
				}
				if(curN.item == item){		//need to specify the situation when removing the first node				
					//directly skip one node	
					Node<Item> temp = curN.next;
					curN = prevN;
					prevN.next = temp;
					//System.out.println("remove the item "+item);				
					break;	
				}
				prevN = curN;
				curN = curN.next;
					/*if(first == null){	//break when the first node is null
						break;
					}*/
			}
			if(curN.item == item && !firstChecked){//the last node situation
				if(prevN != curN){
					prevN.next = null;
					//System.out.println(" last remove the item "+item+" prevN is "+prevN.item);
				}
				else{		//only one element in the bag, delete the lat one situation
					first = null;
				}				
			}
			/*first = new Node<Item>();
			first.item = item;
			first.next = curN;*/
			n--;
		}

		
    }
	
	
/////////////////////////////////////////	
    /**
     * Returns an iterator that iterates over the items in this bag in arbitrary order.
     *
     * @return an iterator that iterates over the items in this bag in arbitrary order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

    /**
     * Unit tests the {@code Bag} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        /*Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }*/
		Bag<String> bag = new Bag<String>();
		bag.add("mother ");
		bag.add("koww");
		bag.add("wtf");
		bag.add("valarien sucks");
		bag.add("trump");
		
		/*StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
		bag.delete("wtf");
		
		StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }*/
    }

}




