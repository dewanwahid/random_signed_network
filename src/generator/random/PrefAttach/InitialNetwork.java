package generator.random.PrefAttach;

import java.util.Random;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class InitialNetwork {
	
	/**
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao Research group, Computer Science, UBC-Okanagan
	 * 
	 * @param g - signed directed network
	 * @param N - total number of vertices
	 * @param k - model parameter to add k signed edge in each direction
	 * 
	 * Dependency: jGrapht, 'http://jgrapht.org/'
	 * 
	 */

	private static Random random = new Random();

	protected static String addIniNet(
			DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g,
			int N, int k) {

		String tempOutput = "";

		//	Add vertices to the initial network
		int k0 = 2*k + 1;
		for (int i=1; i<=k0; i++) {
			g.addVertex(i);
		}

		//	Select a vertex from the vertex set {1, 2, ..., k0}
		

		// #of positive edges added to network so far
		int m = 0;  

		//	Add k positive edges to the network
		while (m < 2*k) {

			//	Selecte a random vertex form {1, 2, ..., 2k+1}
			//		to connect with 'v1'
			//int v3 = random.nextInt(k0) + 1;
			int src = random.nextInt(k0) + 1;
			int trg = random.nextInt(k0) + 1;

			//	If the selecting vertex is not v1, i.e. no self loop
			//		and if there exist no edge between v3 and v1, then
			//		create a positive edge (v3, v1)
			if (src != trg 
					&& PrefAttach.getPosOutDeg(src) == 0
					&& PrefAttach.getPosInDeg(trg) == 0
					&& !g.containsEdge(src, trg)
					//&& !g.containsEdge(trg, src)
					) {

				//System.out.println("src: " + src + ", trg: " + trg + "; 1");

				//	Add a positive edge (v3,v1) to network
				g.setEdgeWeight(g.addEdge(src, trg), 1);
				
				//	Add edge to the data string
				tempOutput = tempOutput + src + "," + trg + "," + "1" + "\n";

				//	Update the vertex and network sined degrees
				UpdateDegreeSigns.updatingVertexAndTotalDegreeSign(src, trg, 1);

				//	updating stoping condition
				m++;	
			}
		}

		//	Select a vertex 'v2' from the vertex set {1,2,..,k0}
		//int v2 = random.nextInt(k0) + 1;

		//	#of negative edges added to network so far
		int n = 0;

		//	Add k negative edges to the network
		while (n < 2*k) {

			//	Selecte a random vertex form {1, 2, ..., k0}
			//		to connect with 'v2'
			//int v4 = random.nextInt(k0) + 1;
			int src = random.nextInt(k0) + 1;
			int trg = random.nextInt(k0) + 1;

			//	If the selecting vertex is not v2, i.e. no self loop
			// 		and if there exist no edge between v4 and v2, then
			//		create a negative edge (v4, v2)
			if (src != trg 
					&& PrefAttach.getNegOutDeg(src) == 0
					&& PrefAttach.getNegInDeg(trg) == 0
					&& !g.containsEdge(src, trg) 
					//&& !g.containsEdge(trg, src)
					) {

				//System.out.println("src: " + src + ", trg: " + trg + "; -1");

				//	Add a positive edge (v4,v2) to network
				g.setEdgeWeight(g.addEdge(src, trg), -1);
				
				//	Add edge to the data string
				tempOutput = tempOutput + src + "," + trg + "," + "-1" + "\n";

				//Update the vertex and network sined degrees
				UpdateDegreeSigns.updatingVertexAndTotalDegreeSign(src, trg, -1);

				//	Updating stoping condition
				n++;	
			}
		}
		return tempOutput;
	}
}
