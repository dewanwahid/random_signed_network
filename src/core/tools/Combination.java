package core.tools;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 *@author Dewan Ferdous Wahid
 *@affiliation Prof. Dr. Yong Gao's Research Group, Computer Science, UBC Okanagan
 *
 **/

public class Combination {
	
	/**
	 * Listing all possible k-combination of an array of size n, i.e. (n, k)
	 * 
	 * @param  int[] array - the array of integers 
	 * @param  int n - size of the array
	 * @param  int k - size of the k-clique
	 * @return HashMap<int[], Integer> cliques - collection of all possible k-clique
	 ***/

	private static int c = 0;

	public static HashMap<Integer, LinkedList<Integer>> getCombinationWithUpdatedKey(int[] array, int n, int k, int cliquesNum) {

		HashMap<Integer, LinkedList<Integer>> newCliques = new HashMap<Integer, LinkedList<Integer>>();
		//remove all previous data
		//	newCliques.equals(null);
		c = cliquesNum;
		// A temporary array to store all combination one by one
		int data[]=new int[k];     

		// Print all combination using temprary array 'data[]'
		newCliques = combinationUtil(newCliques, array, data, 0, n-1, 0, k);
		//System.out.println("c_comb = "+ cliques.size());

		return newCliques;
	}

	public static HashMap<Integer, LinkedList<Integer>> getCombinationNoUpdatedKey(int[] array, int n, int k, int cliquesNum) {

		HashMap<Integer, LinkedList<Integer>> newCliques = new HashMap<Integer, LinkedList<Integer>>();
		//remove all previous data
		//newCliques.equals(null);
		c = 0;
		// A temporary array to store all combination one by one
		int data[]=new int[k];     

		// Print all combination using temprary array 'data[]'
		newCliques = combinationUtil(newCliques, array, data, 0, n-1, 0, k);
		//System.out.println("c_comb = "+ cliques.size());

		return newCliques;
	}

	private static HashMap<Integer, LinkedList<Integer>> combinationUtil(HashMap<Integer, LinkedList<Integer>> newCliques, 
			int array[], int data[], int start, int end, int index, int k) { 
		LinkedList<Integer> data2 = new LinkedList<Integer>();

		// Current combination is ready to be printed, print it
		if (index == k) {  
			
			//print k-clique before store to cliques
			for (int j=0; j<k; j++) {

				//add to linked list
				data2.add(data[j]);
			}

			//if data2 (k-clique) not contains in cliques, then store it
			if (!newCliques.containsKey(data2) && data2.size() == k) {
				newCliques.put(c, data2);
				c += 1; //k-clique number in cliques
			}

			return newCliques;
		}

		// replace index with all possible elements. The condition
		// "end-i+1 >= k-index" makes sure that including one element
		// at index will make a combination with remaining elements
		// at remaining positions
		for (int i=start; i<=end && end-i+1 >= k-index; i++) {
			data[index] = array[i];
			combinationUtil(newCliques, array, data, i+1, end, index+1, k);
		}
		return newCliques;
	}
}
