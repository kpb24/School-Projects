import java.io.Serializable;
public class SimpleStack<T> implements SimpleStackInterface<T>, Serializable{
	private T stack[];
	private int top;
	private static final int CAPACITY = 100;
	
	public SimpleStack(){
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[CAPACITY];
		stack = tempStack;
		top = -1;
	}
	 
	public boolean add(T item){
		if(isFull() == false){
			stack[top + 1] = item;
			top++;
			return true;
		}
		return false;
	}
	
	public T remove(){
		if(isEmpty() == true){
			return null;
		}
		else{
			T topStack = stack[top];
			stack[top] = null;
			top--;
			return topStack;
		}
	}
	
	public Object[] topItems(int howMany){
		if(isEmpty() == true){
			System.out.println("This profile does not follow anyone");
		}
		T[] topNElements = (T[]) new Object[howMany];
		for(int i = top ; i >= 0;i--){
			topNElements[i] = stack[i];
			if(howMany == 5){
			System.out.println(topNElements[i]);
			}
		}
		return topNElements;
	}
	 
	public boolean contains(T item){
		boolean exists = false;
		int index = 0;
		while(!exists && (index < size())){
			if(item.equals(stack[index])){
				exists = true;
			}
			index++;
		}
		return exists;
		
	}
	
	public boolean isEmpty(){
		if(size() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isFull(){
		if(CAPACITY == size()){
			return true;
		}
		else{
			
		return false;}
	}
	 
	public int size(){
		return stack.length -1;
	}
}
