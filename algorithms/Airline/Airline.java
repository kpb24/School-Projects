//Kerilee Bookleiner
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class Airline{
	static ArrayList<String> vertices;
	static int V;
	static Graph airlineGraph;
	static Edge E;
	static PrintWriter fileOutput;
	static String file;
	static String fileName;

	public static void main(String[] args){
	
		//read in file from user
		System.out.println("Enter the file: ");
		boolean correctFile = false;
		while(correctFile == false){
			try{
				Scanner inputFile = new Scanner(System.in);
				fileName = inputFile.next();
				file = fileName;
				File fileOfFLights = new File(file); 
				if(fileOfFLights.exists()){
					correctFile = true; //keep prompting until a file name enters that exists
				}
				try{
					FileReader fr = new FileReader(fileOfFLights);
					BufferedReader br = new BufferedReader(fr);
					V = Integer.parseInt(br.readLine()); //get the number of vertices
	
					vertices = new ArrayList<String>(V); //make the city arraylist the size of the number of vertices
					airlineGraph = new Graph(V); //create the graph
					
					for(int i = 0; i < V; i++){
						vertices.add(i, br.readLine()); //add the cities to the vertices arraylist
					}
					airlineGraph.setVerticesArrayList(vertices); //send the vertices arraylist to the graph 
					String edges;
					
					while((edges = br.readLine()) != null){ //while the line isn't empty
						//information about routes
						String[] splitS = edges.split(" ");
						int vertex = Integer.parseInt(splitS[0]); //vertex
						int weight = Integer.parseInt(splitS[1]); //weight
						double distance = Double.parseDouble(splitS[2]); //price, make double in case cents
						double price = Double.parseDouble(splitS[3]); //distance 
						
						Edge E = new Edge((vertex-1), (weight-1), distance, price); //make an edge with the attributes
						airlineGraph.addEdge(E);
					}
				br.close();
				
				}
				
				catch(IOException e){
					e.printStackTrace();
				
				}
			}
			catch(Exception f){
				f.printStackTrace();
			
			}
		}
	
	
		System.out.println("\nWelcome to Airline\n");
		int menuChoice = 1;
		Scanner input = new Scanner(System.in);
		
		while(menuChoice >= 1 && menuChoice <= 8){
		System.out.println("\n\tPlease choose one of the following:");
		System.out.println("1 Display all routes, distances, and prices");
		System.out.println("2 Display minimum spanning tree");
		System.out.println("3 Display shorted path based on miles from source to destination");
		System.out.println("4 Display shortest path based on price from the source to destination");
		System.out.println("5 Display shortest path based on number of hops from source to destination");
		System.out.println("6 Display affordable trips");
		System.out.println("7 Add route");
		System.out.println("8 Remove route");
		System.out.println("9 Exit");
	
		menuChoice = input.nextInt();
		int s = -1;
		int d = -1;
		String source = "";
		String destination = "";
			
		if(menuChoice == 1){
			System.out.println(airlineGraph.toString());
		}
		
		else if(menuChoice == 2){
			//use prim's algorithm to get minimum spanning tree
			System.out.println(airlineGraph.prim());
		}
		
		//use dijkstra to find shortest distance/ price path
		else if(menuChoice == 3 || menuChoice == 4){
			//keep asking until both are located in there
			while(s < 0 && d < 0){
				System.out.println("Enter the source: ");
				source = input.next();
				System.out.println("Enter the destination");
				destination = input.next();
				s = vertices.indexOf(source); //gives index of -1 if not in there
				d = vertices.indexOf(destination);
				if(s < 0 || d < 0){
					System.out.println("Locations are not in graph. Try again");
				}
			}
			//distance path
			if(menuChoice == 3){
				System.out.println(airlineGraph.dijkstra(s, d, "Destination"));
			}
			//price path
			else if(menuChoice == 4){
				System.out.println(airlineGraph.dijkstra(s, d, "Price"));
			}
		}
	
		
		//use bfs to find shortest hops
		else if(menuChoice == 5){
			while(s < 0 && d < 0){
				System.out.println("Enter the source: ");
				source = input.next();
				System.out.println("Enter the destination");
				destination = input.next();
				s = vertices.indexOf(source);
				d = vertices.indexOf(destination);
				if(s < 0 || d < 0){
					System.out.println("Locations are not in graph. Try again");
				}
			}
			System.out.println(airlineGraph.bfs(s, d));
		}
		
		
		//show routes under certain price
		else if(menuChoice == 6){
			double price;
			System.out.println("Enter your maximum price: ");
			price = input.nextDouble();
			System.out.println("\n");
			airlineGraph.maxPricePaths(price);
		}
		
		
		//add route. source and destination must already be in there
		else if(menuChoice == 7){
			double distance;
			double price;
			while(s < 0 && d < 0){
				System.out.println("Enter the source: ");
				source = input.next();
				System.out.println("Enter the destination");
				destination = input.next();
				s = vertices.indexOf(source);
				d = vertices.indexOf(destination);
				if(s < 0 || d < 0){
					System.out.println("Locations are not in graph. Try again");
				}
			}
			System.out.println("Enter distance: "); //get distance between source and destination
			distance = input.nextDouble();
			System.out.println("Enter price: "); //get price of the flight
			price = input.nextDouble();
			
			E = new Edge(s, d, distance, price); //new edge 
			airlineGraph.addEdge(E);
		}
	
	
		//remove from graph
		else if(menuChoice == 8){
			while(s < 0 && d < 0){
				System.out.println("Enter the source: ");
				source = input.next();
				System.out.println("Enter the destination");
				destination = input.next();
				s = vertices.indexOf(source);
				d = vertices.indexOf(destination);
				if(s < 0 || d < 0){
					System.out.println("Locations are not in graph. Try again");
					}
			}
			E = new Edge(s,d); //make a new edge and remove it
			airlineGraph.removeEdge(E);
	
		}			
	}
	
		
	//overwrite file to save any changes before exiting
	try{
		fileOutput = new PrintWriter(new FileOutputStream(fileName, false)); //overwrite the file
		fileOutput.println(V); //number of verticies at top of file
		for(int i = 0; i < V; i++){
			fileOutput.println(vertices.get(i)); //place all the citites
		}
			ArrayList<Edge> Edges = new ArrayList(V);
			for(int i = 0; i < V; i++){
				for(Edge e : airlineGraph.adj(i)){
					if(!Edges.contains(e)){
						Edges.add(e); //add the edges
						int v = e.either()+1; 
						int w =	e.other((v-1))+1;
						StringBuilder s = new StringBuilder(v + " " + w  + " " + e.distance()  + " " + e.price());
						fileOutput.println(s.toString());
					}
				}
			}
		fileOutput.close(); //will be able to read back in file with the user changes made
	}
	catch(Exception e){
		e.printStackTrace();
	}
	System.out.println("\nThe file " + fileName + " had been updated.");
	System.out.println("Thank you for using Airline. Goodbye.");
	
	}
}