/*
* Star (subclass of AstronomicalObjects)
* Provides methods to set and get constellation and type of Star
* @author CTH
* First version 18/02/2020
* Modified 16/03/2020
*/


import java.util.*;
import java.io.*;


class Star extends AstronomicalObjects{

  // instance variable
  private String type;
  private String constellation;
  private Map<String, String> store = new HashMap<>();

  // constructors
  public Star(){
  }


  // methods

  @Override
  public void setMap(){
    store.put("id", getId());
    store.put("ra", String.valueOf(getRa()));
    store.put("decl", String.valueOf(getDeclination()));
    store.put("magn", String.valueOf(getMagnitude()));
    store.put("distance", String.valueOf(getDistance()));
    store.put("constellation",getConstellation());
    store.put("type",getType());
  }

  /**
  * Get the map of the object
  * @return a Map
  */

  @Override
  public Map<String, String> getMap(){
    return store;
  }

  public void setType(String t){
    this.type = t;
  }

  /**
  * Get the type of the object
  * @return a type
  */

  public String getType(){
    return (type);
  }

  public void setConstellation(String c){
    this.constellation = c;
  }

  /**
  * Get the constellation of the object
  * @return a type
  */

  public String getConstellation(){
    return (constellation);
  }
}
