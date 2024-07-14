package generator.random.PrefAttach;

import java.util.HashMap;

public class UpdateDegreeSigns {
	
	/**
	 * @author Dewan Ferdous Wahid
	 * @affiliation Dr. Yong Gao Research group, Computer Science, UBC-Okanagan
	 * 
	 */

	protected static void updatingVertexAndTotalDegreeSign(int src, int trg, int sign) {
		
		if (sign > 0) {		//	if the sign is positive
			
			//Add positive-out-degree to 'src' and positive-in-degree to 'trg' by updating
			//		to the array 'posOutDeg' and 'posInDeg' in 'PrefAttach' class
			PrefAttach.setPosOutDeg(src, 1);
			PrefAttach.setPosInDeg(trg, 1);
			
			//Updating total positive degree, i.e. increase by 2
			PrefAttach.updateTotalPosDeg(2);
			
		}
		else {				// if the sign is negative
			
			//	Add negative-out-degree to 'src' and negative-in-degree to 'trg'
			//		to the array 'negOutDeg' and 'negInDeg' in 'PrefAttach' class
			PrefAttach.setNegOutDeg(src, 1);
			PrefAttach.setNegInDeg(trg, 1);

			//	Updating total negative degree, i.e. increase by 2
			PrefAttach.updateTotalNegDeg(2);
		}
		
	}
	
	protected static void updateTemSignedDeg(int src, int trg, int sign) {
		
		if (sign > 0) {
			PrefAttach.setTempPosOutDeg(src, 1);
			PrefAttach.setTempPosInDeg(trg, 1);
			
			PrefAttach.setTempTotPosDeg(2);
		}
		else {
			PrefAttach.setTempNegOutDeg(src, 1);
			PrefAttach.setTempNegInDeg(trg, 1);
			
			PrefAttach.setTempTotNegDeg(2);			
		}
	}
	

	public static void updatePosOutDeg(double[] posOutDeg,
			HashMap<Integer, Integer> tempPosOutDeg) {
		
		//System.out.println("\ntempPosOutDeg (before): " + tempPosOutDeg);
		
		for (Integer v : tempPosOutDeg.keySet()) {
			int d = tempPosOutDeg.get(v);
			PrefAttach.setPosOutDeg(v, d);
			//tempPosOutDeg.remove(v);
		}
		
		tempPosOutDeg.clear();
		//System.out.println("tempPosOutDeg (after): " + tempPosOutDeg);
	}

	public static void updatePosInDeg(double[] posInDeg,
			HashMap<Integer, Integer> tempPosInDeg) {
		
		//System.out.println("tempPosInDeg (before): " + tempPosInDeg);
		
		for (Integer v : tempPosInDeg.keySet()) {
			int d = tempPosInDeg.get(v);
			PrefAttach.setPosInDeg(v, d);
			//tempPosInDeg.remove(v);
		}	
		tempPosInDeg.clear();
		//System.out.println("tempPosInDeg (after): " + tempPosInDeg);
	}

	public static void updateNegOutDeg(double[] negOutDeg,
			HashMap<Integer, Integer> tempNegOutDeg) {
		
		//System.out.println("tempNegOutDeg (before): " + tempNegOutDeg);
		
		for (Integer v : tempNegOutDeg.keySet()) {
			int d = tempNegOutDeg.get(v);
			PrefAttach.setNegOutDeg(v, d);
			//tempNegOutDeg.remove(v);
		}
		tempNegOutDeg.clear();
		//System.out.println("tempNegOutDeg (after): " + tempNegOutDeg);
	}

	public static void updateNegInDeg(double[] negInDeg,
			HashMap<Integer, Integer> tempNegInDeg) {
		
		//System.out.println("tempNegInDeg (before): " + tempNegInDeg);

		for (Integer v : tempNegInDeg.keySet()) {
			int d = tempNegInDeg.get(v);
			PrefAttach.setNegInDeg(v, d);
			//tempNegInDeg.remove(v);
		}
		tempNegInDeg.clear();;
		//System.out.println("tempNegInDeg (after): " + tempNegInDeg);
	}
}
