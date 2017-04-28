package StackAndQueuePackage;
public class StackNode<T>		
	{
		private T data; // entry in queue
		private StackNode<T> previous; // link to previous node

		public StackNode(T dataPortion)
		{
			data = dataPortion;
			previous = null;	
		} // end constructor
		
		public StackNode(T dataValue, StackNode<T> linkValue)
		{
			data = dataValue;
			previous = linkValue;	
		} // end constructor

		public T getData()
		{
			return data;
		} // end getData

		public void setData(T newData)
		{
			data = newData;
		} // end setData

		public StackNode<T> getPreviousNode()
		{
			return previous;
		} // end getPrevioustNode
		
		public void setPreviousNode(StackNode<T> previousNode)
		{
			previous = previousNode;
		} // end setPreviousNode
	} // end StackNode