/*
* Messier (subclass of AstronomicalObjects)
* Provides methods to set and get constellation and description of Messier
* @author CTH
* First version 18/02/2020
* Modified 20/03/2020
*/

import java.util.*;
import java.io.*;


class Messier extends AstronomicalObjects{

  // instance variable
  private String constellation;
  private String description;    // optionally contains a description of the item
  private Map<String, String> store = new HashMap<>();

  // constructors
  public Messier(){
  }

  //methods
  @Override
  public void setMap(){
    store.put("id", getId());
    store.put("ra", String.valueOf(getRa()));
    store.put("decl", String.valueOf(getDeclination()));
    store.put("magn", String.valueOf(getMagnitude()));
    store.put("distance", String.valueOf(getDistance()));
    store.put("constellation",getConstellation());
    store.put("description",getDescription());
  }

  /**
  * Get the map of the object
  * @return a Map
  */

  @Override
  public Map<String, String> getMap(){
    return store;
  }


  public void setConstellation(String c){
    this.constellation = c;
  }

  /**
  * Get the constellation of the object
  * @return a constellation
  */

  public String getConstellation(){
    return (constellation);
  }

  public void setDescription(String d){
    this.description = d;
  }

  /**
  * Get the description of the object
  * @return description
  */

  public String getDescription(){
    return (description);
  }

}
