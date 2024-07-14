package core.tools;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestingClass {
	
	/**
	 * 
	 *@author Dewan Ferdous Wahid
	 *@affiliation Prof. Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan
	 *
	 *Used library: jGrapht graph package from 'http://jgrapht.org/'
	 *
	 **/
	
	public static void main (String[] args) {
		
		/**
		 * Active a particular testing area
		 * 
		 **/
		
		
		/**
		 * Mutiple edge testing JTGraph
		 **/
		
		DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		g.addVertex(1); g.addVertex(2); g.addVertex(3);
		g.setEdgeWeight(g.addEdge(1, 2), 1);
		g.setEdgeWeight(g.addEdge(1, 2), -1);
		g.setEdgeWeight(g.addEdge(1, 2), -1);
		g.setEdgeWeight(g.addEdge(2, 3), 1);
		g.setEdgeWeight(g.addEdge(3, 2), 1);
		
		
		for (DefaultWeightedEdge e: g.edgeSet()){
			System.out.println(e + "; " + g.getEdgeWeight(e));
		}
		
		
		/**
		 * Combintation Testing
		 **/
		/*
		int [] array = {1,2,3,4,5,6};
		int n = array.length;
		int k = 3;
		
		HashMap<Integer, LinkedList<Integer>> cliques = new HashMap<Integer, LinkedList<Integer>>();
		LinkedList<Integer> kClique = new LinkedList<Integer>();
		int cliquesNum = 0;
		
		//get combianation
		cliques = Combination.getCombination(array, n, k, cliquesNum);
		
		for (int i=0; i<cliques.size(); i++) {
			kClique = cliques.get(i);
			for (int j=0; j<kClique.size(); j++) {
				System.out.print(kClique.get(j) + " ");
			}
			System.out.println();
		}
		
		System.out.println("#/clique_size " + cliques.size());
		
		*/
		
		
		/**
		 * Probability Testing
		 **/
		/*
		Random r=new Random();   
		double tdeg = 15;
		double pIn = 2;
		double pOt = 3;
		double nIn = 4;
		double nOt = 6;
		double p1 = pIn / tdeg; 
		double p2 = p1 + (pOt / tdeg);
		double p3 = p2 + (nIn / tdeg);
		double p4 = p3 + (nOt / tdeg);
		double[] weights=new double[]{p1, p2, p3, p4};
		
		for (int i=1; i<weights.length; i++){
			System.out.println(weights[i] + " ");
		}

		double rnd = r.nextDouble();
		System.out.println("rnd : " + rnd);
		
		if (rnd <= weights[0]) {
			System.out.println("PosIn");
		}
		else if (rnd <= weights[1]) {
			System.out.println("PosOut");
		}
		else if (rnd <= weights[2]) {
			System.out.println("NegIn");
		}
		else {
			System.out.println("NegOut");
		}*/
		
		
		/**
		 * Combining two HashMap testing
		 */
		/*
		HashMap<Integer, LinkedList<Integer>> map1 = new HashMap<Integer, LinkedList<Integer>>();
		LinkedList<Integer> m1 = new LinkedList<Integer>();
		m1.add(0);
		int c = 0;
		for (int i=1; i<=20; i++) {
			System.out.println(m1.size());
			int n = m1.size();
			if (m1.size() == 3) {
				System.out.println("c: " +c);
				map1.put(c, m1);
				c +=1;
				System.out.println("m1: " + m1);
				m1.remove();m1.remove(); m1.remove();
			}	
			else {
				m1.add(i);
			}
		}
		System.out.println("Size map1: " + map1.size());
		System.out.println("Map 1 : " + map1);
		for (int i=0; i<c; i++){
			System.out.println(map1.get(i));
		}
		
		
		HashMap<Integer, LinkedList<Integer>> map2 = new HashMap<Integer, LinkedList<Integer>>();
		LinkedList<Integer> m2 = new LinkedList<Integer>();
		for (int i=22; i<=35; i++) {
			System.out.println(m2.size());
			int n = m2.size();
			if (m2.size() == 3) {
				System.out.println("c: " +c);
				map2.put(c, m2);
				c +=1;
				System.out.println("m2: " + m2);
				m2.remove();m2.remove(); m2.remove();
			}	
			else {
				m2.add(i);
			}
		}
		System.out.println("Size map2: " + map2.size());
		System.out.println("Map 2 : " + map2);
		
		
		
		Set<HashMap.Entry<Integer, LinkedList<Integer>>> entries = map1.entrySet();
		for ( HashMap.Entry<Integer, LinkedList<Integer>> entry : entries ) {
		  LinkedList<Integer> map2Value = map2.get( entry.getKey() );
		  if ( map2Value  == null ) {
			  map2.put( entry.getKey(), entry.getValue() );
		  }
		  else {
			  map2Value.addAll( entry.getValue() );
		  }
		}
		System.out.println("Size Map2 : " + map2.size());
		

		for (int i=0; i<map2.size(); i++) {
			System.out.println(map2.get(i));
		}
		System.out.println("Map 2 : " + map2);
		*/
		
		/**
		 * testing combining two hashmap, without key repeatation
		 **/ 
		/*
		HashMap<Integer, LinkedList<Integer>> map1 = new HashMap<Integer, LinkedList<Integer>>();
		LinkedList<Integer> temp1 = new LinkedList<Integer>(Arrays.asList(1, 2));
		LinkedList<Integer> temp2 = new LinkedList<Integer>(Arrays.asList(3, 4));
		LinkedList<Integer> temp3 = new LinkedList<Integer>(Arrays.asList(5, 6));
		
		map1.put(0, temp1); 		map1.put(1, temp2); 		map1.put(2, temp3);
		System.out.println("Map 1:" + map1);
		
		HashMap<Integer, LinkedList<Integer>> map2 = new HashMap<Integer, LinkedList<Integer>>();
		LinkedList<Integer> m1= new LinkedList<Integer>(Arrays.asList(6, 7));
		LinkedList<Integer> m2 = new LinkedList<Integer>(Arrays.asList(9, 8));
		
		map2.put(3, m1); 		map2.put(4, m2); 		
		System.out.println("Map 2: " + map2);
		
		map1.put(3, map2.get(3)); map1.put(4, map2.get(4));
		System.out.println("Map 1:" + map1);
		*/
	} 

}
