package generator.random.PrefAttach;

import java.util.Random;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;


public class AddEdges {

	/**
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao Research group, Computer Science, UBC-Okanagan
	 * 
	 */

	private static Random random = new Random();

	/**
	 * Add 'k' signed directed edges in-ward to the new vertex vNew
	 * 
	 * @param g - signed directed netwrok
	 * @param k - number of edges added by new vertex
	 * @param vNew - the new vertex intering to the network g
	 * @param sign - edge sign of newly added edges (1 or -1) 
	 * 
	 * Dependency: jGrapht, 'http://jgrapht.org/'
	 * 
	 **/

	protected static String  add_k_inEdgesToNewVertex(
			DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>  g, 
			int k, 			// #of edges adding to network by connectin existing vertices with new vertex
			int vNew, 		// the new vetex entering to the network
			int sign		// the sign of the newly creating edges
			) {

		//	Number of vertice in the existing network 'g' (excluding the new vertex)
		int n = g.vertexSet().size() - 1;

		//	Temporary output data string (new edges)
		String tempOutput = "";

		//	Count the newly creating edges
		int count = 0;

		double posDegIgnore = 0;
		double negDegIgnore = 0;

		//	Add k edges between 'vNew' vertice and k distince existing vertices
		while (count < k) {

			// 	Probability that an existing vetex received a signed-out-degree
			double pOut = 0;

			//	Generate a random double to compare the probability of creating edge
			double rnd = random.nextDouble();
			//System.out.println("\n\nrnd: " + rnd);

			//	Try to conncet the new vertex vNew with every existing node
			for (int v=1; v<=n; v++) {
				
				//System.out.println("\nv: " + v);
				//	Determine the direction base on the input 'dir'.
				//		If "in" create an edge inward to 'vNew'
				// 		and if "out" crate an edge outward to 'vNew'
				// 		and if there exist no edges on the prefered direction (either positive or negative)
				if (!g.containsEdge(v, vNew)) {				//creating in-ward edge to vNew

					//	Increment the talley by the v-th vertex's probability
					// 	The probability that 'v' receives a signed-out-degree (either pos or neg)
					//		based on the given 'sign'
					if (sign > 0) {
						double degIgnore = posDegIgnore;
						//System.out.println("posDegIgnore: " + degIgnore);
						pOut += getSignedOutDegProbability(v, sign, degIgnore);
					}
					else {
						double degIgnore = negDegIgnore;
						//System.out.println("negDegIgnore: " + degIgnore);
						pOut += getSignedOutDegProbability(v, sign, degIgnore);
					}
					//System.out.println("in; pOut: " + pOut);
				}

				// 	If the 'rnd' is less than pOut, then  
				//	Add an directed edge (v, vNew) with given 'sign'
				if (rnd <= pOut) {

					// Push the posDegIgnore or negDegIgnoretally by adding total positive-degree (in & out) of v if sign> 0
					// 	or if sign <0, then increse by adding total negative degree (in &out) of v
					if (sign > 0) {
						posDegIgnore += (PrefAttach.getPosOutDeg(v) + PrefAttach.getPosInDeg(v));
						//System.out.println("posDegIgnore (next step): " + posDegIgnore + "; posOutDeg["+v+"] = " 
						//		+ PrefAttach.getPosOutDeg(v)
						//		+ "; posInDeg["+v+"]="
						//		+ PrefAttach.getPosInDeg(v));
					}
					else {
						negDegIgnore += (PrefAttach.getNegOutDeg(v) + PrefAttach.getNegInDeg(v));
						//System.out.println("negDegIgnore (next step): " + negDegIgnore + "; negOutDeg["+v+"] = "
						//		+ "" + PrefAttach.getNegOutDeg(v)
						//		+ "; negInDeg["+v+"]=" 
						//		+ PrefAttach.getNegInDeg(v));
					}

					// Add the edge (v, vNew)
					g.setEdgeWeight(g.addEdge(v, vNew), sign);

					//	Add the edge to the tempOutput data string
					tempOutput = tempOutput + v + "," + vNew + "," + sign + "\n"; 

					//System.out.println("src: " + v + ", trg: " + vNew + "; " + sign);

					//	Updating the signed degree of the vertices 
					//UpdateDegreeSigns.updatingVertexAndTotalDegreeSign(v, vNew, sign);
					UpdateDegreeSigns.updateTemSignedDeg(v, vNew, sign);

					//	Updating newly added edge count
					count++;	
					break;
				}

			}
		} //end-while-loop
		return tempOutput;	
	}


	protected static String  add_k_outEdgesToNewVertex(
			DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>  g, 
			int k, 			// #of edges adding to network by connectin existing vertices with new vertex
			int vNew, 		// the new vetex entering to the network
			int sign		// the sign of the newly creating edges
			) {

		//	Number of vertice in the existing network 'g' (excluding the new vertex)
		int n = g.vertexSet().size() - 1;

		//	Temporary output data string (new edges)
		String tempOutput = "";

		//	Count the newly creating edges
		int count = 0;

		double posDegIgnore = 0;
		double negDegIgnore = 0;
		

		//	Add k edges between 'vNew' vertice and k distince existing vertices
		while (count < k) {

			// 	Probability that an existing vetex received a signed-in-degree
			double pIn = 0;

			//	Generate a random double to compare the probability of creating edge
			double rnd = random.nextDouble();
			//System.out.println("rnd: " + rnd);

			//	Try to conncet the new vertex vNew with every existing node
			for (int v=1; v<=n; v++) {

				//System.out.println("\nv: " + v);
				if (!g.containsEdge(vNew, v)) {		//crating out-ward edge to vNew

					// 	Calculating the probability that vNew is creating signed (given by 'sign") out-ward edge with v
					// 		i.e. the probability that 'v' receives a signed-in-degree (either pos or neg)
					//		based on the given 'sign'
					if (sign > 0) {
						double degIgnore = posDegIgnore;
						//System.out.println("posDegIgnore: " + degIgnore);
						pIn += getSignedInDegProbability(v, sign, degIgnore);
					}
					else {
						double degIgnore = negDegIgnore;
						//System.out.println("negDegIgnore: " + degIgnore);
						pIn += getSignedInDegProbability(v, sign, degIgnore);
					}
					//System.out.println("out; pIn: " + pIn);


					//	If the 'rnd' is less than pIn, then  
					//		Add an directed edge (vNew, v) with given 'sign'
					if (rnd <= pIn) {

						// Push the posDegIgnore or negDegIgnoretally by adding total positive-degree (in & out) of v if sign> 0
						// 	or if sign <0, then increse by adding total negative degree (in &out) of v
						if (sign > 0) {
							posDegIgnore += (PrefAttach.getPosOutDeg(v) + PrefAttach.getPosInDeg(v));
							
							//System.out.println("posDegIgnore (next step): " + posDegIgnore + "; posOutDeg["+v+"] = " 
							//		+ PrefAttach.getPosOutDeg(v)
							//		+ "; posInDeg["+v+"]="
							//		+ PrefAttach.getPosInDeg(v));
						}
						else {
							negDegIgnore += (PrefAttach.getNegOutDeg(v) + PrefAttach.getNegInDeg(v));
							
							//System.out.println("negDegIgnore (next step): " + negDegIgnore + "; negOutDeg["+v+"] = "
							//		+ "" + PrefAttach.getNegOutDeg(v)
							//		+ "; negInDeg["+v+"]=" 
							//		+ PrefAttach.getNegInDeg(v));
						}

						//	Add the edge (vNew, v)
						g.setEdgeWeight(g.addEdge(vNew, v), sign);

						//	Add the edge to the tempOutput data string
						tempOutput = tempOutput + vNew + "," + v + "," + sign + "\n"; 

						//System.out.println("src: " + vNew + ", trg: " + v + "; " + sign);

						//	Updating the signed degree of the vertices 
						UpdateDegreeSigns.updateTemSignedDeg(vNew, v, sign);

						//	Updating newly added edge count
						count++;
						break;
					}
				}
				else continue;
			}
		} //end-while-loop

		return tempOutput;	
	}


	private static double getSignedOutDegProbability(int v, int sign, double degIgnore) {
		double prOut = 0;

		//	probability that the vertex v receved a positive-out-degree
		if (sign > 0) {	
			//System.out.println("posOutDeg["+v+"] : "+ PrefAttach.getPosOutDeg(v));
			//System.out.println("totalPosDeg: " + PrefAttach.getTotalPosDeg());

			prOut = (2 * PrefAttach.getPosOutDeg(v)) / (PrefAttach.getTotalPosDeg() - degIgnore);
			//System.out.println("prOut: " + prOut);
		}

		// probability that the vertex v receved a negative-out-degree
		else {
			//System.out.println("negOutDeg["+v+"] : "+ PrefAttach.getNegOutDeg(v));
			//System.out.println("totalNegDeg: " + PrefAttach.getTotalNegDeg());

			prOut = (2 * PrefAttach.getNegOutDeg(v)) / (PrefAttach.getTotalNegDeg() - degIgnore);
			//System.out.println("prOut: " + prOut);
		}

		return prOut;
	}

	private static double getSignedInDegProbability(int v, int sign, double degIgnore) {
		double prIn = 0;

		//	probability that the vertex v receved a positive-In-degree
		if (sign > 0) {	
			//System.out.println("posInDeg["+v+"] : "+ PrefAttach.getPosInDeg(v));
			//System.out.println("totalPosDeg: " + PrefAttach.getTotalPosDeg());

			prIn = (2 * PrefAttach.getPosInDeg(v)) / (PrefAttach.getTotalPosDeg() - degIgnore);
			//System.out.println("prIn: " + prIn);
		}

		// probability that the vertex v receved a negative-In-degree
		else {
			//System.out.println("negInDeg["+v+"] : "+ PrefAttach.getNegInDeg(v));
			//System.out.println("totalNegDeg: " + PrefAttach.getTotalNegDeg());

			prIn = (2 * PrefAttach.getNegInDeg(v)) / (PrefAttach.getTotalNegDeg() - degIgnore);

			//System.out.println("prIn: " + prIn);
		}

		return prIn;
	}
}
