/*
* AOB
* Provides methods
* @author CTH
* First version 25/02/2020
* Second version 16/03/2020
* Third version 20/04/2020
*/

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class AOB{

  // instance variable
  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>();
  Summaries summaries = null; // new Summaries to call the function in Summaries class
  QueryRunner queryRunner = null; // new QueryRunner to call the function
  FrameButton fb = null;
  private ArrayList<String> allQueries = new ArrayList<>();

  private String fStar, fMessier, fPlanets; // name of the file


  // constructors
  /**
  * Create a new AOB
  */
  public AOB(String fStar, String fMessier, String fPlanets){
    this.fStar = fStar;
    this.fMessier = fMessier;
    this.fPlanets = fPlanets;
  }

  // methods

  // get the answers
  public void getSummaryStatistics(){
    try{
      getData();
      summaries = new Summaries(allStar, allMessier, allPlanet);
      summaries.getSummaries();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  /**
  * ToString for summaries
  */

  public String toString(){
    if(summaries == null){
      return "";
    }
    return summaries.toString();
  }

  /**
  * ToString for query only
  */

  public String queryToString(){
    if(queryRunner == null){
      return "";
    }
    return queryRunner.toString(allQueries);
  }

  // get the data from the files and update to three ArrayLists
  public void getData()throws Exception{
    getStars(readFile(fStar));
    getPlanets(readFile(fPlanets));
    getMessiers(readFile(fMessier));
  }

  /**
  * Read the file and save the text in an ArrayList
  * @return allData
  */

  private ArrayList<String> readFile(String name) throws Exception{
    ArrayList<String> allData = new ArrayList<String>();
    File file = new File (name);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String text;

    while((text = reader.readLine()) != null){
       // replace all the special characters with the blank
       text = text.replaceAll("[^(a-zA-Z0-9\\.\\-\\'\\>\\<\\=\\!)]"," ");
       if (!text.isEmpty() && text.length()>=0) {
          allData.add(text); // store the text into an ArrayList
        }
      }
      reader.close();
      return allData;
  }

  /**
  * Read the stored data and add ones with correct format to allQueries
  */
  private void getQuery(ArrayList<String> allData)throws Exception{
    queryRunner = new QueryRunner(allStar, allPlanet, allMessier);
    allQueries = queryRunner.checkFormat(allData);
  }

  /**
  * Run the user interface
  */
  private void runFrame(){
    try{
      getData();
      EventQueue.invokeLater(new Runnable(){
        public void run(){
          fb = new FrameButton(allStar, allPlanet, allMessier);
          fb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          fb.setVisible(true);
          }
        });
      }
      catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  /**
  * run all query to give the answers
  */
  private void runQuery(){
    try{
      getData();
      queryRunner.runAll(allQueries);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  /**
  * Read the stored data and split them to set the Planet object
  */
  private void getPlanets(ArrayList<String> allData)throws Exception{
    String eMessage = "Please give the correct format in planet file.";
    for (int i = 0; i < allData.size(); i++){
      String line = allData.get(i);
      line = line.replaceAll("^\\s+",""); // replace all leading blanks

      // split the data with white spaces
      String[] planetsList = line.split("\\s+");
      Planet planet = new Planet(); // create a new Planet

      if(planetsList.length != 6){
        throw new Exception(eMessage);
      }

      try{
        // set the variable
        planet.setId(planetsList[0].trim());
        planet.setRa(Double.parseDouble(planetsList[1].trim()));
        planet.setDeclination(Double.parseDouble(planetsList[2].trim()));
        planet.setMagnitude(Double.parseDouble(planetsList[3].trim()));
        planet.setDistance(Double.parseDouble(planetsList[4].trim()));
        planet.setAlbedo(Double.parseDouble(planetsList[5].trim()));
        planet.setMap();
        allPlanet.add(planet);// add the object to the list

      }catch(Exception e){
        throw new Exception(eMessage);
      }
    }
  }

  /**
  * Read the stored data and split them to set the Star object
  */

  private void getStars(ArrayList <String> allData)throws Exception{
    String eMessage = "Please give the correct format in stars file.";
    for (int i = 0; i < allData.size(); i++){
      String line = allData.get(i);
      line = line.replaceAll("^\\s+",""); // replace all leading blanks

      // split the data with blanks
      String[] starsList = line.split("\\s+");
      Star star = new Star(); // creat a new Star

      if(starsList.length != 7 ){
        throw new Exception(eMessage);
      }
      try{
        // set the variable
        star.setId(starsList[0].trim());
        star.setRa(Double.parseDouble(starsList[1].trim()));
        star.setDeclination(Double.parseDouble(starsList[2].trim()));
        star.setMagnitude(Double.parseDouble(starsList[3].trim()));
        star.setDistance(Double.parseDouble(starsList[4].trim()));
        star.setType(starsList[5].trim());
        star.setConstellation(starsList[6].trim());
        star.setMap();
        allStar.add(star);
      }catch(Exception e){
        throw new Exception(eMessage);
      }
    }
  }

  /**
  * Read the stored data and split them to set the Messier object
  */
  private void getMessiers(ArrayList <String> allData)throws Exception{
    String eMessage = "Please give the correct format in messier file.";
    for (int i = 0; i < allData.size(); i++){
      String line = allData.get(i);
      line = line.replaceAll("^\\s+",""); // replace all leading blanks

      // split the data with blanks
      String[] messiersList = line.split("\\s+");
      Messier messier = new Messier(); // create a new Messier

      if(messiersList.length < 6){
        throw new Exception(eMessage);
      }
      try{
        // set the variable
        messier.setId("M" + messiersList[0].trim());
        messier.setRa(Double.parseDouble(messiersList[1].trim()));
        messier.setDeclination(Double.parseDouble(messiersList[2].trim()));
        messier.setMagnitude(Double.parseDouble(messiersList[3].trim()));
        messier.setDistance(Double.parseDouble(messiersList[4].trim()));
        messier.setConstellation(messiersList[5].trim());

        if(messiersList.length > 7){
          StringBuilder sb = new StringBuilder(); // if the length of list is more than seven, string them together
          for(int j = 6; j < messiersList.length ; j++){
            sb.append(messiersList[j]+" ");
          }messier.setDescription(sb.toString().trim()); // set as the description
        }else if(messiersList.length == 7){
          messier.setDescription(messiersList[6]);
        }
        messier.setMap();
        allMessier.add(messier);
      }catch(Exception e){
        throw new Exception(eMessage);
      }
    }
  }


  // main function
  public static void main(String[] args){
    if(args.length != 3){
      System.out.println("Please give three files.");
      return;
    }

    try{
      AOB aob = new AOB (args[0], args[1], args[2]); // create a new AOB and use four args in the cmd as the parameters
      aob.runFrame();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
}
