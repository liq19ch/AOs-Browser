/*
* QueryRunner
* Provides methods to check format and run query
* @author CTH
* First version 16/03/2020
* Modified 20/04/2020
*/

import java.util.*;
import java.io.*;


class QueryRunner {

  // instance
  private ArrayList<String> allQueries = new ArrayList<>(); // store lines with correct format
  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>();
  private ArrayList<QueryResults> result = new ArrayList<>();
  private ArrayList<String> errorMessage = new ArrayList<>();


  // constructors

  public QueryRunner(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
    this.allStar = allStar;
    this.allPlanet = allPlanet;
    this.allMessier = allMessier;
  }


  // methods

  /**
  * Get the error Messages
  * @return an ArrayList
  */

  public ArrayList<String> getErrorMessage(){
    return errorMessage;
  }

  /**
  * Read the query and check the format
  * @return an ArrayList with correct format queries
  */

  public ArrayList<String> checkFormat(ArrayList<String> allData){
    for (int i = 0; i < allData.size(); i++){
      String line = allData.get(i);
      line = line.replaceAll("^\\s+",""); // replace all leading blanks
        if (getFormat(line)) {
            allQueries.add(line); // add line with correct format
        }
    }
    return allQueries;
  }

  /**
  * Read the query and check the format
  * @return true or false
  */

  private boolean getFormat(String s){

    // set key words
    String[] objectType = {"messiers", "stars", "planets"};
    String[] starsKey = {"number","ra","distance","magn","decl","type","constellation"};
    String[] messiersKey = {"number","ra","distance","magn","decl","constellation","description"};
    String[] planetsKey = {"name","ra","distance","magn","decl","albedo"};

    String[] queryList = s.split("\\s+"); // replace all leading blanks
    int total = queryList.length;
    int left = (total-6) % 4; // calculate if it is the right length
    int and = Math.round((total-6) / 4); // store how many and in a query


    if(total == 2 || total>=6){

      String obName = queryList[1].toLowerCase(); // get name of objects

      // check select
      if(!queryList[0].toLowerCase().equals("select")){
        String e = s.trim() +" has unknown keyword: " + queryList[0].toLowerCase() + ".";
        errorMessage.add(e);
        return false;
      }

      // check name of object type
      if(!Arrays.asList(objectType).contains(obName)){
        String e = s.trim() + " has unknown type: " + queryList[1] + ".";
        errorMessage.add(e);
        return false;
      }

      if(total == 2){
        return true;
      }

      // check the length
      if(left != 0){
        String e = s.trim() + " has a wrong format.";
        errorMessage.add(e);
        return false;
      }

      // check where
      if(!queryList[2].toLowerCase().equals("where")){
        String e = s.trim() +" has unknown keyword: " + queryList[2] + ".";
        errorMessage.add(e);
        return false;
      }

      // check key words
      boolean doc = true;
      switch(obName){
        case "stars":
        doc = checkDataType(starsKey, queryList);
        break;

        case "messiers":
        doc = checkDataType(messiersKey, queryList);
        break;

        case "planets":
        doc = checkDataType(planetsKey, queryList);
        break;
      }


      if(!doc){
        String e = s.trim() + " has a wrong data type.";
        errorMessage.add(e);
        return false;
      }

      if(!checkOperator(queryList)){
        String e = s.trim() + " has a wrong operator.";
        errorMessage.add(e);
        return false;
      }

      if(!checkNumber(queryList)){
        String e = s.trim() + " has a wrong compare object.";
        errorMessage.add(e);
        return false;
      }

      if(and > 0){
        if(!checkAnd(queryList)){
          String e = s.trim() + " has a wrong connect word.";
          errorMessage.add(e);
          return false;
        }
      }return true;
    }
    return false;
  }

  /**
  * Check the format of compared type in a query
  * @return true or false
  */

  private boolean checkDataType(String[] key, String[] queryList){
    int i = 0;
    int total = queryList.length;
    int and = Math.round((total-6) / 4); // store how many and in a query

    do{
      if(!Arrays.asList(key).contains(queryList[4*i+3])){ // 4*i+3 as the compared data type position
        return false;
      }
      i++;
    }while(i <= and);
    return true;
  }

  /**
  * Check the format operator type in a query
  * @return true or false
  */

  private boolean checkOperator(String[] queryList){
    int total = queryList.length;
    int and = Math.round((total-6) / 4); // store how many and in a query
    String[] operator = {">","<","=",">=","<=","!="};
    int i = 0;
    do{
      if(!Arrays.asList(operator).contains(queryList[4*i+4])){ // 4*i+4 as the compared operator position
        return false;
      }
      i++;
    }while(i <= and);
    return true;
  }

  /**
  * Check the format of number in a query
  * @return true or false
  */

  private boolean checkNumber(String[] queryList){
    int total = queryList.length;
    int and = Math.round((total-6) / 4); // store how many and in a query
    int i = 0;
    boolean check = true;
    do{
      // skip if the compared type as name, number, type, constellation, and description
      if(queryList[4*i+3].equals("name") || queryList[4*i+3].equals("number")|| queryList[4*i+3].equals("type") || queryList[4*i+3].equals("constellation") || queryList[4*i+3].equals("description")){
        i++;
      }else{
        try{
          Double.parseDouble(queryList[4*i+5]); // 4*i+5 as the compared number position
          i++;
        }catch(Exception e){
          return false;
        }
      }
    }while(i <= and);
    return true;
  }

  /**
  * Check the format of and in a query
  * @return true or false
  */

  private boolean checkAnd(String[] queryList){
    int total = queryList.length;
    int and = Math.round((total-6) / 4); // store how many and in a query
    int i = 0;
    do{
      if(!queryList[4*i+6].toLowerCase().equals("and")){ // 4*i+6 as the and position if and exists
        return false;
      }else{
        i++;
      }
    }while(i < and);
    return true;
  }

  /**
  * Check the condition in a query
  * @return an ArrayList with the condition
  */

  private ArrayList<Condition> getCondition(String[] queryList){
    ArrayList<Condition> condition = new ArrayList<>();
    int total = queryList.length;
    int and = Math.round((total-6) / 4); // store how many and in a query

    int j = 0;
    do{
      Condition selectData = new Condition();
      selectData.setSort(queryList[4*j+3]);
      selectData.setOperator(queryList[4*j+4]);
      selectData.setNumber(queryList[4*j+5]);
      condition.add(selectData);
      j++;
    }while(j <= and);
    return condition;
  }

  /**
  * Run the query in allQueries
  * @return an ArrayList that stores QueryResults objects
  */

  public ArrayList<QueryResults> runAll(ArrayList<String> allQueries){
    for(int i = 0 ; i < allQueries.size(); i++){
      QueryResults q = run(allQueries.get(i));
      result.add(q);
    }
    return result;
  }

  /**
  * Run a query and find the answers
  * @return an QueryResults
  */

  public QueryResults run(String q){
      String[] queryList = q.split("\\s+");
      String typeOfObject = queryList[1].toLowerCase();
      QueryResults queryResult = new Query();
      int total = queryList.length;
      int and = Math.round((total-6) / 4); // store how many and in a query

      ArrayList<? extends AstronomicalObjects> all = new ArrayList<>();

      switch(typeOfObject){
      case "stars":
      all = allStar;
      break;
      case "messiers":
      all = allMessier;
      break;
      case "planets":
      all = allPlanet;
      break;
      }

      if(queryList.length == 2){ // return all ids
        for(int i = 0; i < all.size(); i++){
          queryResult.setIDs(all.get(i).getId());
        }
        return queryResult;
      }
      ArrayList<Condition> condition = getCondition(queryList); // get the condition of the query

      for (int i = 0; i < all.size(); i++){ // visit all objects to find answer
        String aType = null;
        String name = null;
        boolean isMatched = false;

        for(int k = 0; k < condition.size(); k++){ // visit all condition
          if(condition.get(k).getSort().equals("name") || condition.get(k).getSort().equals("number") || condition.get(k).getSort().equals("constellation") || condition.get(k).getSort().equals("type")){
            if(condition.get(k).getSort().equals("name")|| condition.get(k).getSort().equals("number")){ // switch name to id
              aType = "id";
            }else{
              aType = condition.get(k).getSort();
            }
            String compare = all.get(i).getMap().get(aType);
            String match = condition.get(k).getNumber();
            String op = condition.get(k).getOperator();

            switch(op){ // match the string
              case "=":
              if(compare.equals(match)){
                isMatched = true;
              }else{
                isMatched = false;
              }
              break;
              case "!=":
              if(!compare.equals(match)){
                isMatched = true;
              }else{
                isMatched = false;
              }
              break;
            }

            if(isMatched){
              continue;
            }else{
              break;
            }

          }else{
              aType = condition.get(k).getSort();

              double compare = Double.parseDouble(all.get(i).getMap().get(aType));
              double number = Double.parseDouble(condition.get(k).getNumber());
              String op = condition.get(k).getOperator();

              switch(op){
                case "<":
                if(compare < number){
                  isMatched = true;
                }else{
                  isMatched = false;
                }

                break;
                case ">":
                if(compare > number){
                  isMatched = true;
                }else{
                  isMatched = false;
                }
                break;
                case "=":
                if(compare == number){
                  isMatched = true;
                }else{
                  isMatched = false;
                }
                break;
                case ">=":
                if(compare >= number){
                  isMatched = true;
                }else{
                  isMatched = false;
                }
                break;
                case "<=":
                if(compare <= number){
                  isMatched = true;
                }else{
                  isMatched = false;
                }
                break;
              }
              if(isMatched){
                continue;
              }else{
                break;
              }
            }
          }
          if(isMatched){
            queryResult.setIDs(all.get(i).getId());
          }
        }
        return queryResult;
      }

      public String toString(ArrayList<String> allQueries){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for(int i = 0; i < result.size(); i++){
          sb.append(allQueries.get(i).trim() + ":\n");
          ArrayList<String> print = result.get(i).getIDs();
          sb.append("(" +print.size()+ " results are found)\n");
          for (int j = 0; j < print.size(); j++){
            sb.append(print.get(j)+"\n");
          }
        }
        sb.append("\n\n");
        sb.append("Summary:\n");
        for(int k = 0; k < result.size(); k++){
          sb.append(allQueries.get(k).trim() + ":  ");
          ArrayList<String> p = result.get(k).getIDs();
          sb.append(p.size()+ " results.\n");
        }
        if(errorMessage != null){
          for (String e : errorMessage){
            sb.append(e+"\n");
          }
        }
        return sb.toString();
      }

      /*
      * Condition
      * Provides methods to set conditions of query
      */

      private class Condition{
        // instance variable
        private String sort;
        private String operator;
        private String number;

        //constructors
        public Condition(){

        }

        //methods

        public void setSort(String sort){
          this.sort = sort;
        }

        /**
        * Get the sort to compare
        * @return sort
        */

        public String getSort(){
          return sort;
        }

        public void setOperator(String operator){
          this.operator = operator;
        }

        /**
        * Get the operator to compare
        * @return operator
        */

        public String getOperator(){
          return operator;
        }

        public void setNumber(String number){
          this.number = number;
        }

        /**
        * Get the number to compare
        * @return number
        */

        public String getNumber(){
          return number;
        }
      }


}
