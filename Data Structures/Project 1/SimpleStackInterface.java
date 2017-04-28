/**
 * A simple stack interface: items are fetched most-recent first
 */
public interface SimpleStackInterface<T> {

    /**
     * Adds a new T object to the top of the stack
     * @param item The item to be added
     * @return True if successful, false if there is no more room
     */
    public boolean add(T item);
	
	
    /**
     * Removes the top T object
     * @return The item that is removed, or null if no item can be removed
     */
     public T remove();
	
    /**
     * Returns an array of size n containing the top n items from the stack. The most recently-added
      * items should be listed first.
      * @param howMany The number of items to return
     * @return An array of the most recently-added items, or null if the stack does not contain
      * enough items
     */
    public Object[] topItems(int howMany);
	
	
    /**
      * Determines if the stack contains the specified item
      * @param item The item in question
      * @return True if the item is contained in the stack, false otherwise
      */
     public boolean contains(T item);

	
    /**
     * Determines if the stack is empty
     * @return True if the stack is empty, false otherwise
     */
     public boolean isEmpty();

	
	
	/**
     * Determines if the stack is full (at capacity)
     * @return True if the stack is full, false otherwise
     */
    public boolean isFull();

	
    /**
     * Determines the size of the stack (the current number of objects in the stack, NOT the
     * capacity)
     * @return The number of objects in the stack
     */
    public int size();
}

