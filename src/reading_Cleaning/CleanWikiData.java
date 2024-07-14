package reading_Cleaning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class CleanWikiData {
	
	/**
	 * Wikipedia Request for Adminship - Cleaning
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science @ UBC Okanagan
	 * 
	 **/
	

	//private static DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	private static LinkedList<int[]> edges = new LinkedList<int[]>();
	public static void main (String[] args) {

		//loading data
		String filename = "Wiki_RfA_edgeList.csv";
		String outputFile1 = "Wiki_RfA_edgeList_without_repeatedEdges.csv";
		String outputFile2 = "Wiki_RfA_repeatedEdges.csv";
		//		String filename = "sampleData.csv";
		//		String outputFile = "sampleData_Clean.csv";

		FileInputStream f = null;

		//initialization
		int line = 0;
		int [] iniEdge = {0,0,0};
//		for (int i=0; i<3; i++) {
//			System.out.print(iniEdge[i]);
//		} System.out.println();


		try {
			f = new FileInputStream (filename);
			Scanner s = new Scanner (f);
			PrintWriter writer1 = new PrintWriter (outputFile1);
			PrintWriter writer2 = new PrintWriter(outputFile2);

			//CSV datafile heading
			writer1.print("#Source"); writer1.print(","); writer1.print("Target"); writer1.print(","); writer1.print("Sign"); writer1.print("\n");
			writer2.print("#Source"); writer2.print(","); writer2.print("Target"); writer2.print(","); writer2.print("Sign"); writer2.print("\n");
			//reading data 
			while (s.hasNext()) {

				//Reading lines
				String dataline = s.nextLine();
				line = line + 1;
				//System.out.println("line: " + line);

				//if line is empty, then continue
				if (dataline.equals("")) continue;

				//if the first character is "#" write the info line
				if (Character.toString(dataline.charAt(0)).equals("#")) {
					writer1.println(dataline);
				}
				else {
					String[] lineParts = dataline.split(",");

					//if lineParts lenght is not 3, then there is error in data line
					if (lineParts.length != 3) {
						System.out.println("Error in line: " + line);
					}
					else {
						//if the vote is 0 the remove it
						if (Integer.parseInt(lineParts[2]) != 0){
							int src = Integer.parseInt(lineParts[0]);
							int trg = Integer.parseInt(lineParts[1]);
							int sign = Integer.parseInt(lineParts[2]);
							//System.out.println(src + ", " + trg + "; " + sign);

							//creating the edges array
							int[] e = new int[3];
							e[0] = Integer.parseInt(lineParts[0]);
							e[1] = Integer.parseInt(lineParts[1]);
							e[2] = Integer.parseInt(lineParts[2]);

							//checking the repeated edge in the edge list 'edges'
							for (int[] ei : edges) {
								System.out.println("existing: " + ei[0] + ", " + ei[1] + "; " + ei[2]);

								if (e[0]==ei[0] && e[1]==ei[1] && e[2]==ei[2]) { //sing and direction same
									System.out.println("1");
									//write in the repeated edge list
									writer2.print(e[0]);  writer2.print(","); writer2.print(e[1]);  writer2.print(","); writer2.print(e[2]);
								} else if (e[0]==ei[0] && e[1]==ei[1] && e[2]!=ei[2]) { //sign same
									System.out.println("2");
									//write in the edge list
									writer1.print(e[0]);  writer1.print(","); writer1.print(e[1]);  writer1.print(","); writer1.print(e[2]);
									//write in the repeated edge list
									writer2.print(e[0]);  writer2.print(","); writer2.print(e[1]);  writer2.print(","); writer2.print(e[2]);
								} else { //new edge
									System.out.println("3");
									//write in the edge list
									writer1.print(e[0]);  writer1.print(","); writer1.print(e[1]);  writer1.print(","); writer1.print(e[2]);
								}
							}
						}
					} 
				}
			}


			s.close();
			writer1.flush();
			writer1.close();
			writer2.flush();
			writer2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		//		//checking the repeated edge
		//		if (g.containsEdge(g.getEdge(src, trg))) {	
		//			System.out.println("Yes");
		//			if (g.getEdgeWeight(g.getEdge(src, trg)) != sign) {
		//				System.out.println("Yes yes");
		//				//add edge to network
		//				g.setEdgeWeight(g.addEdge(src, trg), sign);
		//				//write edge to csv file
		//				writer.println(dataline);
		//				//print 
		//				System.out.println(src + ", " + trg + "; " + sign);
		//			} else continue;
		//		} else {
		//			g.addVertex(src);
		//			g.addVertex(trg);
		//			//add edge to network
		//			g.setEdgeWeight(g.addEdge(src, trg), sign);
		//			//write edge to csv file
		//			writer.println(dataline);
		//			//print 
		//			System.out.println(src + ", " + trg + "; " + sign);
		//		}

	}

}
