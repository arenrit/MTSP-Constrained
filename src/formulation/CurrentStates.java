package formulation;

import java.util.ArrayList;
import definition.Inputs;
import location.Coordinates;

/**
 * <h1> Current State</h1>
 * Class keeps the track of number of nodes currently visited, total time taken and possible next nodes
 * It is the base of implementation of Forward Dynamic programming. 
 * Three methods for checking the precedence has been implemented, to reduce worst case complexity than that of TSP 
 * @author Ritesh Agrawal
 * @version 20170822
 *
 */
public class CurrentStates implements Comparable{
	
   /**
    * List containing nodes visted so far  	
    */
   ArrayList<Node> visitednodes = new ArrayList<Node>();
   
   /**
    * List containing next possible nodes - this takes care of node precedence
    */
   ArrayList<Node> NextNodes = new ArrayList<Node>();
   
   /**
    * List containing leftover Delivery Nodes
    */
   ArrayList<Node> DelNodes = new ArrayList<Node>();
   
   /**
    * Node at the terminal in the given traversal
    */
   Node LastNode;
   
   /**
    * Time taken in traversal
    */
   int time;
   
   
   /**
    * Default constructor - used for cloning
    */
   public CurrentStates() {}
   
   /**
    * Constructor to instantiate object for first iteration - reach nth Pick-up location
    * @param index Index of pick-up location
    * @param nextAll All possible pick-up locations - this will be used to derive next set of possible nodes
    * @param delall All possible delivery locations
    * @param t Time to reach index pick-up location
    */
   public CurrentStates(int index, ArrayList<Node> nextAll, ArrayList<Node> delall, int t){
	   visitednodes.add(new Node(index, true));
	   NextNodes.addAll(nextAll);
	   NextNodes.remove(new Node(index, true));
	   NextNodes.add(new Node(index, false));
	   DelNodes.addAll(delall);
	   LastNode = new Node(index, true);
	   time = t;
   }
   
   @Override
   public CurrentStates clone() {
	   CurrentStates c1 = new CurrentStates();
	   for(Node n: this.visitednodes) c1.visitednodes.add(n.clone());
	   for(Node n: this.NextNodes) c1.NextNodes.add(n.clone());
	   for(Node n: this.DelNodes) c1.DelNodes.add(n.clone());
	   c1.LastNode = this.LastNode.clone();
	   c1.time = this.time;
	   return c1;
   }
   
   /**
    * Add a node to the current set
    * @param n Node
    * @param in Inputs Object to get Geo-location for calculations
    * @return A new Current State Object with node added
    */
   public CurrentStates addNode(Node n, Inputs in) {
	   CurrentStates c1 = this.clone();
	   c1.visitednodes.add(n);
	   Coordinates j1 = in.getLocation(c1.LastNode);
	   c1.LastNode = n;
	   Coordinates j2 = in.getLocation(c1.LastNode);
	   double distance = j1.getDistance(j2);
	   int t1 = (int)(distance/Coordinates.speed);
	   t1 = Math.max(t1 + c1.time, in.getprepTime(n));
	   c1.time = t1;
	   c1.NextNodes.remove(n);
	   if(n.getType()) c1.NextNodes.add(n.flipNode());
	   else c1.DelNodes.remove(n);
	   return c1;
   }
   
   /**
    * Method to check if Last added node met the Delivery Time Criterion
    * @param in Inputs Object to get Geo-location for calculations
    * @return True if Delivery Time Criterion is met
    */
   public boolean checkfeasibilityn0(Inputs in) {
	   if(this.time > in.getDeliveryTime(this.LastNode)) return false;
	   else return true;
   }
   
   /**
    * Method to check if rest all of the delivery time criterion can be met 
    * @param in Inputs Object to get Geo-location for calculations
    * @return true if Rest all Deliveries can be done in Maximum Delivry Time after Last Node
    */
   public boolean checkfeasibilityn1(Inputs in) {
	   boolean b = true;
	   for(Node n1: this.DelNodes) {
		   if(!n1.pickup) {
			   Coordinates j1 = in.getLocation(this.LastNode);
			   int t = this.time + (int)(j1.getDistance(in.getLocation(n1))/Coordinates.speed);
			   if(t>in.getDeliveryTime(n1)) return false;
		   }
	   }
	   return b;
   }
   
   /**
    * Method to check if rest all of the current picked-up order pairs can be delivered in stipulated time
    * @param in Inputs Object to get Geo-location for calculations
    * @return true if Rest all permutation of picked-up order pairs can be Delivered within SLA
    */
   public boolean checkfeasibilityn2(Inputs in) {
	   ArrayList<Node> visdel = new ArrayList<Node>();
	   for(Node n1:this.NextNodes) {
		   if(!n1.pickup)visdel.add(n1);
	   }
	   
	   for(int i=0; i<visdel.size(); i++) {
		   for(int j=0; j<visdel.size(); j++) {
			   if(i!=j) {
				   Coordinates c0 = in.getLocation(this.LastNode);
				   Coordinates c1 = in.getLocation(visdel.get(i));
				   Coordinates c2 = in.getLocation(visdel.get(j));
				   int t1 = this.time + (int)(c0.getDistance(c1)/Coordinates.speed);
				   int t2 = t1 + (int)(c1.getDistance(c2)/Coordinates.speed);
				   if(t1>in.getDeliveryTime(visdel.get(i))||(t2>in.getDeliveryTime(visdel.get(j)))) return false;
			   }
		   }
	   }
	   return true;
   }

   @Override
   public int compareTo(Object arg0) {
	  CurrentStates c1 =   (CurrentStates) arg0;
	  if(c1.time < this.time) return 1;
	  else if (c1.time == this.time) return 0;
	  else return -1;
   }
   
   /**
    * Print Function to print Traversal
    */
   public void printcurr() {
	   for(Node n: visitednodes) {
		   System.out.print(n.toString());
		   System.out.print(">");
	   }
   }
}
