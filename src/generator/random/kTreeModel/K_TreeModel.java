package generator.random.kTreeModel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import core.tools.Combination;

public class K_TreeModel {
	
	/**
	 * 
	 * MODEL D: k Tree Random Model
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan.
	 * 
	 **/

	private static Random random = new Random();
	private static HashMap<Integer, LinkedList<Integer>> cliques = new HashMap<Integer, LinkedList<Integer>>();
	private static HashMap<Integer, LinkedList<Integer>> newCliques = new HashMap<Integer, LinkedList<Integer>>();
	private static LinkedList<Integer> kClique = new LinkedList<Integer>();
	private static int cliquesNum = 0;


	/**
	 * MODEL D: k Tree Random Model
	 * 
	 * Model Definition:
	 * 1) Initially, start with a clique of size k+1, in which edges are arbitrarily signed and directed
	 * 2) At each time t > k+1, add a vertex v_t to the network
	 * 3) New vertex v_t+1 selects a k-clique u.a.r from G^k_t. Then the probability of selecting an existing vertex v 
	 *    to be connected with v_t+1:
	 * 	
	 * 		P[v connect to v_t+1] = (a_k d(v) - b_k)/ (c_k t)
	 * 
	 *    where a_k = k-1, b_k = k(k-2), c_k = k - (k^2 -1)/t and d(v) is the total degree of v in G^k_t.
	 *    
	 * 4) The sign and direction determine by following:
	 * 		
	 * 		P[positive edge from v_t+1 to v] = d+in(v) / d(v)    
	 * 		P[negative edge from v_t+1 to v] = d-in(v) / d(v)
	 * 		P[positive edge from v to v_t+1] = d+out(v) / d(v)
	 * 		P[negative edge from v to v_t+1] = d-out(v) / d(v)
	 * 	  
	 *    where d+in(v) - positive-in-degree of v
	 *    		d-in(v) - negative-in-degree of v
	 *    		d+out(v) - positive-out-degree of v
	 *    		d-out(v) - negative-out-degree of v
	 *    
	 * @param N - number of vertices
	 * @param k - number of new connection added by entering vertex
	 * @retrun Signed Directed Random Network
	 * 
	 * Dependency: jGrapht, 'http://jgrapht.org/'
	 * 
	 **/
	public static DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> getRandomNetwork(int N, int k, String outputFile) {
		DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = 
				new DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		try {
			//output file writer
			PrintWriter writer = new PrintWriter (outputFile);

			//CSV file data heading
			writer.print("Source"); writer.print(","); writer.print("Target"); writer.print(","); writer.print("Sign"); writer.print("\n");


			/**Initial network**************************/
			//	list of vertice visited
			Set<Integer> verticesList = new HashSet<Integer>();
			int nxtV= 0;
			int nxtV_temp = 0;
			int step = 0;
			int pOrN_flag = 0;
			int firstPorNegNrb = 0; // to make sure first selection is negative-in-nbr

			//Tracking degree of each N vertices
			double[] posInDeg = new double[N+2];
			double[] negInDeg = new double[N+2];
			double[] posOutDeg = new double[N+2];
			double[] negOutDeg = new double[N+2];

			// Setp-01: add intial vertices to the graph
			int k0 = 2*k + 1;
			int[] vArray = new int[k0+1];
			for (int i=1; i<=k0; i++) {
				g.addVertex(i);
				verticesList.add(i);
				vArray[i-1] = i;
			}
			int n = vArray.length; //number of initial vertices array

			//	Step-02: connect v1 with all other vertices as d+in(v1) = 2k-1 and d-in(v1)=1
			step = 2*k-1;
			g.setEdgeWeight(g.addEdge(2, 1), -1); 	 //	d-in(v1)=1
			writer.print(2); writer.print(","); writer.print(1); writer.print(","); writer.print(-1); writer.print("\n");
			negOutDeg[2] = 1; negInDeg[1] =1;
			nxtV = 2;				// negative-in-nbr of v1 which will be the next visiting vetex

			for (int i=3; i<=k0; i++) { 			 //	d+in(v1) = 2k-1
				g.setEdgeWeight(g.addEdge(i, 1), 1);
				writer.print(i); writer.print(","); writer.print(1); writer.print(","); writer.print(1); writer.print("\n");
				posOutDeg[i] = posOutDeg[i] + 1;
				posInDeg[1] = posInDeg[1] + 1;	
			}
			verticesList.remove(1);	// mark v1 is visited

			while (step> 0) {

				verticesList.remove(nxtV); //removing the next selecte vertex from list
				firstPorNegNrb = 0;

				if (pOrN_flag == 0) {   

					for (int i: verticesList) {

						if (firstPorNegNrb==0) {

							g.setEdgeWeight(g.addEdge(i, nxtV), 1);
							writer.print(i); writer.print(","); writer.print(nxtV); writer.print(","); 
							writer.print(1); writer.print("\n");

							posOutDeg[i] = posOutDeg[i] + 1;  
							posInDeg[nxtV] = posInDeg[nxtV] + 1;

							firstPorNegNrb = 1;		// flag that a negative-in-nbr created
							step = step - 1;
						}
						else {
							//System.out.println("firstPorNegNrb: " + firstPorNegNrb);
							g.setEdgeWeight(g.addEdge(i, nxtV), -1);
							writer.print(i); writer.print(","); writer.print(nxtV); writer.print(","); 
							writer.print(-1); writer.print("\n");

							negOutDeg[i] = negOutDeg[i] + 1;  
							negInDeg[nxtV] = negInDeg[nxtV] + 1;
							nxtV_temp = i;				// negative-in-nbr of nxV which will be the next visiting vetex
						}
					}
					nxtV = nxtV_temp;
					pOrN_flag = 1; 

				} else {

					for (int i : verticesList) {

						if (firstPorNegNrb==0){		
							g.setEdgeWeight(g.addEdge(i, nxtV), -1);
							writer.print(i); writer.print(","); writer.print(nxtV); writer.print(","); 
							writer.print(-1); writer.print("\n");

							negOutDeg[i] = negOutDeg[i] + 1;  
							negInDeg[nxtV] = negInDeg[nxtV] + 1;

							nxtV_temp = i;				// negative-in-nbr of nxV which will be the next visiting vetex
							firstPorNegNrb = 1;		// flag that a negative-in-nbr created
							step = step - 1;
						}
						else {				
							g.setEdgeWeight(g.addEdge(i, nxtV), 1);
							writer.print(i); writer.print(","); writer.print(nxtV); writer.print(","); 
							writer.print(1); writer.print("\n");

							posOutDeg[i] = posOutDeg[i] + 1;  
							posInDeg[nxtV] = posInDeg[nxtV] + 1;
						}
					}
					nxtV = nxtV_temp;
					pOrN_flag = 0; 
				}
			}

//			//Adding intial nodes to the graph
//			int[] vArray = new int[k+1];  
//			for (int i=1; i<=k+1; i++) {
//				g.addVertex(i);
//				vArray[i-1] = i;
//			}
//
//			int n = vArray.length; //number of initial vertices array
//
//			//Tracking degree of each N vertices
//			double[] posInDeg = new double[N+2];
//			double[] negInDeg = new double[N+2];
//			double[] posOutDeg = new double[N+2];
//			double[] negOutDeg = new double[N+2];
//
//			//add initial edges to the graph
//			for (int i=1; i<=k+1; i++) {
//				for (int j=i+1; j<=k+1; j++) {	
//					double rnd_1 = random.nextDouble();
//					//select sign and direction between i & j u.a.r 
//					if (rnd_1 < 0.25) {	//add a positive edge (i->j)					
//						g.setEdgeWeight(g.addEdge(i, j), 1);
//						posOutDeg[i] +=1; posInDeg[j] +=1;
//						writer.print(i); writer.print(","); writer.print(j); writer.print(","); writer.print(1); writer.print("\n");
//					}
//					else if (rnd_1 < 0.50){ //add a negative edge (i->j)
//						g.setEdgeWeight(g.addEdge(i, j), -1);
//						negOutDeg[i] +=1; negInDeg[j] +=1;
//						writer.print(i); writer.print(","); writer.print(j); writer.print(","); writer.print(-1); writer.print("\n");
//					}
//					else if (rnd_1 < 0.75){ //add a positive edge (j->i)
//						g.setEdgeWeight(g.addEdge(j, i), 1);
//						posOutDeg[j] +=1; posInDeg[i] +=1;
//						writer.print(j); writer.print(","); writer.print(i); writer.print(","); writer.print(1); writer.print("\n");
//					}	
//					else { //add a negative edge (j->i)
//						negOutDeg[j] +=1; negInDeg[i] +=1;
//						g.setEdgeWeight(g.addEdge(j, i), -1);
//						writer.print(j); writer.print(","); writer.print(i); writer.print(","); writer.print(-1); writer.print("\n");
//					}
//				}
//			}

			//initial cliques
			cliques = Combination.getCombinationWithUpdatedKey(vArray,n, k, cliquesNum);					//number of existing k-cliques in graph
			cliquesNum = k0+1;			//# of initial cliques 

			/*
			//Print:
			System.out.println("Print initial cliques: ");
			System.out.println("Initial cliques size : "+ cliques.size());
			System.out.println("Initial cliques: " + cliques);
			*/
			
			//adding new vertex inductively to the graph
			for (int vNew=k0+1; vNew<=N; vNew++) {
				//print new vertex intering network
				//System.out.println("\n\nNew vertex: " + vNew);

				//selecting a k-clicque u.a.r from existing vertices
				int rnd_3 = random.nextInt(cliquesNum);  			//uniform randomly generate a integer key for selecting k-clique
				kClique= cliques.get(rnd_3); 						//selected k-clique that to be connected with new vertex i

				//Print: the selected k-clique
				/*
				System.out.println("The selected k-clique: ");
				System.out.println("kye: " + rnd_3);
				for (int j=0; j<kClique.size(); j++) {
					System.out.print(kClique.get(j) + " ");
				}
				System.out.println();
				
				
				//Print: 'cliques' collection after romoving selected k-clique
				System.out.println("Printc 'cliques' after selecting k-clique: ");
				System.out.println("Cliques size (after selecting k-clique): " + cliques.size());
				System.out.println("Cliques (after selecting k-clique): " + cliques);
				*/
				
				//connecte each vertex 'v' in k-clique with 'vNew'
				for (int j=0; j<kClique.size(); j++) { //iterate for all elements in k-clique
					//get a vertex for the selected k-clique
					int v = kClique.get(j);    //vertex v from k-clique
					//System.out.println("v_kClique: " + v);
					double totalDeg_v = posInDeg[v] + negInDeg[v] + posOutDeg[v] + negOutDeg[v];
					double p1 = posInDeg[v] / totalDeg_v;
					double p2 = p1 + (negInDeg[v] / totalDeg_v);
					double p3 = p2 + (posOutDeg[v] / totalDeg_v);
					double p4 = p3 + (negOutDeg[v] / totalDeg_v);

					double rnd_4 = random.nextDouble();

					//determine the sign and direction of the edge between 'vNew' and 'v' w.r.t 'v'
					if (rnd_4 <= p1) { //add an edge (vNew, v)+ 
						g.addVertex(vNew);
						g.setEdgeWeight(g.addEdge(vNew, v), 1);
						posOutDeg[vNew] +=1; posInDeg[v] +=1;
						writer.print(vNew); writer.print(","); writer.print(v); writer.print(","); writer.print(1); writer.print("\n");
						//System.out.print("Edge:" + vNew + " + " + v + "\n");
					}
					else if (rnd_4 <= p2) { //add an edge (vNew, v)-
						g.addVertex(vNew);
						g.setEdgeWeight(g.addEdge(vNew, v), -1);
						negOutDeg[vNew] +=1; negInDeg[v] +=1;
						writer.print(vNew); writer.print(","); writer.print(v); writer.print(","); writer.print(-1); writer.print("\n");
						//System.out.print("Edge:" + vNew + " - " + v + "\n");
					}
					else if (rnd_4 <= p3)  { //add an edge (v, vNew)+
						g.addVertex(vNew);
						g.setEdgeWeight(g.addEdge(v,vNew), 1);
						posOutDeg[v] +=1; posInDeg[vNew] +=1;
						writer.print(v); writer.print(","); writer.print(vNew); writer.print(","); writer.print(1); writer.print("\n");
						//System.out.print("Edge:" + v + " + " + vNew + "\n");
					}
					else if (rnd_4 <= p4){                    //add an edge (v, vNew)-
						g.addVertex(vNew);
						g.setEdgeWeight(g.addEdge(v, vNew), -1);
						negOutDeg[v] +=1; negInDeg[vNew] +=1;
						writer.print(v); writer.print(","); writer.print(vNew); writer.print(","); writer.print(-1); writer.print("\n");
						//System.out.print("Edge:" + v + " - " + vNew + "\n");
					}
					else {
						System.out.println("Error");
					}
				} //end-for-loop to make k connection between vertices from selected kClique and vNew


				//Print: Updating the cliques collection
				//System.out.println("\nUpdating cliques:");
				//System.out.println("Cliques (before update): " + cliques);
				
				//creating an array with the vertices from selected k-clique (without vNew) 
				int[] kCliqueArray = new int[kClique.size()]; //first create an array with the vertices of selected k-clique and 'vNew'
				for (int i=0; i<kClique.size(); i++) {
					kCliqueArray[i] = kClique.get(i);
				}


				/*
				//Print: new kCliqueArray print
				System.out.println("kCliqueArray Size: " + kCliqueArray.length);
				for (int i=0; i<kCliqueArray.length; i++) {
					System.out.print(kCliqueArray[i] + " ");
				}
				System.out.println();
				System.out.println("Cliques (before update): " + cliques);
				*/

				//create a 'newCliques' colletion (selected clique + new cliques)
				int k1 = k-1;
				newCliques = Combination.getCombinationWithUpdatedKey(kCliqueArray, kCliqueArray.length, k1, cliquesNum);
				
				//Print: 
				//System.out.println("NewClique(before remove): " + newCliques);
				//System.out.println("Cliques (before update & comb): " + cliques);
				//System.out.println("ELEMENTS IN newCliques: ");
				//System.out.println("newCliques (from kCliqueArray): " + newCliques);
				//System.out.println("Cliques (before update): " + cliques);

				//Print: add 'vNew' to the new k-cliques in the collection 'newCliques
				//System.out.println("Adding vNew " + vNew + " : ");
				for (int p=cliquesNum; p< (cliquesNum+k); p++) {
					kClique = newCliques.get(p);
					kClique.add(vNew);
				}
				//System.out.println("newCliques (after adding vNew): " + newCliques);

				//Print: 'newCliques' after adding vNew
				//System.out.println("newCliques after adding vNew " + vNew + ": ");
				//System.out.println("newCliques size: " + newCliques.size());
				//System.out.println("newCliques : " + newCliques);

				//Print: add all cliques from 'newCliques' and 'cliques' colletion
				//System.out.println("Adding 'newCliques' and 'cliques': ");
				//System.out.println("Cliques: " + cliques);
				//System.out.println("NewCliques: " + newCliques);
				for (int i=cliquesNum; i < (cliquesNum + k); i++) {
					cliques.put(i, newCliques.get(i));
				}
				//Print: Final updating clique
				//System.out.println("Cliques size (after adding): " + cliques.size());
				//System.out.println("Cliques (after adding): " + cliques);
				
				//updated total cliquesNum
				cliquesNum = cliquesNum + k;

			} //add vNew
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return g ;
	}
}
