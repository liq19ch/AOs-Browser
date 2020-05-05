/*
* Summaries
* Provides methods to answer the questions
* @author CTH
* First version 28/02/2020
*/

import java.util.*;
import java.io.*;

class Summaries{


  // instance variable
  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>();
  private Map<String, Integer> constellations = new HashMap<>();
  private String Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10;



  //constructors
  /**
  * Create a new Summaries
  */

  public Summaries(ArrayList<Star> allStar, ArrayList<Messier> allMessier, ArrayList<Planet> allPlanet){
    this.allStar = allStar;
    this.allMessier = allMessier;
    this.allPlanet = allPlanet;
  }

  // methods

  /**
  * Get the number of planets
  * @return the number
  */
  private int numberOfPlanets(){
    int number = allPlanet.size();
    return number;
  }

  /**
  * Get the number of messiers
  * @return the number
  */
  private int numberOfMessiers(){
    int number = allMessier.size();
    return number;
  }

  /**
  * Get the number of stars
  * @return the number
  */
  private int numberOfStars(){
    int number = allStar.size();
    return number;
  }

  /**
  * Get the id and value of max distance, min distance, brightest, and faintest objects
  * @return a DataSet that store all the results
  */
  public DataSet analysis(ArrayList<? extends AstronomicalObjects> a){
    double maxDis = Double.MIN_VALUE;
    double faintest = Double.MIN_VALUE;
    double minDis = Double.MAX_VALUE;
    double brightest = Double.MAX_VALUE;
    ArrayList<Integer> maxIndex = new ArrayList<>();
    ArrayList<Integer> minIndex = new ArrayList<>();
    ArrayList<Integer> brightestIndex = new ArrayList<>();
    ArrayList<Integer> faintestIndex = new ArrayList<>();
    DataSet result = new DataSet();


    for (int i = 0 ; i < a.size() ; i++){
      double dis = ((AstronomicalObjects)a.get(i)).getDistance();
      double mag = ((AstronomicalObjects)a.get(i)).getMagnitude();


      if (minDis > dis){
        // only stroe the min one
        minIndex.clear();
        minDis = dis;
        minIndex.add(i);
      }else if (minDis == dis){
        minIndex.add(i);
      }if (maxDis < dis){
        maxIndex.clear();
        maxDis = dis;
        maxIndex.add(i);
      }else if (maxDis == dis){
        maxIndex.add(i);
      }if (faintest < mag){
        faintestIndex.clear();
        faintest = mag;
        faintestIndex.add(i);
      }else if (faintest == mag){
        faintestIndex.add(i);
      }if (brightest > mag){
        brightestIndex.clear();
        brightest = mag;
        brightestIndex.add(i);
      }else if (brightest == mag){
        brightestIndex.add(i);
      }
    }
      // set the DataSet object
      result.setFaintest(faintest);
      result.setBrightest(brightest);
      result.setMinDis(minDis);
      result.setMaxDis(maxDis);
      result.setMaxIndex(maxIndex);
      result.setMinIndex(minIndex);
      result.setBrightestIndex(brightestIndex);
      result.setFaintestIndex(faintestIndex);
      return result;
  }

  /**
  * Get the id list of min distance
  * @return a ArrayList that store all the ID
  */
  private ArrayList<String> nearestToEarth(){
    DataSet st = analysis(allStar); // get the eight variables from allStar ArrayList
    DataSet me = analysis(allMessier); // get the eight variables from allMessier ArrayList
    DataSet pl = analysis(allPlanet); // get the eight variables from allPlanet ArrayList

    ArrayList<String> result = new ArrayList<>();
    if(st.getMinDis() < me.getMinDis() && st.getMinDis() < pl.getMinDis()){
      ArrayList<Integer> starPos = st.getMinIndex();
      for(int i : starPos){
        result.add(allStar.get(i).getId());
      }
    }else if (me.getMinDis() < st.getMinDis() && me.getMinDis() < pl.getMinDis()){
      ArrayList<Integer> messierPos = me.getMinIndex();
      for(int i : messierPos){
        result.add(allMessier.get(i).getId());
      }
    }else if (pl.getMinDis() < st.getMinDis() && pl.getMinDis() < me.getMinDis()){
      ArrayList<Integer> planetPos = pl.getMinIndex();
      for(int i : planetPos){
        result.add(allPlanet.get(i).getId());
      }
    }return result;
  }

  /**
  * Get the id list of max distance
  * @return a ArrayList that store all the ID
  */
  private ArrayList<String> furthestToEarth(){
    DataSet st = analysis(allStar);
    DataSet me = analysis(allMessier);
    DataSet pl = analysis(allPlanet);
    ArrayList<String> result = new ArrayList<>();
    if(st.getMaxDis() > me.getMaxDis() && st.getMaxDis() > pl.getMaxDis()){
      ArrayList<Integer> starPos = st.getMaxIndex();
      for(int i : starPos){
        result.add(allStar.get(i).getId());
      }
    }else if (me.getMaxDis() > st.getMaxDis() && me.getMaxDis() > pl.getMaxDis()){
      ArrayList<Integer> messierPos = me.getMaxIndex();
      for(int i : messierPos){
        result.add(allMessier.get(i).getId());
      }
    }else if (pl.getMaxDis() > st.getMaxDis() && pl.getMaxDis() > me.getMaxDis()){
      ArrayList<Integer> planetPos = pl.getMaxIndex();
      for(int i : planetPos){
        result.add(allPlanet.get(i).getId());
      }
    }return result;
  }

  /**
  * Provide the format of printing an ArrayList
  * @return a String that split the objects in an ArrayList by ","
  */
  private String toPrintArray(ArrayList<String> a){
    StringBuilder sb = new StringBuilder();
    if(a != null && a.size() > 0){
      for(int i = 0 ; i < a.size(); i++) {
        sb.append(i == 0 ? a.get(i) : "," + a.get(i));
      }
    }return sb.toString();
  }

  /**
  * Get the numbers of constellations
  * @return the number
  */
  private int countConstellations(){
    for (Star s : allStar){
      if (!constellations.containsKey(s.getConstellation())){ // if the map doesn't have the constellation as the key, set it and set the value as one
        constellations.put(s.getConstellation(),1);
      }else{
        constellations.put(s.getConstellation(),(constellations.get(s.getConstellation()))+1);// else add 1 on its value
      }
    }for (Messier m : allMessier){
      if(!constellations.containsKey(m.getConstellation())){
        constellations.put(m.getConstellation(),1);
      }else{
        constellations.put(m.getConstellation(),(constellations.get(m.getConstellation()))+1);
      }
    }
    return constellations.size();
  }

  /**
  * Get the name of the largest constellation
  * @return the name list
  */
  private ArrayList<String> largestConstellations(){
    String name;
    int max = Integer.MIN_VALUE;
    ArrayList<String> cons = new ArrayList<>();
    for (Map.Entry<String, Integer> n : constellations.entrySet()){ // use previous map to find the largest value
      if (n.getValue() > max){
        cons.clear();
        max = n.getValue();
        name = n.getKey();
        cons.add(name);
      }else if (n.getValue() == max){
        name = n.getKey();
        cons.add(name);
      }
    }return cons;
}

  /**
  * Store ten answers
  */

  public void getSummaries(){
    Q1 = String.valueOf(numberOfPlanets());
    Q2 = String.valueOf(numberOfMessiers());
    Q3 = String.valueOf(numberOfStars());
    Q4 = toPrintArray(nearestToEarth());
    Q5 = toPrintArray(furthestToEarth());

    // get the data of star
    DataSet starSet = analysis(allStar);
    ArrayList<Integer> minList = starSet.getMinIndex(); // get the min index
    ArrayList<String> minAns = new ArrayList<>();
    for(int i : minList){
      minAns.add(allStar.get(i).getId());
    }
    Q6 = toPrintArray(minAns);

    ArrayList<Integer> brList = starSet.getBrightestIndex(); // get the brightest index
    ArrayList<String> brAns = new ArrayList<>();
    for(int j : brList){
      brAns.add(allStar.get(j).getId());
    }
    Q7 = toPrintArray(brAns);

    ArrayList<Integer> faList = starSet.getFaintestIndex(); // get the faintest index
    ArrayList<String> faAns = new ArrayList<>();
    for(int k : faList){
      faAns.add(allStar.get(k).getId());
    }
    Q8 = toPrintArray(faAns);

    Q9 = String.valueOf(countConstellations());
    Q10 = toPrintArray(largestConstellations());
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Q1. " + Q1 + " Q2. " + Q2 +" Q3. " + Q3 + " Q4. " + Q4 + " Q5. " + Q5 + " Q6. " + Q6 + " Q7." + Q7 + " Q8. " + Q8 + " Q9. " + Q9 + " Q10. "+Q10);
    return sb.toString();
  }


}
