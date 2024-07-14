package generator.random;

import generator.random.kTreeModel.K_TreeModel;

import java.util.Scanner;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import core.tools.CountPosNegEdges;

public class RunRandomModels {
	
	/****
	 * Main method: Generate the random models (A/B/C/-Tree)
	 * @author Dewan Ferdous Wahid, Prof. Dr. Yong Gao's Research Group, Department of Computer Science, UBC Okanagan
	 * 
	 * @param 	N - total number of vertices in the network
	 * @param 	k - the model parameter (# of new edges added at each time step)
	 * @param	alpha - the probability of selecting source or target (only for Model B)
	 * @return 	outputFile - the output (.CSV) file name
	 ****/

	public static void main(String [] args)
	{	
		DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = null;
		Scanner input = new Scanner(System.in);
		
		//input network information
		System.out.print("Enter number of vertices N = ");
		int N = input.nextInt();

		System.out.print("Enter parameter k = ");
		int k = input.nextInt();
		
		//Only for Model B
		System.out.print("Enter parameter alpha = ");
		//double alpha = input.nextDouble();
		
		//Output file
		//String outputFile = "data/RandomSignedNetwork_kTreeModel.csv";
		//String outputFile = "data/RSDN_prefAttach.csv";
		//String outputFile = "data/RandomSignedNetwork_edgeCopyingModel.csv";
		//String outputFile = "data/RandomSignedNetwork_cliqueCopyingModel.csv";

		//Selecting random model 
		//g = PrefAttach.getRandomNetwork(N, k);	
		g = K_TreeModel.getRandomNetwork(N, k, null);
		//g = EdgeCopyingModel.getRandomNetwork(N, k,alpha);
		//g = CliqueCopyingModel.getRandomNetwork(N, k, outputFile);

		//Printing network information
		System.out.println("\nMain method: " );
		System.out.println("Number of vertices: " + g.vertexSet().size());
		System.out.println("Number of Edges: " + g.edgeSet().size());
		System.out.println("Number of positive edges: " + CountPosNegEdges.getPosEdges(g));
		System.out.println("Number of negative edges: " + CountPosNegEdges.getNegEdges(g));
		input.close();
	}

}
