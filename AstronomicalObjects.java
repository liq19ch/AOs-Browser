/*
* AstronomicalObjects
* Provides methods to set and get RA, declination, magnitude, and distance
* @author CTH
* First version 18/02/2020
* Modified 16/03/2020
*/

import java.util.*;
import java.io.*;

abstract class AstronomicalObjects{
  // instance varible
  private String id;
  private double ra;           //  right ascension
  private double declination;
  private double magnitude;
  private double distance;    // distance from the Earth

  // abstract methods to be inherited by subclass

  public abstract Map<String, String> getMap();
  public abstract void setMap();

  // methods

  public void setId(String i){
    this.id = i;
  }

  /**
  * Get the ID of the object
  * @return ID
  */

  public String getId(){
    return id;
  }

  public void setRa(double r){
    this.ra = r;
  }

  /**
  * Get the RA of the object
  * @return RA (a double number)
  */

  public double getRa(){
    return (ra);
  }

  public void setDeclination(double dec){
    this.declination = dec;
  }

  /**
  * Get the declination of the object
  * @return declination
  */

  public double getDeclination(){
    return (declination);
  }

  public void setMagnitude(double mag){
    this.magnitude = mag;
  }

  /**
  * Get magnitude of the object
  * @return magnitude
  */

  public double getMagnitude(){
    return (magnitude);
  }

  public void setDistance(double dis){
    this.distance = dis;
  }

  /**
  * Get distance of the object
  * @return distance
  */

  public double getDistance(){
    return (distance);
  }

  public String toString(){
    return ("RA:\n"+ra+"\ndeclination:\n"+declination+"\nmagnitude:\n"+magnitude+"\ndistance:"+distance);
  }


}
