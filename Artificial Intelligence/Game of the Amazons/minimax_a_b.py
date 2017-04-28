import sys


def maxValue(state, alpha, beta):
    visited = 1
    if isinstance(state, tuple):
        name, value = state
        return (value, visited) #if final state
    value = float("-inf")
    for s in successorStates(state):
        minS, visitedMaxVal = minValue(s, alpha, beta)
        visited += visitedMaxVal #visited keeps track of number of nodes seen
        value = max(value, minS)
        if value >= beta:
            return (value, visited)
        else:
            alpha = max(alpha, value)
    return (value, visited)


def minValue(state, alpha, beta):
    visited = 1
    if isinstance(state, tuple):
        name, value = state
        return (value, visited) #if final state
    value = float("inf")
    for s in successorStates(state):
        maxValState, visitedMaxVal = maxValue(s, alpha, beta)
        visited += visitedMaxVal #visited keeps track of number of nodes seen
        value = min(value, maxValState)
        if value <= alpha:
            return (value, visited)
        else:
            beta = min(beta, value)
    return (value, visited)


#returns the sucessor states if there are any
def successorStates(state):
    if isinstance(state, list):
        return state[1:]
    else:
        return None
        

def main():
	f = open(sys.argv[1], "r")
	value, visited = maxValue(eval(f.readline()), float("-inf"), float("inf"))
	print("Utility value of root node: %s" % value)
	print("Nodes visited: %s" % visited)
    
    
    
main()