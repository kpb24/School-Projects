package StackAndQueuePackage;public interface StackInterface<T>{  /* Adds an element newElement to the top of this stack. */  public void push(T newElement);      /* Removes and returns the stack's top entry. Returns null if the stack is empty */  public T pop();      /* Retrieves the element at the top of the stack. Returns null if the stack is empty */  public T peek();    /* Checks whether the stack is empty. Returns true if the stack is empty */  public boolean isEmpty();    /* Clears the stack by removing all elements from the stack */  public void clear();} // end StackInterface