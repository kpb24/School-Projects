import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.*;
import StackAndQueuePackage.*;
public class TernaryTree<T> implements TernaryTreeInterface<T> {
	private TernaryNode<T> root;
	
	public TernaryTree() {
		root = null;
	} // end default constructor
	
	public TernaryTree(T rootData) {
		root = new TernaryNode<T>(rootData);
	} // end constructor
	
	public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree) {
		privateSetTree(rootData, leftTree, middleTree, rightTree);
	} // end constructor
	
	
	 public void setTree(T rootData) {
		root = new TernaryNode<>(rootData);
	} // end setTree
	
	public void setTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> middleTree, TernaryTreeInterface<T> rightTree) {
		privateSetTree(rootData, (TernaryTree<T>)leftTree, (TernaryTree<T>) middleTree,(TernaryTree<T>)rightTree);
	} // end setTree
	
	private void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree) {
		root = new TernaryNode<>(rootData);
		
		if ((leftTree != null) && !leftTree.isEmpty()) {
			root.setLeftChild(leftTree.root);
		}
		
		if ((middleTree != null) && !middleTree.isEmpty()) {
			if (middleTree != leftTree) {
				root.setMiddleChild(middleTree.root);
			} 
			else {
				root.setMiddleChild(middleTree.root.copy());
			}
		}
		
		if ((rightTree != null) && !rightTree.isEmpty()) {
			if (rightTree != leftTree && rightTree != middleTree) {
				root.setRightChild(rightTree.root);
			} 
			else {
				root.setRightChild(rightTree.root.copy());
			}
		} // end if
		
		if ((leftTree != null) && (leftTree != this)) {
			leftTree.clear();
		}
		
		if ((middleTree != null) && (middleTree != this)) {
			middleTree.clear();
		}
		
		if ((rightTree != null) && (rightTree != this)) {
			rightTree.clear();
		}
	} // end privateSetTree
		
		
	public T getRootData() {
		if (isEmpty()) {
			throw new EmptyTreeException("Invalid");
		} else {
			return root.getData();
		}
	} // end getRootData
	
	public boolean isEmpty() {
		return root == null;
	} // end isEmpty
	
	public void clear() {
		root = null;
	} // end clear
	
	public int getHeight() {
		return root.getHeight();
	} // end getHeight
	
	public int getNumberOfNodes() {
		return root.getNumberOfNodes();
	} // end getNumberOfNodes
	
	
	public Iterator<T> getPreorderIterator() {
		return new PreorderIterator();
	} // end getPreorderIterator
	
	public Iterator<T> getInorderIterator() {
		//inorder is not supported by ternary tree because there isn't a well defined 
		//place where the root could be processed. The root and middle will always go together.
	
		throw new UnsupportedOperationException();
	
	} // end getPostorderIterator
	
	public Iterator<T> getPostorderIterator() {
		return new PostorderIterator();
	} // end getPostorderIterator
	
	public Iterator<T> getLevelOrderIterator() {
		return new LevelOrderIterator();
	} // end getLevelOrderIterator
	
	
	private class PreorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;
		
		public PreorderIterator() {
			nodeStack = new LinkedStack<>();
			if (root != null) {
				nodeStack.push(root);
			}
		} // end default constructor
		
		public boolean hasNext() {
			return !nodeStack.isEmpty();
		} // end hasNext
		
		public T next() {
			TernaryNode<T> nextNode;
			
			if (hasNext()) {
				nextNode = nodeStack.pop();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();
				
				// Push into stack in reverse order of recursive calls
				if (rightChild != null) {
					nodeStack.push(rightChild);
				}
				if (middleChild != null) {
					nodeStack.push(middleChild);
				}
				if (leftChild != null) {
					nodeStack.push(leftChild);
				}
			} 
			else {
				throw new NoSuchElementException();
			}
			
			return nextNode.getData();
		} // end next
		
		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end PreorderIterator
	
	
	
	
	private class PostorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;
		private TernaryNode<T> currentNode;
		
		public PostorderIterator() {
			nodeStack = new LinkedStack<>();
			currentNode = root;
		} // end default constructor
		
		public boolean hasNext() {
			return !nodeStack.isEmpty() || (currentNode != null);
		} // end hasNext
		public T next() {
			boolean foundNext = false;
			TernaryNode<T> leftChild, middleChild, rightChild, nextNode = null;
			
			// Find leftmost leaf
			while (currentNode != null) {
				nodeStack.push(currentNode);
				leftChild = currentNode.getLeftChild();
				if (leftChild == null) {
					currentNode = currentNode.getRightChild();
				} else {
					currentNode = leftChild;
				}
			} // end while
			
			// // Stack is not empty either because we just pushed a node, or
			// // it wasn't empty to begin with since hasNext() is true.
			// // But Iterator specifies an exception for next() in case
			// // hasNext() is false.
			
			if (!nodeStack.isEmpty()) {
				nextNode = nodeStack.pop();
				// nextNode != null since stack was not empty before pop
				
				TernaryNode<T> parent = null;
				if (!nodeStack.isEmpty()) {
					parent = nodeStack.peek();
					if (nextNode == parent.getLeftChild()){
						currentNode = parent.getMiddleChild();
					} 
					else if (nextNode == parent.getMiddleChild()){
						currentNode = parent.getRightChild();
					} 
					else {
						currentNode = null;
					}
				} else {
					currentNode = null;
				}
			} else {
				throw new NoSuchElementException();
			} // end if
			
			return nextNode.getData();
		} // end next
		
		
		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	} // end PostorderIterator
	
	
	private class LevelOrderIterator implements Iterator<T> {
		private QueueInterface<TernaryNode<T>> nodeQueue;
		
		public LevelOrderIterator() {
			nodeQueue = new LinkedQueue<>();
			if (root != null) {
				nodeQueue.enqueue(root);
			}
		} // end default constructor
		
		public boolean hasNext() {
			return !nodeQueue.isEmpty();
		} // end hasNext
		
		public T next() {
			TernaryNode<T> nextNode;
			
			if (hasNext()) {
				nextNode = nodeQueue.dequeue();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();
				
				// Add to queue in order of recursive calls
				if (leftChild != null) {
					nodeQueue.enqueue(leftChild);
				}
				
				if (middleChild != null) {
					nodeQueue.enqueue(middleChild);
				}
				
				if (rightChild != null) {
					nodeQueue.enqueue(rightChild);
				}
			} else {
				throw new NoSuchElementException();
			}
			
			return nextNode.getData();
		} // end next
		
		public void remove() {
			throw new UnsupportedOperationException();
		} // end remove
	}
}
