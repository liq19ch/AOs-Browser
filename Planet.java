/*
* Planet (subclass of AstronomicalObjects)
* Provides methods to set and get albedo of Planet
* @author CTH
* First version 18/02/2020
* Modified 16/03/2020
*/

import java.util.*;
import java.io.*;


class Planet extends AstronomicalObjects{
  // instance variable
  private double albedo;
  private Map<String, String> store = new HashMap<>();


  // constructors
  public Planet(){
  }

  //methods

  @Override
  public void setMap(){
    store.put("id", getId());
    store.put("ra", String.valueOf(getRa()));
    store.put("decl", String.valueOf(getDeclination()));
    store.put("magn", String.valueOf(getMagnitude()));
    store.put("distance", String.valueOf(getDistance()));
    store.put("albedo", String.valueOf(getAlbedo()));
  }

  /**
  * Get the map of the object
  * @return a Map
  */

  @Override
  public Map<String, String> getMap(){
    return store;
  }

  public void setAlbedo(double a){
    this.albedo = a;
  }

  /**
  * Get the albedo of the object
  * @return albedo (a double number)
  */

  public double getAlbedo(){
    return (albedo);
  }
}
