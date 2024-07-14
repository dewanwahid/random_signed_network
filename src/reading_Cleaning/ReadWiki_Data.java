package reading_Cleaning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class ReadWiki_Data {
	
	/**
	 * Wikipedia Request for Adminship - Reading from original data
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao's Research Group, Computer Science @ UBC Okanagan
	 * 
	 **/

	public static void main(String[] args) {

//		String filename = "Wiki_RfA_Separated.txt";
//		String outputFile = "Wiki_RfA_edgeList.csv";

		String filename = "sampleData.txt";
		String outputFile = "sampleData.csv";
		
		FileInputStream f = null;

		//Assign each user by an integer ID
		HashMap<String, Integer> id = new HashMap<String, Integer>();

		//initialization
		int src = 0;
		int trg = 0;
		int vote = 0;
		int vID = 0;
		int line = 0;

		String dataline;
		String [] line1parts = new String[2];

		//No soruce ID 
		vID = vID + 1;
		id.put("anynomous01", vID);
		//No target ID
		vID = vID + 1;
		id.put("anynomous02", vID);

		//data reading
		try {
			f = new FileInputStream (filename);
			Scanner s = new Scanner (f);
			PrintWriter writer = new PrintWriter (outputFile);

			//CSV file data heading
			writer.print("#Source"); writer.print(","); writer.print("Target"); writer.print(","); writer.print("Sign"); writer.print("\n");
			//System.out.println("Source, Target, Vote");

			while (s.hasNext()) {

				//Reading source
				dataline = s.nextLine();
				line = line + 1;
				System.out.println(" line: " + line);

				//if the line is not empty
				if (!dataline.equals("")) {

					//split the line at ":"
					line1parts = dataline.split(":");
//					System.out.println("; length: " + line1parts[1].length());
					
					//if the line has source
					if (line1parts[0].trim().equals("SRC")){

						//if the soruce name is empty
						if (line1parts.length < 2) {
							src = id.get("anynomous01");
//							System.out.print("src: " + src);
						}
						//if the source already integer id
						else if (id.containsKey(line1parts[1].trim())) {
							src = id.get(line1parts[1].trim());
//							System.out.print("src: " + src);
						}
						//if the source is new, that is no integer id already
						else {
							//giving the new source a new integer ID
							vID = vID + 1;
							id.put(line1parts[1].trim(), vID);  //put in hashmap
							src = vID;
//							System.out.print("src: " + src);
						}	
					}

					//if the line has target
					if (line1parts[0].trim().equals("TGT")){

						//if the target name is empty
						if (line1parts.length < 2) {
							trg = id.get("anynomous02");
//							System.out.print(", trg: " + trg);
						}
						//if the target already integer id
						else if (id.containsKey(line1parts[1].trim())) {
							trg= id.get(line1parts[1].trim());
//							System.out.print(", trg: " + trg);
						}
						//if the target is new, that is no integer id already
						else {
							//giving the new source a new integer ID
							vID = vID + 1;
							id.put(line1parts[1].trim(), vID);  //put in hashmap
							trg = vID;
//							System.out.print(", trg: " + trg);
						}	
					}

					//if the line has vote
					if (line1parts[0].trim().equals("VOT")){

						//if the vote is empty
						if (line1parts[1].isEmpty()) {
							System.out.println("Error in data line : " + line);
						}
						else {
							vote = Integer.parseInt(line1parts[1].trim());
							writer.print(src); writer.print(","); writer.print(trg); writer.print(","); writer.print(vote); writer.print("\n");
//							System.out.print("; vote: " + vote);
//							System.out.println();
						}
					}
				}	
				else { //if the line is empty, then continue
					continue;
				}
			}
				s.close();
				
				//writing network info
				writer.println("#Network Name: Wikipedia Request for Adminship");
				writer.println("#Nodes: " + id.size());
				writer.println("#Nodes ID: ");
				for (String st : id.keySet()) {
					writer.print("#" + id.get(st)+ "= "); writer.println(st);
				}
				writer.flush();
				writer.close();
				
				System.out.println("Nodes: "+ id.size());
				System.out.println(id);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Error in line " + line);
			}
		}

	}
