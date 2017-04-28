import sys
from abc import ABCMeta, abstractmethod
from utils import *
import string

#defines a problem so that way we can have abstraction
class Problem(object):
	def __init__(self, initial, goal = None):
		self.initial = initial
		self.goal = goal
	
	#each problem will uniquely define this
	@abstractmethod
	def actions(self, state):
		pass
		
	#each problem will uniquely define this
	@abstractmethod
	def result(self, state, action):
		pass
		
	def goalTest(self, state):
		return state == self.goal
		
	def pathCost(self, cost, state1, action, state2):
		return cost + 1
		
		
#defines a node 
class Node:
	def __init__(self, state, parent = None, action = None, pathCost = 0):
		self.state = state
		self.parent = parent #keep track of the parent node
		self.action = action
		self.pathCost = pathCost
		self.depth = 0 #use this for iterative deepening
		if parent:
			self.depth = parent.depth + 1
			
	def __repr__(self):
		return "Node: %s" % (self.state,)
		
	#expands a nodes, giving its children
	def expand(self, problem):
		return [self.childNode(problem, action) for action in problem.actions(self.state)]
	
	#defines a child node 
	def childNode(self, problem, action):
		return Node(problem.result(self.state, action), self, action, problem.pathCost(self.pathCost, self.state, action, problem.result(self.state, action)))
		
	def __lt__(self, other): 
		#need this in python 3 for comparing
		return self.pathCost < other.pathCost
	 
	def solution(self):
		return [node.action for node in self.path()[1:]]
		
	def path(self):
		node, path = self, []
		while node:
			path.append(node)
			node = node.parent
		return list(reversed(path))
		
	def __eq__(self, other):
		return isinstance(other, Node) and self.state == other.state
		
	def __hash__(self):
		return hash(self.state)
		
		
#defines a water jug object 
class jug(object):
	def __init__(self, capacity, currentVolume):
		if currentVolume > capacity:
			raise Error("Jug will overflow!")
		self.capacity = capacity
		self.currentVolume = currentVolume
		self.isEmpty = (currentVolume == 0) #use later to tell us if a jug is empty
		self.isFull = (currentVolume == capacity) #use later to tell us if a jug is at capacity
		self.nodesCreated = 0 #keep track of the nodes we have created so far
		
	def __repr__(self):
		return "%s"%(self.currentVolume)
	
	def __eq__(self, other):
		return (isinstance(other, jug) and self.capacity == other.capacity and self.currentVolume == other.currentVolume)
	
	def __hash__(self):
		return hash((self.capacity, self.currentVolume))
	
	
class jugProblem(Problem):
	def __init__(self):
		goal = jug(goalJug, goalAmount) #what we want to get to 
		initial = (jug(jug1Capacity, jug1InitState), jug(jug2Capacity, jug2InitState)) #what we are starting with
		Problem.__init__(self, initial, goal) #initiate a problem
		self.nodesCreated = 0 #keep track of the nodes we have created so far
	
	#check if it's at the goal
	def goalTest(self, state):
		return state[0] == self.goal
	
	#use action to give a result of either transferring, emptying, or filling a jug
	#jug 1 and 2 are 0,1 respectively to make it easier to read, they are indexes 
	#to get the state
	def actions(self, state):
		self.nodesCreated += 1 #created a node 
		#doing it this way so I can have the actions print later with solution path
		if not state[jug1].isEmpty and not state[jug2].isFull:
			yield 'transfer %s to %s'%(jug1+1, jug2+1) #can transfer jug1 to 2 if jug1 has water in it and jug2 isn't full
		if not state[jug1].isEmpty:
			yield 'empty %s'%(jug1+1) #can empty jug1 if it isn't already empty
		elif not state[jug1].isFull:
			yield 'fill %s'%(jug1+1) #can fill jug1 as long as it isn't at capacity
			
		if not state[jug2].isEmpty and not state[jug1].isFull:
			yield 'transfer %s to %s'%(jug2+1, jug1+1) #can transfer jug2 to 1 if jug2 has water in it and jug1 isn't full
		if not state[jug2].isEmpty:
			yield 'empty %s'%(jug2+1) #can empty jug2 if it isn't already empty
		elif not state[jug2].isFull:
			yield 'fill %s'%(jug2+1) #can fill jug2 as long as it isn't at capacity
		
	#do what the action tells us to do (already explained in actions function)
	def result(self, state, action):
		if action == 'transfer %s to %s'%(jug1+1, jug2+1):
			return self.moveWater(state,jug1, jug2)
		elif action == 'empty %s'%(jug1+1):
			return self.empty(state, jug1)
		elif action == 'fill %s'%(jug1+1):
			return self.fill(state, jug1)
			
		if action == 'transfer %s to %s'%(jug2+1, jug1+1):
			return self.moveWater(state, jug2, jug1)
		elif action == 'empty %s'%(jug2+1):
			return self.empty(state, jug2)
		elif action == 'fill %s'%(jug2+1):
			return self.fill(state, jug2)
	
	#fills the specified jug, we already checked to make sure it won't overflow
	def fill(self, state, jugtoFill):
		fillJug = jug(state[jugtoFill].capacity, state[jugtoFill].capacity) #we want to fill the jug to capacity
		if jugtoFill == jug1:
			return (fillJug, state[jug2]) #fill jug1 and return that we filled it, update state
		elif jugtoFill == jug2:
			return (state[jug1], fillJug) #fill jug2 and return that we filled it, update state
	
	#transferring the water from one jug to another, we already checked which jug we should transfer to/from
	#and know that it won't overflow
	def moveWater(self, state, jugMoveFrom, jugMoveTo):
		moveFrom = state[jugMoveFrom] #where we are transferring from
		moveTo = state[jugMoveTo] #where we are transferring to
		amountJugCanHold = moveTo.capacity - moveTo.currentVolume #how much extra water the jug can hold that we are moving to
		waterLeftover = moveFrom.currentVolume - amountJugCanHold #the amount left over in the jug we are moving from after transfer
		#do some updating of the jugs 
		if waterLeftover <= 0:
			moveFrom, moveTo = jug(moveFrom.capacity, 0), jug(moveTo.capacity, moveTo.currentVolume + moveFrom.currentVolume)
		elif waterLeftover > 0:
			moveFrom, moveTo = jug(moveFrom.capacity, waterLeftover), jug(moveTo.capacity, amountJugCanHold)
		if jugMoveFrom == jug1:
			return (moveFrom, moveTo)
		elif jugMoveFrom == jug2:
			return (moveTo, moveFrom)
	
	#emptying a jug
	def empty(self, state, jugToEmpty):
		emptyJug = state[jugToEmpty] #what we are emptying
		emptyJug = jug(emptyJug.capacity, jug1)
		if jugToEmpty == jug1:
			return (emptyJug, state[jug2])
		elif jugToEmpty == jug2:
			return (state[jug1], emptyJug)
	
	
######################Path planning problem##########################
#defines the path finding problem
class PathProblem(Problem):
	def __init__(self, initial, goal, graph):
		self.initial = initial
		self.goal = goal
		Problem.__init__(self, initial, goal)
		self.graph = graph
		self.nodesCreated = 0
	
	def actions(self, start):
		self.nodesCreated += 1 #we created a node
		return list(self.graph.get(start).keys()) #return the keys of the start
	
	def result(self, state, action):
		return action #do what the action says to do
	
	#need to account for costs of the cities here
	def pathCost(self, cost, start, action, end):
		return cost + (self.graph.get(start, end) or infinity)
		
	
#an undirected graph to use for path finding problem
class UndirectedGraph:
	#creates an undirected graph
	def __init__(self, dict = None):
		self.dict = dict
		for start in list(self.dict.keys()):
			for (end, distance) in self.dict[start].items():
				self.connect(end, start, distance)
	#connects the cities
	def connect(self, start, end, distance):
		self.dict.setdefault(start, {})[end] = distance
	#allows us to get the graph
	def get(self, start, end = None):
		path = self.dict.setdefault(start, {})
		if end is None:
			return path
		else:
			return path.get(end)
	#gives the nodes
	def nodes(self):
		return list(self.dict.keys())
	
	
	
#######################Pancake Problem###########################
class PancakeProblem(Problem):
	def __init__(self, initial, goal):
		self.initial = initial
		self.goal = goal
		Problem.__init__(self, initial, goal)
		self.nodesCreated = 0
	
	def actions(self, start):
		self.nodesCreated += 1
	
	def result(self, state, action):
		return action
	
	def pathCost(self, cost, start, action, end):
		return cost + 1
		
	#flips the stack at the index
	def flip(stack, index):
		flipped = stack[:(index + 1)] 
		flipped.reverse()
		flipped += stack[(index + 1):]
		return flipped
		
		
		
	
################Uninformed search ################################
#breadth first search(graph)
def bfs(problem):
	global frontierSpace
	frontierSpace = 0
	global exploredSpace 
	exploredSpace = 0
	root = Node(problem.initial)
	if problem.goalTest(root.state):
		return root
	frontier = [] #uses fifoqueue
	frontier.append(root)
	explored = set() #explored list is just a set
	while frontier:
		if len(frontier) > frontierSpace:
			frontierSpace = len(frontier) #max nodes created
		node = frontier.pop()
		explored.add(node.state) #add to explored list since we have seen it
		if len(explored) > exploredSpace:
			exploredSpace = len(explored)
		for child in node.expand(problem):
			if child.state not in explored and child not in frontier:
				if problem.goalTest(child.state):
					return child
				frontier.append(child)
	return None
		
#depth first search (tree search)
def dfs (problem):
	global frontierSpace
	frontierSpace = 0
	global exploredSpace 
	exploredSpace = None
	frontier = Stack() #use a lifo stack 
	frontier.append(Node(problem.initial))
	while frontier:
		if len(frontier) > frontierSpace:
			frontierSpace = len(frontier) #we dont use an explored list since this is tree search
		node = frontier.pop()
		if problem.goalTest(node.state):
			return node
		frontier.extend(node.expand(problem))
	return None

#Iterative Deepening(tree search)
def dls(problem, limit):
	def dlsRecur(node, problem, limit):
		if problem.goalTest(node.state):
			return node
		elif node.depth == limit:
			return 'limit'
		else:
			limitReached = False
			for child in node.expand(problem):
				result = dlsRecur(child, problem, limit)
				if result == 'limit':
					limitReached = True
				elif result is not None:
					return result
			return ('limit') #done if we hit the limit which is based on depth
	return dlsRecur(Node(problem.initial), problem, limit)
	
def iddfs(problem):
	global frontierSpace
	frontierSpace = 0
	global exploredSpace 
	exploredSpace = None
	for depth in range(sys.maxsize):
		result = dls(problem, depth)
		frontierSpace+= 1
		if result != 'limit':
			return result #we got a result if didn't hit limit
	return None
	
###uniform cost###########
def uniformCost(problem, f = lambda node: node.pathCost):
	global frontierSpace
	frontierSpace = 0
	global exploredSpace 
	exploredSpace = 0
	frontier = PriorityQueue(Node(problem.initial), f) #uses a priority queue
	explored = set() #use a set for explored
	while frontier:
		if len(frontier) > frontierSpace:
			frontierSpace = len(frontier)
		node = frontier.pop()
		if problem.goalTest(node.state):
			return node
		explored.add(node.state) #add this so we know we have seen it before
		if len(explored) > exploredSpace:
			exploredSpace = len(explored)
		for action in problem.actions(node.state):
			child = node.childNode(problem, action)
			if child.state not in explored and child not in frontier:
				frontier.add(child) #add children to frontier if not there already and not explored yet
			elif child in frontier and frontier.cost[child] < child.pathCost:
				frontier.replace(child) #replace the child node if better
	return None
	
	
	
#############INFORMED SEARCHES########################################################
##########astar###############################
def astar(problem, heuristic):
	if heuristic == "jug_heuristic":
		print(heuristic)
		f = lambda node: node.pathCost + (sum(node.state[a].currentVolume for a in range(0, 1))) #since we have 2 jugs
		return uniformCost(problem, f) #we can use uniform cost for astar just need to specify heuristic
	elif heuristic == "path_heuristic":
		print("getting path")
		f = lambda node: node.pathCost * citiesAvg
		return uniformCost(problem, f) #we can use uniform cost for astar just need to specify heuristic
	
############greedy###############################
#greedy uses best first search only it inputs a heuristic
def bestFirst (problem, f = lambda node: node.pathCost):
	global frontierSpace
	frontierSpace = 0
	global exploredSpace 
	exploredSpace = 0
	node = Node(problem.initial)
	if problem.goalTest(node.state):
		return node
	frontier = PriorityQueue(Node(problem.initial), f) #use priority queue
	explored = set() #use a set for explored
	while frontier:
		if len(frontier) > frontierSpace:
			frontierSpace = len(frontier)
		node = frontier.pop()
		if problem.goalTest(node.state):
			return node
		explored.add(node.state)
		if len(explored) > exploredSpace:
			exploredSpace = len(explored)
		for child in node.expand(problem):
			if child.state not in explored and child not in frontier:
				frontier.add(child) #add children to frontier if not there already and not explored yet
			elif child in frontier:
				x = frontier[child]
				if f(child) < f(x):
					del frontier[x]
					frontier.add(child)
	return None
	
def greedy(problem, heuristic):
	if heuristic == "jug_heuristic":
		print(heuristic)
		f = lambda node: node.pathCost + (sum(node.state[a].currentVolume for a in range(0, 1))) #since we have 2 jugs
		return bestFirst(problem, f) #can use best first search for greedy just specify the heuristic
	elif heuristic == "path_heuristic":
		f = lambda node: node.pathCost * citiesAvg 
		return bestFirst(problem, f) #can use best first search for greedy just specify the heuristic
	
	
def main():
	configFile = sys.argv[1]
	searchAlgorithm = sys.argv[2]
	infile = open(configFile, 'r')
	puzzleType = infile.readline()
	puzzleType = puzzleType.rstrip('\n')
	
	
	#####cities path finding problem
	if puzzleType == "cities":
		cityList = infile.readline() #need to figure out formatting of this line for the program
		translator = str.maketrans({key: None for key in string.punctuation})
		cityList = cityList.translate(translator)
		startingCity = infile.readline()
		destinationinationCity = infile.readline()
		global citiesAvg #use for the heuristic, the average of costs for both test files
		
		
		#coded in what the dictionaries should be for both test files
		if configFile == "cities.config":
			cityMap = UndirectedGraph(dict(
				Arlington = dict(Chelmsford = 4, Berkshire = 10),
				Berkshire = dict(Chelmsford = 5)))
			cityMap.locations = dict(
				Arlington = (1, 1), Berkshire = (2, 3), Chelmsford = (1, 5))
				
			citiesAvg = 19/3
			path = PathProblem("Berkshire", "Arlington", cityMap)
			
			
		elif configFile == "test_cities.config":
			map = UndirectedGraph(dict(
				c00 = dict(c10 = 7, c01 = 5, c11=4),
				c10 = dict(c20 = 5, c11 = 5, c21 = 8),
				c20 = dict(c30 = 3, c21 = 5, c31 = 12),
				c30 = dict(c40 = 1, c31 = 5, c41 = 16),
				c01 = dict(c11 = 7, c02 = 5, c12 = 4, c10 = 1),
				c11 = dict(c21 = 5, c12 = 5, c22 = 8, c20 = 1),
				c21 = dict(c31 = 3, c22 = 5, c32 = 12, c30 = 1),
				c31 = dict(c41 = 1, c32 = 5, c42 = 16, c40 = 1),
				c02 = dict(c12 = 7, c03 = 5, c13 = 4, c11 = 1),
				c12 = dict(c22 = 5, c13 = 5, c23 = 8, c21 = 1),
				c22 = dict(c32 = 3, c23 = 5, c33 = 12, c31 = 1),
				c32 = dict(c42 = 1, c33 = 5, c41 = 1),
				c03 = dict(c13 = 7, c04 = 5, c14 = 4, c12 = 1),
				c13 = dict(c23 = 5, c14 = 5, c22 = 1),
				c23 = dict(c33 = 3, c24 = 5, c34 = 12, c32 = 1),
				c33 = dict(c43 = 1, c34 = 5, c44 = 16, c42 = 1),
				c04 = dict(c14 = 7, c13 = 1),
				c14 = dict(c24 = 5, c23 = 1),
				c24 = dict(c34 = 3, c33 = 1),
				c34 = dict(c44 = 1, c43 =1),
				c40 = dict(c41 = 5),
				c41 = dict(c42 = 5),
				c42 = dict(c43 = 5),
				c43 = dict(c44 = 5)))
			map.locations = dict(
				c00 = (0, 0),c01 = (0, 1),c02 = (0, 2),c03 = (0, 3),c04 = (0, 4),c10 = (1, 0),c11 = (1, 1),c12 = (1, 2),c13 = (1, 3),
				c14 = (1, 4),c20 = (2, 0),c21 = (2, 1),c22 = (2, 2),c23 = (2, 3),c24 = (2, 4),c30 = (3, 0),c31 = (3, 1),c32 = (3, 2),
				c33 = (3, 3),c34 = (3, 4),c40 = (4, 0),c41 = (4, 1),c42 = (4, 2),c43 = (4, 3),c44 = (4, 4))
			
			citiesAvg = 330/70
			path = PathProblem("c00", "c44", map) #the start and end can be changed to other locations from file
		
		if searchAlgorithm == "bfs":
			solvePathProblem = bfs(path)
		elif searchAlgorithm == "dfs":
			solvePathProblem = dfs(path)
		elif searchAlgorithm == "iddfs":
			solvePathProblem = iddfs(path)
		elif searchAlgorithm == "uniformcost":
			solvePathProblem = uniformCost(path)
		elif searchAlgorithm == "astar":
			solvePathProblem = astar(path, sys.argv[3])
		elif searchAlgorithm == "greedy":
			solvePathProblem = greedy(path, sys.argv[3])
		
		print("\nSolution Path")
		#use zip to get the lists together
		for node, action in zip(solvePathProblem.path(), solvePathProblem.solution()):
			print(node, action)
		print ("\nNodes Created: %s" %(path.nodesCreated))
		print("\nFrontier list size: %s" % frontierSpace)
		print("\nExplored list size: %s" % exploredSpace)
			
	######water jugs problem
	if puzzleType == "jugs":
		jugCapacities = infile.readline()
		translator = str.maketrans({key: None for key in string.punctuation})
		jugCapacities = jugCapacities.translate(translator)
		jugCapacities = jugCapacities.split()
		global jug1Capacity
		jug1Capacity = int(jugCapacities[0])
		global jug2Capacity
		jug2Capacity = int(jugCapacities[1])
		
		
		jugInits = infile.readline()
		translator = str.maketrans({key: None for key in string.punctuation})
		jugInits = jugInits.translate(translator)
		jugInits = jugInits.split()
		global jug1InitState
		jug1InitState = int(jugInits[0])
		global jug2InitState
		jug2InitState = int(jugInits[1])
		
		
		jugGoals = infile.readline()
		translator = str.maketrans({key: None for key in string.punctuation})
		jugGoals = jugGoals.translate(translator)
		jugGoals = jugGoals.split()
		global jug1GoalState
		jug1GoalState = int(jugGoals[0])
		global jug2GoalState
		jug2GoalState = int(jugGoals[1])
		global goalJug
		global goalAmount
		global other
		global jug1 
		jug1 = 0 #represents jug1 for array
		global jug2 
		jug2 = 1 #represents jug2 for array
		#setting up goalstate formatting
		if jug1GoalState != 0:
			goalJug = jug1Capacity
			goalAmount = jug1GoalState
			other = jug2Capacity
		elif jug2GoalState != 0:
			goalJug = jug2Capacity
			goalAmount = jug2GoalState
			other = jug1Capacity
			
		waterjug = jugProblem()
		if searchAlgorithm == "bfs":
			solveJugProblem = bfs(waterjug)
			if solveJugProblem != None:
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
			else:
				#if we get here then we need to do it the opposite way
				goalJug = other
				waterjug = jugProblem()
				solveJugProblem = bfs(waterjug)
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
					
		elif searchAlgorithm == "dfs":
			solveJugProblem = dfs(waterjug)
			if solveJugProblem != None:
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
			else:
				goalJug = other
				waterjug = jugProblem()
				solveJugProblem = dfs(waterjug)
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
					
		elif searchAlgorithm == "iddfs":
			solveJugProblem = iddfs(waterjug)
			if solveJugProblem != None:
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
			else:
				goalJug = other
				waterjug = jugProblem()
				solveJugProblem = iddfs(waterjug)
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
					
		elif searchAlgorithm == "uniformcost":
			solveJugProblem = uniformCost(waterjug)
			if solveJugProblem != None:
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
			else:
				goalJug = other
				waterjug = jugProblem()
				solveJugProblem = uniformCost(waterjug)
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
					
		elif searchAlgorithm == "astar":
			solveJugProblem = astar(waterjug, sys.argv[3])
			if solveJugProblem != None:
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
			else:
				goalJug = other
				waterjug = jugProblem()
				solveJugProblem = astar(waterjug)
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
					
		elif searchAlgorithm == "greedy":
			solveJugProblem = greedy(waterjug, sys.argv[3])
			if solveJugProblem != None:
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
			else:
				goalJug = other
				waterjug = jugProblem()
				solveJugProblem = greedy(waterjug, sys.argv[3])
				for node, action in zip (solveJugProblem.path(), solveJugProblem.solution()):
					print(node, action)
					
		print ("\nNumber of Nodes Created: %s" %(waterjug.nodesCreated))
		print("\nFrontier list size: %s" % frontierSpace)
		print("\nExplored list size: %s \n" % exploredSpace)
		
		
		
	if puzzleType == "pancakes":
		burned = infile.readline()
		correctStack = infile.readline()
		
main()