package formulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import definition.Inputs;
import location.Coordinates;

/**
 * <h1> Solution layer</h1>
 * Class Implements Forward Dynamic programming solution proposed by Desrosiers et al(1986)
 * see <a href="http://bora.uib.no/bitstream/handle/1956/15817/148682815.pdf?sequence=1">here</a>
 * @author Ritesh Agrawal
 * @version 20170822
 * @see <a href="http://www.tandfonline.com/doi/abs/10.1080/01966324.1986.10737198">Paper</a>
 */
public class SolutionLayer {
	
	/**
	 * List of Possible Current States
	 */
	List<CurrentStates> cs = new ArrayList<CurrentStates>();
	
	/**
	 * Constructor to initialize first iteration with all pick-up points
	 * @param in Inputs object for geolocations
	 */
	public SolutionLayer(Inputs in) {
		ArrayList<Node> nextAll = new ArrayList<Node>();
		ArrayList<Node> delall = new ArrayList<Node>();
		for(int i=0; i<in.getnumcust(); i++){
			delall.add(new Node(i, false));
			nextAll.add(new Node(i, true));			
		}
		for(int i=0; i<in.getnumcust(); i++){
			int t = (int)(in.getLocation(new Node(i, true)).getDistance(in.getInitLoc())/Coordinates.speed);
			cs.add(new CurrentStates(i, nextAll, delall, t));
		}
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Method to remove the Sets of Traversal routes with higher time costs
	 * For proof, refer to the links
	 */
	public void trim() {
		Collections.sort(cs);
		if(cs.size() > 0) {
			int mintime = cs.get(0).time;
			int i=1;
			for(i = 1; i<cs.size(); i++) {
				int tm = cs.get(i).time;
				if(tm>mintime) break;
			}
			if(i<cs.size()-1) cs = cs.subList(0, i+1);
			else cs = cs.subList(0, i);
		}
	}
	
	/**
	 * Method to move ahead one iteration
	 * @param in Inputs object for geolocations
	 * @param iter Iteration Number
	 */
	public void nextiter(Inputs in, int iter) {
		ArrayList<CurrentStates> next = new ArrayList<CurrentStates>();
		for(CurrentStates c : cs) {
			for(Node n: c.NextNodes) {
				CurrentStates c1 = c.addNode(n, in);
				if(c1.checkfeasibilityn0(in)&&c1.checkfeasibilityn1(in)&&c1.checkfeasibilityn2(in)) 
					next.add(c1);
			}
		}
		cs = next;
		this.trim();
	}
	
	/**
	 * Print All feasible solutions in current state
	 */
	public void printState() {
		for(CurrentStates c: cs) {
			c.printcurr();
			System.out.println(c.time);
		}
	}
}
