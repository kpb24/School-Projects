package StackAndQueuePackage;
public class QueueNode<T>
	{
		private T data; // entry in queue
		private QueueNode<T> next; // link to next node

		public QueueNode(T dataPortion)
		{
			data = dataPortion;
			next = null;	
		} // end constructor
		
		public QueueNode(T dataPortion, QueueNode<T> linkPortion)
		{
			data = dataPortion;
			next = linkPortion;	
		} // end constructor
		
		public T getData()
		{
			return data;
		} // end getData

		public void setData(T newData)
		{
			data = newData;
		} // end setData

		public QueueNode<T> getNextNode()
		{
			return next;
		} // end getNextNode
		
		public void setNextNode(QueueNode<T> nextNode)
		{
			next = nextNode;
		} // end setNextNode
	} // end Node