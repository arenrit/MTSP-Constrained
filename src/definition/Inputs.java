package definition;

import formulation.Node;
import location.Coordinates;

/**
 * <h1> Input Class </h1>
 * Class stores all the information of the delivery and pickup Restaurant locations for all the orders
 * Class also has preparation time and delivery time stored
 * In the current version, There is only Max Delivery time at customer End, and Min Preparation time at Restaurant
 * However same formulation can be used to add Max Pick-up time at Restaurant, and Min Delivery time at Customer End 
 * @author Ritesh Agrawal
 * @version 20170822
 *
 */
public class Inputs {
	
	  /**
	   * Origin of Biker - Aman
	   */
	  Coordinates initloc;
	  
	  /**
	   * Coordinates of Restaurant Locations
	   */
	  Coordinates[] restloc;
	  
	  /**
	   * Coordinates of customer Locations
	   */
	  Coordinates[] custloc;
	  
	  /**
	   * Preparation time for customers 
	   */
	  int[] preptime;
	  
	  /**
	   * Maximum Delivery time at customer - Service Level
	   */
	  int[] maxDeliverytime;
	  
	  /**
	   * Constructor to instantiate Object with null values
	   * @param n Number of Customers to serve
	   */
	  public Inputs(int n) {
		  restloc = new Coordinates[n];
		  custloc = new Coordinates[n];
		  preptime = new int[n];
		  maxDeliverytime = new int[n];
		  for(int i=0; i<n; i++) {
			  preptime[i] = 0;
			  maxDeliverytime[i] = Integer.MAX_VALUE;
		  }
	  }
	  
	  /**
	   * Method to add Initial point of biker
	   * @param c Coordinates of initial point
	   */
	  public void addInit(Coordinates c) {
		  initloc = c;
	  }
	  
	  /**
	   * Method to add pick-up and delivery location for nth customer order
	   * @param pic Pick-up Restaurant Location
	   * @param drop Delivery location
	   * @param index Customer order index
	   */
	  public void addPair(Coordinates pic, Coordinates drop, int index) {
		  restloc[index] = pic;
		  custloc[index] = drop;
	  }
	  
	  /**
	   * Method to add preparation time for nth Order
	   * @param time Preparation time at restaurant
	   * @param index Customer Order Index
	   */
	  public void addPrepTime(int time, int index) {
		  preptime[index] = time;
	  }
	  
	  /**
	   * Method to add Maximum Delivery time for nth order
	   * @param time Maximum Delivery time for nth Order
	   * @param index Customer Order Index
	   */
	  public void addMaxDeliveryTime(int time, int index) {
		  maxDeliverytime[index] = time;
	  }
	  
	  /**
	   * Method to get location of {@link formulation.Node} n
	   * @param n Node for which location is required
	   * @return Geocoordinates for Node
	   * @see formulation.Node
	   */
	  public Coordinates getLocation(Node n) {
		  if(n.getType()) return this.restloc[n.getIndex()];
		  else return this.custloc[n.getIndex()];
	  }
	  
	  /**
	   * Method to get Maximum Delivery time for {@link formulation.Node} n.
	   * In current version, there is no Maximum Delivery Time for Pick-up nodes
	   * However this can be easily added here 
	   * @param n Node for which Maximum Delivery Time is required
	   * @return Maximum Delivery time required
	   * @see formulation.Node
	   */
	  public int getDeliveryTime(Node n) {
		  if(n.getType()) return Integer.MAX_VALUE;
		  else return this.maxDeliverytime[n.getIndex()];
	  }
	  
	  /**
	   * Method to get Preparation time, i.e. Minimum Time for {@link formulation.Node} n.
	   * In current version, there is no Minimum Delivery Time for Delivery nodes
	   * However this can be easily added here  - Customer Not At Home
	   * @param n Node for which Preparation Time is required
	   * @return Preparation time required
	   * @see formulation.Node
	   */
	  public int getprepTime(Node n) {
		  if(n.getType()) return this.preptime[n.getIndex()]; 
		  else return 0;
	  }
	  
	  /**
	   * Method to get number of customer in configuration
	   * @return Number of Customers
	   */
	  public int getnumcust() {
		  return this.maxDeliverytime.length;
	  }
	  
	  
	  /**
	   * Getter method for initial location
	   * @return Geo-coordinates for initial location of biker
	   */
	  public Coordinates getInitLoc() {
		  return this.initloc;
	  }
}
