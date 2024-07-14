package generator.random.cliqueCopying;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import core.tools.Combination;

public class CliqueCopyingModel {
	
	private static Random random = new Random();
	private static HashMap<Integer, LinkedList<Integer>> kcliquesColl = new HashMap<Integer, LinkedList<Integer>>();
	private static HashMap<Integer, LinkedList<Integer>> k_1_CliquesColl = new HashMap<Integer, LinkedList<Integer>>();
	private static LinkedList<Integer> kClique = new LinkedList<Integer>();
	private static LinkedList<Integer> k_1_Clique = new LinkedList<Integer>();
	private static int cliquesNum = 0;

	/**
	 * MODEL C: Clique Copying Model
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan.
	 * @date March 10, 2016
	 * 
	 * 	Model Definition:
	 * 	1)	Initially, start with an arbitralily signed and directed clique of size $k+1$.
	 * 	2) 	At each time $t> k+1$, we add a new vetex to the network.
	 * 	3) 	At the time step $t+1$, the new vertex $v_{t+1}$ enters the network $G^k_t$  and 
	 * 		(a)	selects u.a.r a $k$ clique $\mathcal{C}_t$, which to be copied, from $G^k_t$,
	 * 		(b)	select u.a.r a $k-1$ clique from the selected $k$-clique, 
	 * 		(c) copy the selected $k$-clique by connecting all vertices from the $k-1$-clique randomly selected from $\mathcal{C}_t$ 
	 * 			the with $v_{t+1}$.
	 *   
	 * @param N - the totoal number of the vertices
	 * @param k - the size of $k$-clique to be copied
	 * 
	 * Dependency: jGrapht graph package from 'http://jgrapht.org/'
	 **/
	
	public static DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> getRandomNetwork(int N, int k, String outputFile) {
		DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = 
				new DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		try {
			//output file writer
			PrintWriter writer = new PrintWriter (outputFile);
			
			//CSV file data heading
			writer.print("Source"); writer.print(","); writer.print("Target"); writer.print(","); writer.print("Sign"); writer.print("\n");
			
			//Adding intial nodes to the graph
			int[] vArray = new int[k+1];  
			for (int i=1; i<=k+1; i++) {
				g.addVertex(i);
				vArray[i-1] = i;
			}
			
			int n = vArray.length; //number of initial vertices array

			//Tracking degree of each N vertices
			double[] posInDeg = new double[N+2];
			double[] negInDeg = new double[N+2];
			double[] posOutDeg = new double[N+2];
			double[] negOutDeg = new double[N+2];

			//add initial edges to the graph
			for (int i=1; i<=k+1; i++) {
				for (int j=i+1; j<=k+1; j++) {	
					double rnd_1 = random .nextDouble();
					//select sign and direction between i & j u.a.r 
					if (rnd_1 < 0.25) {	//add a positive edge (i->j)					
						g.setEdgeWeight(g.addEdge(i, j), 1);
						posOutDeg[i] +=1; posInDeg[j] +=1;
						writer.print(i); writer.print(","); writer.print(j); writer.print(","); writer.print(1); writer.print("\n");
					} else if (rnd_1 < 0.50){ //add a negative edge (i->j)
						g.setEdgeWeight(g.addEdge(i, j), -1);
						negOutDeg[i] +=1; negInDeg[j] +=1;
						writer.print(i); writer.print(","); writer.print(j); writer.print(","); writer.print(-1); writer.print("\n");
					} else if (rnd_1 < 0.75){ //add a positive edge (j->i)
						g.setEdgeWeight(g.addEdge(j, i), 1);
						posOutDeg[j] +=1; posInDeg[i] +=1;
						writer.print(j); writer.print(","); writer.print(i); writer.print(","); writer.print(1); writer.print("\n");
					} else { //add a negative edge (j->i)
						negOutDeg[j] +=1; negInDeg[i] +=1;
						g.setEdgeWeight(g.addEdge(j, i), -1);
						writer.print(j); writer.print(","); writer.print(i); writer.print(","); writer.print(-1); writer.print("\n");
					}
				}
			}
			//Print: initial nework
			System.out.println("Initial edges: ");
			for (DefaultWeightedEdge e: g.edgeSet()) {
				System.out.println(e + "; " + g.getEdgeWeight(e));
			}
			
			//initial cliques
			kcliquesColl = Combination.getCombinationWithUpdatedKey(vArray,n, k, cliquesNum);					//number of existing k-cliques in graph
			cliquesNum = k+1;			//# of initial cliques 

			/*
			//Print: initial k-cliques collection
			System.out.println("Print initial cliques: ");
			System.out.println("Initial cliques size : "+ kcliquesColl.size());
			System.out.println("Initial cliques: " + kcliquesColl);
			*/
			
			//adding new vertex to the network inductively 
			for (int vNew=k+2; vNew<=N; vNew++) {
				//Print: the new vertex entering to the network
				//System.out.println("\nNew vertex vNew: " + vNew);
				
				//add new vertex to the network
				g.addVertex(vNew);
				
				//selecting a k-clicque u.a.r from existing vertices
				int rnd_2 = random.nextInt(cliquesNum);  			//uniform randomly generate a integer key for selecting k-clique
				kClique= kcliquesColl.get(rnd_2); 						//selected k-clique that to be connected with new vertex i

				/*
				//Print: the selected k-clique
				System.out.println("The selected k-clique: ");
				System.out.println("kye: " + rnd_2);
				System.out.print("k-clique: ");
				for (int j=0; j<kClique.size(); j++) {
					System.out.print(kClique.get(j) + " ");
				} System.out.println();
				
				//Print: 'cliques' collection after selecting a k-clique
				System.out.println("Print 'cliques' after selecting k-clique: ");
				System.out.println("Cliques size (after selecting k-clique): " + kcliquesColl.size());
				System.out.println("Cliques (after selecting k-clique): " + kcliquesColl);
				*/
				
				//collecting all (k-1)-cliques in the selected k-cliques
				//HashMap<Integer, LinkedList<Integer>> k_1_CliquesColl = new HashMap<Integer, LinkedList<Integer>>();
				int[] kCliqueArray = new int[kClique.size()];  	//create an array to store the vertices in the selected k-clique
				for (int i=0; i<kClique.size(); i++) {
					kCliqueArray[i] = kClique.get(i); 			//store the vertices from the k-clique to the kCliqueArray
				}
				
				k_1_CliquesColl = Combination.getCombinationNoUpdatedKey(kCliqueArray, kCliqueArray.length, k-1, cliquesNum);
				
				//Print: (k-1)-cliques collection in the selected k-clique
				//System.out.println("All (k-1)-Cliques collection in selected k-clique: " + k_1_CliquesColl);
				
				//selecting a (k-1)-clique from the selected k-clique
				int rnd_3 = random.nextInt(k_1_CliquesColl.size());
				k_1_Clique = k_1_CliquesColl.get(rnd_3);
				
				/*
				//Print: the selected (k-1)-clique from the selected k-cliqued
				System.out.print("(k-1)-clique: " );
				for (int j=0; j<k_1_Clique.size(); j++) {
					System.out.print(k_1_Clique.get(j) + " ");
				} System.out.println("; key :" + rnd_3);
				*/
				
				//the vertex, which need to be copied 
				int v = 0;
				for (int j=0; j<kCliqueArray.length; j++) {
					if (!k_1_Clique.contains(kCliqueArray[j])) {
						v = kCliqueArray[j];
						//System.out.println("v: " + v);
					}
				}
				
				//copy the edges between 'v' and the vertices in (k-1)-clique
				//i.e. add new edge by replacing 'v' by 'vNew'
				for (int j: k_1_Clique) { //for all vertices in selected k_1_clique
					if (g.containsEdge(g.getEdge(v, j))) {
						double sign = g.getEdgeWeight(g.getEdge(v, j));
						//add the new edge 
						g.setEdgeWeight(g.addEdge(vNew, j), sign);
						//write the new edge
						writer.print(v); writer.print(","); writer.print(j); writer.print(","); writer.print((int) sign); writer.print("\n");
						//Print: copying edge 
						//System.out.println("Copying edge: " + v +", " + j + ", " + sign);
						//Print: the new edge
						//System.out.println("New edge: " + vNew + ", " + j + ", " + sign);
					} else if (g.containsEdge(g.getEdge(j, v))) {
						double sign = g.getEdgeWeight(g.getEdge(j,v));
						//add the new edge 
						g.setEdgeWeight(g.addEdge(j, vNew), sign);
						//write the new edge
						writer.print(j); writer.print(","); writer.print(v); writer.print(","); writer.print((int) sign); writer.print("\n");
						//Print: copying edge 
						//System.out.println("Copying edge: " + j +", " + v + ", " + sign);
						//Print: the new edge
						//System.out.println("New edge: " + j + ", " + vNew + ", " + sign);
					}
				}
				
				
				/*
				//Print: updated network (after adding new clique)
				System.out.println("\nUpdated network (after adding new clique: ");
				for (DefaultWeightedEdge e: g.edgeSet()) {
					System.out.println(e + ", " + g.getEdgeWeight(e));
				}
				*/
				
				//updating k-cliques 
				k_1_Clique.add(vNew);
				kcliquesColl.put(cliquesNum, k_1_Clique);
				cliquesNum = cliquesNum + 1;
				
				//Print: updated k-cliques collection
				//System.out.println("k-Cliques collection: " + kcliquesColl);
				
				
			}
			
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return g;
	}
	
	/**
	 * main method
	 */
	 
	public static void main(String [] args)
	{	
		DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = null;
		Scanner input = new Scanner(System.in);
		
		//input network information
		System.out.print("Enter number of vertices N = ");
		int N = input.nextInt();

		System.out.print("Enter parameter k = ");
		int k = input.nextInt();

		String outputFile = "data/RandomSignedNetwork_Model_C.csv";
		g = CliqueCopyingModel.getRandomNetwork(N, k, outputFile);

		//Printing network information
		System.out.println("\nNetwork info: ");
		//System.out.println("\nEdges: " + g.edgeSet());
		System.out.println("Number of vertices: " + g.vertexSet().size());
		System.out.println("Number of Edges: " + g.edgeSet().size());
		input.close();
	}
}
