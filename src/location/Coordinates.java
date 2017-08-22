package location;

/**
 * <h1> Location Coordinates </h1>
 * Class to define geocoordinates for a place.
 * Class also has methods to calculate distance between two places using Haversine formula
 * @author Ritesh Agrawal
 * @version 20170822
 *
 */
public class Coordinates {
  /**
   * Lattitude in Degrees
   */
  double lat;
  
  /**
   * Longitude in Degress
   */
  double lon;
  
  /**
   * Radius of Earth in KM - for haversine formula
   */
  private static final int EARTH_RADIUS = 6371;
  
  /**
   * Speed in KM per minute
   */
  public static final double speed = 0.33;
  
  
  /**
   * Constructor to instantiate class
   * @param x Lattitude
   * @param y Longitude
   */
  public Coordinates(double x, double y) {
	  this.lat = x;
	  this.lon = y;
  }
  
  /**
   * Getter for Lattitude
   * @return Lattitude in Degrees
   */
  public double getlattitude() {
	  return lat;
  }
  
  /**
   * Getter for Longitude
   * @return Longitude in Degrees
   */
  public double getlongitude() {
	  return lon;
  }
  
  /**
   * Method to get distance from another coordinate, using haversine formula 
   * @param s Geo-location of other points
   * @return Distance between this point and given point
   */
  public double getDistance(Coordinates s) {
	  double dLat  = Math.toRadians((s.lat - this.lat));
      double dLong = Math.toRadians((s.lon - this.lon));

      double startLat = Math.toRadians(s.lat);
      double endLat   = Math.toRadians(this.lat);

      double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

      return EARTH_RADIUS * c; // <-- d
  }
  
  /**
   * A method to calculate values for use in haversine formula
   * @param val Value for with Sine values needs to calculated
   * @return Sine Value
   */
  private static double haversin(double val) {
      return Math.pow(Math.sin(val / 2), 2);
  }
}
