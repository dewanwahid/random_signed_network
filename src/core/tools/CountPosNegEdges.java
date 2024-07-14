package core.tools;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

public class CountPosNegEdges {
	
	/**
	 * 
	 *@author Dewan Ferdous Wahid
	 *@affiliation Prof. Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan
	 *
	 **/
	
	private static int countPosEdges = 0;
	private static int countNegEdges = 0;
	
	public static int getPosEdges(DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g){
		
		for (DefaultWeightedEdge e : g.edgeSet()){
			if (g.getEdgeWeight(e) > 0) {
				countPosEdges+=1;
			}
		}
		return countPosEdges;
	}

	
	public static int getNegEdges(DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g){
		
		for (DefaultWeightedEdge e : g.edgeSet()){
			if (g.getEdgeWeight(e) < 0) {
				countNegEdges+=1;
			}
		}
		return countNegEdges;
	}

	public static int getPosEdges(DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> g){
		
		for (DefaultWeightedEdge e : g.edgeSet()){
			if (g.getEdgeWeight(e) > 0) {
				countPosEdges+=1;
			}
		}
		return countPosEdges;
	}

	
	public static int getNegEdges(DirectedWeightedMultigraph<Integer, DefaultWeightedEdge> g){
		
		for (DefaultWeightedEdge e : g.edgeSet()){
			if (g.getEdgeWeight(e) < 0) {
				countNegEdges+=1;
			}
		}
		return countNegEdges;
	}
}
