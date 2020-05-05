/*
* DataSet
* Set and Get the min and max
* @author CTH
* First version 16/03/2020
* Modified 10/04/2020
*/

import java.util.*;


class DataSet{
  // instance variable

  private double maxDis, minDis, faintest, brightest;
  private ArrayList<Integer> maxIndex, minIndex, brightestIndex, faintestIndex;

  // constructors
  public DataSet(){

  }

  // methods

  public void setMaxIndex(ArrayList<Integer> maxIndex){
    this.maxIndex = maxIndex;
  }

  /**
  * Get the index of the objects with max distance
  * @return maxIndex
  */

  public ArrayList<Integer> getMaxIndex (){
    return maxIndex;
  }

  public void setMinIndex(ArrayList<Integer> minIndex){
    this.minIndex = minIndex;
  }

  /**
  * Get the index of the objects with min distance
  * @return minIndex
  */


  public ArrayList<Integer> getMinIndex(){
    return minIndex;
  }

  public void setBrightestIndex(ArrayList<Integer> brightestIndex){
    this.brightestIndex = brightestIndex;
  }

  /**
  * Get the index of the objects that is brightest
  * @return brightestIndex
  */

  public ArrayList<Integer> getBrightestIndex(){
    return brightestIndex;
  }

  public void setFaintestIndex(ArrayList<Integer> faintestIndex){
    this.faintestIndex = faintestIndex;
  }

  /**
  * Get the index of the objects that is faintest
  * @return faintestIndex
  */


  public ArrayList<Integer> getFaintestIndex(){
    return faintestIndex;
  }
  public void setMaxDis(double maxDis){
    this.maxDis = maxDis;
  }

  /**
  * Get the the object distance that is max
  * @return maxDis
  */

  public double getMaxDis(){
    return maxDis;
  }

  public void setMinDis(double minDis){
    this.minDis = minDis;
  }


  /**
  * Get the the object distance that is min
  * @return minDis
  */

  public double getMinDis(){
    return minDis;
  }

  public void setBrightest(double brightest){
    this.brightest = brightest;
  }

  /**
  * Get the the object magnitude that is min
  * @return brightest
  */

  public double getBrightest(){
    return brightest;
  }

  public void setFaintest(double faintest){
    this.faintest = faintest;
  }

  /**
  * Get the the object magnitude that is max
  * @return faintest
  */

  public double getFaintest(){
    return faintest;
  }

}
