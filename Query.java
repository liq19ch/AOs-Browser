/*
* Query
* Implements QueryResults to provide methods to store answers
* @author CTH
* First version 18/03/2020
*/

import java.util.*;
import java.io.*;

class Query implements QueryResults{
  // instance variable
  private ArrayList<String> result = new ArrayList<>();

  // methods

  public void setIDs(String id){
    result.add(id);
  }

  /**
  * Get the IDs of the answers
  * @return a ArrayList
  */

  public ArrayList<String> getIDs(){
    return result;
  }


  public String toString(){
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < result.size(); i++){
      sb.append(result.get(i) + "\n");
    }
    return sb.toString();
  }

}
