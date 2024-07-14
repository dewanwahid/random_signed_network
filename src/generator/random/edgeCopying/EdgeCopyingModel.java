package generator.random.edgeCopying;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import core.tools.CountPosNegEdges;

public class EdgeCopyingModel {

	private static Random random = new Random();
	private static int[] posOutDeg;
	private static int[] posInDeg;
	private static int[] negOutDeg;
	private static int[] negInDeg;
	private static HashMap<Integer, int []> edgeCollection = new HashMap<Integer, int []>();
	private static HashMap<Integer, int[]> tempEdgeCollection = new HashMap<Integer, int[]>();
	private static DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> g = 	
			new DirectedWeightedMultigraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	private static int newEdgeCount = 0;
	private static String outputFile = "RSDN-EdgeCopyingModel.csv";

	/**
	 * MODEL B: Edge Copying Model
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan.
	 * 
	 * Model Definition:
	 * 	1) 	Initially, we start with an arbitrarily signed and directed clique of size $k+1$.
	 * 	2) 	At each time $t>k+1$, we add a new vertex $v_t$ to the network.
	 * 	3) 	At the time step $t+1$, the new vertex $v_{t+1}$ enter the network $G^k_t$ and 
	 * 		(a)	select an edge u.a.r to be copied with attributes (sign $&$ directiion).
	 * 		(b) determine either source or target vertex $v$ to be copied with the probabilities $\alpha$ or $1-\alpha$ respectively.
	 * 		i.e. add the new edge either $(v_{t+1}, v)$ or $(v, v_{t+1})$ to the network $G^k_{t+1}$ with the probabilities 
	 * 		$\alpha$ or $1-\alpha$ respectively. 
	 * 	4) 	Repeat step 3 $k$ times. 
	 * 	  
	 * @param N - number of vertices
	 * @param k - number of new edges (connection) added by entering vertex
	 * @retrun Signed Directed Random Network with edge copying property
	 * 
	 * Dependency: jGrapht graph package from 'http://jgrapht.org/'
	 * 
	 **/

	public static DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> getRandomNetwork (int N, int k, double alpha) {

		//create a directed graph object using jGrapht graph 
		//DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> g = 	new DirectedWeightedMultigraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		try {

			/*	Initialization*/
			int edgeNum = 0;		//total number of edges in the current network
			//int newEdgeCount = 0;		//total number of edges added in each step
			int posEdgeCount = 0;		//number of positive edges added to the network so far
			int negEdgeCount = 0;		//number of negative edges added to the network so far

			posOutDeg = new int[N+1];
			posInDeg = new int[N+1];
			negOutDeg = new int[N+1];
			negInDeg = new int[N+1];

			//	Output file writer
			PrintWriter writer = new PrintWriter (outputFile);

			//	csv file data heading
			writer.println("#Data Name: Signed directed network");
			writer.println("#Model generator: Edge copying model");
			writer.println("#Number of vertices: " + N);
			writer.println("#Number of edges: " + ((N-2*k-1)*k + 2*k));
			writer.println("#Parameter k: " + k);
			writer.println("#Parameter alpha: " + alpha);
			writer.println("#Source, Target, Sign"); 


			/* Initial Network******************************************************/	
			//	Add vertices to the initial network
			for (int i=1; i<= (2*k+1); i++) {
				g.addVertex(i);
			}

			//	Add k positive edges to the netwrok
			while (posEdgeCount < k) {

				//	Select two vertices random from {1,2,...,2k+1}
				int src = random.nextInt(2*k+1) + 1;
				int trg = random.nextInt(2*k+1) + 1;

				//	Create a positive edge from src to trg, if there does not exist one
				if (src !=trg
						&& posOutDeg[src] == 0
						&& posInDeg[trg] == 0
						&& !g.containsEdge(src, trg)
						&& !g.containsEdge(trg, src)) {

					//	add  positive edge (src, trg) to network
					g.setEdgeWeight(g.addEdge(src, trg), 1);

					// 	add edge to the edgeCollection
					int [] e = new int[3];
					e[0]= src; e[1]= trg; e[2]= 1;	//edge store in edgeCollection as an arry [src, trg, sign]
					edgeCollection.put(edgeNum, e); //edgeNum is the key for every individual edge

					//	write new edge to csv data file
					writer.println(src +"," + trg +"," + 1);				
					System.out.println(src +"," + trg +"," + 1);

					//	updating the signed degrees of src and trg
					posOutDeg[src] += 1; posInDeg[trg] += 1;

					//	updating the edgeNum and posEdges
					edgeNum++; posEdgeCount++;

				}
			}

			//	Add k negative edges to the network
			while (negEdgeCount < k) {

				//	Select two vertices randomly from {1,2, ..., 2k+1}
				int src = random.nextInt(2*k+1) + 1;
				int trg = random.nextInt(2*k+1) + 1;

				// Create a negative edge from src to trg, if there already does not one
				if (src != trg
						&& negOutDeg[src] == 0
						&& negInDeg[trg] == 0
						&& !g.containsEdge(src, trg)
						&& !g.containsEdge(trg, src)) {

					//	Add  negative edge (src, trg) to network
					g.setEdgeWeight(g.addEdge(src, trg), -1);

					// 	Add edge to the edgeCollection
					int [] e = new int[3];
					e[0]= src; e[1]= trg; e[2]= -1;	//edge store in edgeCollection as an arry [src, trg, sign]
					edgeCollection.put(edgeNum, e); //edgeNum is the key for every individual edge

					//	Write new edge to csv data file
					writer.println(src +"," + trg +"," + -1);
					System.out.println(src +"," + trg +"," + -1);

					// 	Updating signed degree for src and trg
					negOutDeg[src] += 1; negInDeg[trg] += 1;

					//	Updating the edgeNum and posEdges
					edgeNum++; negEdgeCount++;
				}
			}

			//	Print: Initial edge collection
			System.out.println("\nInitial edge collection: ");
			System.out.println("edgeNum: " + edgeNum);
			for (int l=0; l<edgeNum; l++) {
				int [] e = edgeCollection.get(l);
				System.out.print(l+" = [");
				for (int m=0; m<3; m++) {
					System.out.print (e[m] + " ");
				}
				System.out.print("]; ");
			}
			System.out.println();


			//	Track all edges by the new vertex in a time step. 
			//		To make sure each step the new vertex copies k distinct edges 
			Collection<Integer> copiedEdgesKeys = new HashSet<Integer>();

			//	Track all vertices connecte with the new vertex in a time step
			//Collection<Integer> connectListWithNewVertex = new HashSet<Integer>();



			int src;
			int trg;
			int sign;

			//	Sets of vertices that receive particula signed degree
			Collection<Integer> tempPosOutDeg = new HashSet<Integer> ();
			Collection<Integer> tempPosInDeg = new HashSet<Integer> ();
			Collection<Integer> tempNegOutDeg = new HashSet<Integer> ();
			Collection<Integer> tempNegInDeg = new HashSet<Integer> ();


			/**	Add 'k' new edge to the network for each new vertex 'vNew' ***************************/	
			for (int vNew = (2*k+2); vNew <= N; vNew++) {

				System.out.println("\nNew vertex: " + vNew);
				//	Add the new vertex to network
				g.addVertex(vNew);

				//	Number of new edges have to add in this step
				newEdgeCount = 0;

				//	Clear the key list of copied edges from previous step
				copiedEdgesKeys.clear();
				//connectListWithNewVertex.clear();

				//	Clear the list of vertices that received a particular signed degree
				tempPosOutDeg.clear(); tempPosInDeg.clear(); tempNegOutDeg.clear(); tempNegInDeg.clear();

				//	Clear the temporary (new) edges collection from previous step
				tempEdgeCollection.clear();

				//	Print: Temporary edge collection in this step
				System.out.println("\nPrint tempEdgeCollection (before): ");
				System.out.println("newEdgeCount (before): " + newEdgeCount);
				for (int l=0; l<newEdgeCount; l++) {
					int [] eTemp = tempEdgeCollection.get(l);
					System.out.print(l + "= [");
					for (int m=0; m<3; m++) {
						System.out.print (eTemp[m] + " ");
					}
					System.out.print("]; ");
				}
				System.out.println();
				System.out.println("tempPosOutDeg (before): " + tempPosOutDeg);
				System.out.println("tempPosInDeg (before): " + tempPosInDeg);
				System.out.println("tempNegOutDeg (before): "+ tempNegOutDeg);
				System.out.println("tempNegInDeg (before): " + tempNegInDeg);

				//	Add k edges for each new vertex,
				//	 	i.e. continue loop if the number of newly added edges less that 'k'
				while (newEdgeCount < k) {

					//	select an existing edge u.a.r. for copying to create new edge
					int rnd_1 = random.nextInt(edgeNum);
					int [] e = new int [3];
					e = edgeCollection.get(rnd_1);
					src = e[0]; trg = e[1]; sign = e[2];

					//	Print: Selected edge for copying
					System.out.println("\nselect edge key: " + rnd_1 + "; edge: [" + src +", " + trg + ", " + sign + "] for copying");

					//	Flag true if an edge found that is not already copied in this tep
					boolean flag = false;

					// 	Check whether or not this edge (key rnd_1) already copied
					if (!copiedEdgesKeys.contains(rnd_1)) {

						//	Set flag true, i.e. a different edge found for copying 
						//		which is not already copied
						flag = true;

						//	Mark this edge as copied
						copiedEdgesKeys.add(rnd_1);
					}
					else {
						System.out.println(rnd_1 + "key edge is already copied in this step.");
						flag = false;
					}

					//	Selecte head or tail vertex to be connected with vNew
					double rnd_2 = random.nextDouble();

					//	If an edge found for coping that is not already copied, i.e flag == true
					//		and if rnd_2 < alpha (pram), i.e. select source for coping
					//		then, copying source, i.e. connect with the target vertex, e[trg]
					if (rnd_2 < alpha && flag == true) {  

						//	if copied edge e is positive and e[trg] doesn't receive a pos-in-deg in this step
						//if (sign > 0 && !tempPosInDeg.contains(trg)){
						if (sign > 0 ){

							System.out.println("coping src " + src + " and connect with trg " + trg 
									+ "; tempPosInDeg.contains(trg): " + tempPosInDeg.contains(trg));

							//	create an edge (vNew, e[trg], 1) 
							String s = createNewEdge(vNew, trg, 1);

							//	write new edge to output csv file
							writer.println(s);

							//	mark e[trg] as already received a pos-in-deg in this step
							tempPosInDeg.add(trg);
						}

						//	if copied edge e is negative and e[trg] doesn't receive a neg-in-deg in this step
						//else if (sign < 0 && !tempNegInDeg.contains(trg)) {
						else if (sign < 0 && !tempNegInDeg.contains(trg)) {

							System.out.println("coping src " + src + " and connect with trg " + trg 
									+ "; tempNegInDeg.contains(trg): " + tempNegInDeg.contains(trg));

							//	create an edge (vNew, e[trg], -1) 
							String s = createNewEdge(vNew, trg, -1);

							//	write new edge to output csv file
							writer.println(s);

							//	mark e[trg] as already received a neg-in-deg in this step
							tempNegInDeg.add(trg);
						}

//						else if (tempNegInDeg.contains(trg)) {
//							System.out.println("trg (" + trg + ") already receives a neg-in-deg");
//						}
//						
//						else if (tempPosInDeg.contains(trg)) {
//							System.out.println("trg (" + trg + ") already receives a pos-in-deg");
//						}
						else continue;

					}

					//	If an edge found for coping that is not already copied, i.e flag == true
					//	and if rnd_2 >= alpha (pram), i.e. select target for coping
					//		then, coping target, i.e. connect  'vNew' with the source vertex e[src]
					if (rnd_2 >= alpha && flag == true) {

						//	if copied edge e is positive and e[src] doesn't recieve a pos-out-deg in this step
						//if (sign > 0 && !tempPosOutDeg.contains(src)){
						if (sign > 0 && !tempPosOutDeg.contains(src)){
							System.out.println("connect with src " + src + " and copying trg " + trg 
									+ "; tempPosOutDeg.contains(src): " + tempPosOutDeg.contains(src));
							
							//	create an edge (e[src], vNew, 1)
							String s = createNewEdge(src, vNew, 1);

							//	write new edge to output csv file
							writer.println(s);

							//	mark e[src] as already received a pos-out-deg in this step
							tempPosOutDeg.add(src);

						}
						
						//	if copied edge e is negative and e[src] doesn't receive a neg-out-deg in this step
						//else if (sign < 0 && !tempNegOutDeg.contains(src)) {
						else if (sign < 0 && !tempNegOutDeg.contains(src)) {	
							System.out.println("connect with src " + src + " and copying trg " + trg 
									+ "; tempNegOutDeg.contains(src): " + tempNegOutDeg.contains(src));

							//	create an edge (e[src], vNew, -1)
							String s = createNewEdge(src, vNew, -1);

							//	write new edge to output csv file
							writer.println(s);

							//	mark e[src] as already received a neg-out-deg in this step
							tempNegOutDeg.add(src);
						}
						
//						else if (tempPosOutDeg.contains(src)) {
//							System.out.println("src (" + src + ") already receives a pos-out-deg");
//						}
//						
//						else if (tempNegOutDeg.contains(src)) {
//							System.out.println("src (" + src + ") already receives a neg-out-deg");
//						}
						
						else continue;
						
					} 	


				} //end-while-loop


				//	Print: Temporary edge collection in this step
				System.out.println("\nPrint tempEdgeCollection (after): ");
				System.out.println("newEdgeCount (after): " + newEdgeCount);
				for (int l=0; l<newEdgeCount; l++) {
					int [] eTemp = tempEdgeCollection.get(l);
					System.out.print(l + "= [");
					for (int m=0; m<3; m++) {
						System.out.print (eTemp[m] + " ");
					}
					System.out.print("]; ");
				}
				System.out.println();
				System.out.println("tempPosOutDeg (after): " + tempPosOutDeg);
				System.out.println("tempPosInDeg (after): " + tempPosInDeg);
				System.out.println("tempNegOutDeg (after): "+ tempNegOutDeg);
				System.out.println("tempNegInDeg (after): " + tempNegInDeg);

				//	Adding all new edges from tempEdgeCollection to edgeCollection
				for (int l=0; l<newEdgeCount; l++) {

					//	 Add each temp edge to edge collection
					int[] tempEdge = tempEdgeCollection.get(l);
					edgeCollection.put(edgeNum, tempEdge);

					//	Updating the total edge number in g
					edgeNum++;
				}

				//	Print: Edge collection after adding one new vertex
				System.out.println("\nEdge collection (after adding nNew): ");
				System.out.println("edgeNum: " + edgeNum);
				for (int l=0; l<edgeNum; l++) {
					int [] e2 = edgeCollection.get(l);
					System.out.print(l+" = [");
					for (int m=0; m<3; m++) {
						System.out.print (e2[m] + " ");
					}
					System.out.print("]; ");
				}
				System.out.println();

			} //end-for-loop

			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return g;
	}

	// Create a new edge
	private static String createNewEdge(int src, int trg, int sign) {
		String s = "";

		//	Add a new edge by copying the target vertex as, e_new = (sourceFromSelectedEdge, vNew)
		g.setEdgeWeight(g.addEdge( src, trg),  sign);

		//	Write the new edge to the output string
		//writer.println(e[0] + "," + vNew + "," + e[2]); 
		s += src + "," + trg + "," + sign;
		System.out.println("new edge: " + src + "," + trg + "," + sign);

		//	Add to new edge to temporary edge collection tempEdgeCollection
		int [] newEdge = new int[3];
		newEdge[0] = src; newEdge[1] = trg; newEdge[2] = sign;
		tempEdgeCollection.put(newEdgeCount, newEdge);

		//	Updating total edges adden in the current step
		newEdgeCount += 1;

		return s ;
	}


	/**
	 * Main method
	 * 
	 **/
	public static void main(String [] args)
	{	
		DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> g;
		Scanner input = new Scanner(System.in);

		//input network information
		System.out.print("Enter number of vertices N = ");
		int N = input.nextInt();

		System.out.print("Enter parameter k = ");
		int k = input.nextInt();

		//Only for Model B
		System.out.print("Enter parameter alpha = ");
		double alpha = input.nextDouble();

		g = EdgeCopyingModel.getRandomNetwork(N, k,alpha);

		//Printing network information
		System.out.println("\n\nMain method: " );
		System.out.println("Number of vertices: " + g.vertexSet().size());
		System.out.println("Number of Edges: " + g.edgeSet().size());
		System.out.println("Number of positive edges: " + CountPosNegEdges.getPosEdges(g));
		System.out.println("Number of negative edges: " + CountPosNegEdges.getNegEdges(g));
		input.close();
	}
}
