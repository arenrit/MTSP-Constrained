package formulation;

/**
 * <h1> Node </h1>
 * Class represents a node as index of order and a boolean whether node is pik-up or delivery 
 * @author Ritesh Agrawal
 * @version 20170822
 *
 */
public class Node {
	/**
	 * Customer Order Index 
	 */
	int index;
	
	/**
	 * true if node is for Pick-up i.e. Restaurant
	 */
	boolean pickup;
	
	/**
	 * Constructor to instantiate object
	 * @param n Customer Order Index
	 * @param b true if node is for Pick-up i.e. Restaurant
	 */
	public Node(int n, boolean b) {
		this.index = n;
		this.pickup = b;
	}
	
	@Override
	public int hashCode(){
		if(pickup) return index*10+1;
		else return index*10;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Node)) return false;
		else {
			Node Other = (Node) o;
			if((Other.index==this.index) &&(Other.pickup==this.pickup)) return true;
			else return false;
		}
	}
	
	@Override
	public Node clone() {
		return new Node(this.index, this.pickup);
	}
	
	/**
	 * Getter method for Customer Order Index
	 * @return Customer Order Index
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Getter Method for Node Type - Pick-up or delivery
	 * @return true if node is for Pick-up i.e. Restaurant
	 */
	public boolean getType() {
		return this.pickup;
	}
	
	/**
	 * Method to get Pick-up Node for Delivery and Delivery Node for Pick-up 
	 * @return Complementary Node for this Node
	 */
	public Node flipNode() {
		return new Node(this.index, !this.pickup);
	}
	
	@Override
	public String toString() {
		if(pickup) return "R"+index;
		else return "C" + index;
	}
}
