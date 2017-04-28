
Assignment 1

 Version of python used: Python 3
 
 Running the program: python puzzlesolver.py configfile algorithm heuristic
 To use the heuristic for the water jug problem: heuristic = jug_heuristic 
 To use the heuristic for the path problem: heuristic = path_heuristic
 
 I consulted the textbook and the pseudocode for the searches, the abstraction of a problem, and node class.
 I discussed how to get information from the files with Ish Davis from class and we determined that "translate" would
 be a good option which I used for getting the jug info from the file.

While I have experience with python, I haven’t used it for files until now. I was able to get the information for the jug files into the program when it’s specified. I wasn’t able to get the information for the city files into the right format for a dictionary so I wrote both test file graphs in myself. If the test files test_cities.config or cities.config are specified, the program WILL run for the correct information. I specified the start/end points in the code too so they would need updated to check different start/endpoints in the graph. Since Dr. Hwa said she cared more about the problems/searches than how good the code was, I thought it would be better to spend more time on the problems/searches than the file formatting.

I wasn’t able to get the burnt pancake problem working. I have seen the other two problems before, but not this one which is why I think I had some trouble. I coded a quick regular pancake problem without the problem abstraction and couldn’t get it into the actions/results as well as how to deal with the burned ones. I got the other two problems working for all of the searches. For the jug problem, the last Node printed will say that water is being transferred. In test_jugs.config, for example, it prints Node: (4, 8) transfer 1 to 2 and transferring jug 1 to jug 2 leaves 1 gallon in the first jug and the second jug gets emptied. The goal state gets reached.
