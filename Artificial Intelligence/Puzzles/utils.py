import heapq
import operator, math, random, sys, bisect

def Stack():
	return []
		
		
		
class PriorityQueue:
	def __init__(self, initial, costfn=lambda node: node.path_cost):
		self.heap   = []
		self.states = {}
		self.costfn = costfn
		self.add(initial)
	
	def add(self, node):
		cost = self.costfn(node)
		heapq.heappush(self.heap, (cost, node))
		self.states[node.state] = node
		
	def pop(self):
		(cost, node) = heapq.heappop(self.heap)
		self.states.pop(node.state, None)
		return node
	
	def replace(self, node):
		if node.state not in self:
			raise ValueError('{} not there to replace'.format(node.state))
		for (i, (cost, old_node)) in enumerate(self.heap):
			if old_node.state == node.state:
				self.heap[i] = (self.costfn(node), node)
				heapq._siftdown(self.heap, 0, i)
				return
				
	def __contains__(self, state): return state in self.states
	
	def __len__(self): return len(self.heap)
		
		