package control;

import java.util.Random;

import definition.Inputs;
import formulation.SolutionLayer;
import location.Coordinates;

/**
 * Main class - Entry point of the program
 * In the current version, to meet the requirement as specified in the problem, 
 * Maximum Delivery time is set to max integer, i.e. requirement has been removed
 * @author Ritesh Agrawal
 * @version 20170822
 *
 */
public class RunSimulation {
	/**
	 * Method first initialize the input configuration randomly
	 * Develops the solution for generated configuration
	 * Currently 5 orders are being served in code
	 * In case, Maximum Delivery time conditions are added, there may be cases with no solutions
	 * @param args Null as of current implementation
	 */
	public static void main(String args[]) {
		Inputs in = new Inputs(5);
		Coordinates init = new Coordinates(12.9279, 77.6271);
		in.addInit(init);
		Random r = new Random(170587);//Authors Birthday in ddmmyy format
		for(int i = 0; i<5; i++){
			double d1 = 12.9279 + 0.001*r.nextDouble();
			double d2 = 77.6271 + 0.001*r.nextDouble();
			double d3 = 12.9279 + 0.001*r.nextDouble();
			double d4 = 77.6271 + 0.001*r.nextDouble();
			int d5 = 5 + (int)(20*r.nextDouble());
			int d6 = 30 + (int)(20*r.nextDouble());
			in.addPair(new Coordinates(d1, d2), new Coordinates(d3, d4), i);
			in.addPrepTime(d5, i);
			in.addMaxDeliveryTime(Integer.MAX_VALUE, i);
		}
		
		SolutionLayer sl= new SolutionLayer(in);
		for(int i=1; i<= 9; i++) {
			sl.nextiter(in, i);
		}
		sl.printState();
	}
}
