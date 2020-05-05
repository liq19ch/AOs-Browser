/*
* FrameSearch
* The frame for the searchButton in FrameButton
* @author CTH
* First version 09/04/2020
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.TableColumnModel;

public class FrameSearch extends JFrame{

  //instance variables

  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>();

  private JTable brightestS, brightestP, brightestM;
  private JTable faintestS, faintestP, faintestM;
  private JTable furthestS, furthestP, furthestM;
  private JTable nearestS, nearestP, nearestM;
  private JPanel mainPanel, buttonPanel, brightestPanel, faintestPanel, nearestPanel, furthestPanel;
  private JButton brightest, faintest, furthest, nearest;
  private Summaries sum = new Summaries(allStar, allMessier, allPlanet);
  private JLabel SLabel = new JLabel("Star:");
  private JLabel PLabel = new JLabel("Planet:");
  private JLabel MLabel = new JLabel("Messier:");

  public FrameSearch(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
    this.allStar = allStar;
    this.allPlanet = allPlanet;
    this.allMessier = allMessier;
    setTitle("SearchMinAndMax");
    setSize(480, 150);

    mainPanel = new JPanel(); // create a main panel
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    mainPanel.setLayout(new GridLayout(0, 1));

    buttonPanel = new JPanel(); // create a button panel
    brightest = new JButton("Brightest");
    // create button and add actions
    brightest.addActionListener(new SearchAction());
    faintest = new JButton("Faintest");
    faintest.addActionListener(new SearchAction());
    furthest = new JButton("Furthest");
    furthest.addActionListener(new SearchAction());
    nearest = new JButton("Nearest");
    nearest.addActionListener(new SearchAction());

    // add to buttonpanel
    buttonPanel.add(brightest);
    buttonPanel.add(faintest);
    buttonPanel.add(furthest);
    buttonPanel.add(nearest);
    mainPanel.add(buttonPanel); // add button panel to main panel

    add(mainPanel, BorderLayout.CENTER); // add to the frame
  }

  /*
  * SearchAction
  * Implements ActionListener to override the button action
  */

  private class SearchAction implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      if (e.getSource() == brightest){
        // create BrightestFrame when the brightest button is clicked
        BrightestFrame bf = new BrightestFrame(allStar, allPlanet, allMessier);
        bf.setVisible(true);
        bf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }else if (e.getSource() == faintest){
        // create FaintestFrame when the faintest button is clicked
        FaintestFrame ff = new FaintestFrame(allStar, allPlanet, allMessier);
        ff.setVisible(true);
        ff.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }else if (e.getSource() == furthest){
        // create FurthestFrame when the furthest button is clicked
        FurthestFrame ff = new FurthestFrame(allStar, allPlanet, allMessier);
        ff.setVisible(true);
        ff.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }else if (e.getSource() == nearest){
        // create NearestFrame when the nearest button is clicked
        NearestFrame nf = new NearestFrame(allStar, allPlanet, allMessier);
        nf.setVisible(true);
        nf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    }
  }

  /*
  * BrightestFrame
  * The frame for the brightest button in FrameSearch
  */

  private class BrightestFrame extends JFrame{
    public BrightestFrame(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
      setTitle("BrightestObjects");
      setSize(650, 450);

      brightestPanel = new JPanel();
      brightestPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
      brightestPanel.setLayout(new GridLayout(0, 1));

      brightestPanel.add(SLabel); // add the label
      starData(allStar, "brightest"); // find the brightest star objects

      brightestPanel.add(PLabel);
      planetData(allPlanet, "brightest"); // find the brightest planets objects

      brightestPanel.add(MLabel);
      messierData(allMessier, "brightest"); // find the brightest messiers objects

      add(brightestPanel, BorderLayout.CENTER);
    }
  }

  /*
  * FaitnestFrame
  * The frame for the faintest button in FrameSearch
  */

  private class FaintestFrame extends JFrame{
    public FaintestFrame(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
      setTitle("FaintestObjects");
      setSize(650, 450);

      faintestPanel = new JPanel();
      faintestPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
      faintestPanel.setLayout(new GridLayout(0, 1));

      faintestPanel.add(SLabel);
      starData(allStar, "faintest");

      faintestPanel.add(PLabel);
      planetData(allPlanet, "faintest");

      faintestPanel.add(MLabel);
      messierData(allMessier, "faintest");

      add(faintestPanel, BorderLayout.CENTER);
    }
  }

  /*
  * FurthestFrame
  * The frame for the furthest button in FrameSearch
  */
  private class FurthestFrame extends JFrame{
    public FurthestFrame(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
      setTitle("FurthestObjects");
      setSize(650, 450);

      furthestPanel = new JPanel();
      furthestPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
      furthestPanel.setLayout(new GridLayout(0, 1));

      furthestPanel.add(SLabel);
      starData(allStar, "max");

      furthestPanel.add(PLabel);
      planetData(allPlanet, "max");

      furthestPanel.add(MLabel);
      messierData(allMessier, "max");

      add(furthestPanel, BorderLayout.CENTER);
    }
  }

  /*
  * NearestFrame
  * The frame for the nearest button in FrameSearch
  */

  private class NearestFrame extends JFrame{
    public NearestFrame(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
      setTitle("NearestObjects");
      setSize(650, 450);

      nearestPanel = new JPanel();
      nearestPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
      nearestPanel.setLayout(new GridLayout(0, 1));

      nearestPanel.add(SLabel);
      starData(allStar, "min");

      nearestPanel.add(PLabel);
      planetData(allPlanet, "min");

      nearestPanel.add(MLabel);
      messierData(allMessier, "min");

      add(nearestPanel, BorderLayout.CENTER);
    }
  }

  /*
  * starData
  * search and print the results of Star objects
  */

  private void starData(ArrayList<Star> allStar, String s){
    DataSet setStar = sum.analysis(allStar); // find the index of the max and min values
    JTable area = null;
    ArrayList<Integer> sInd = new ArrayList<>();
    JPanel p = null;
    String[] column = {"ID","Ra","Declination","Magnitude", "Distance", "Constellation", "Type"};


    switch(s){
      case "brightest":
      sInd = setStar.getBrightestIndex();
      p = brightestPanel;
      break;

      case "faintest":
      sInd = setStar.getFaintestIndex();
      p = faintestPanel;
      break;

      case "max":
      sInd = setStar.getMaxIndex();
      p = furthestPanel;
      break;

      case "min":
      sInd = setStar.getMinIndex();
      p = nearestPanel;
      break;
    }
    Object[][] rowData = new Object[sInd.size()][7]; // set row number

    int i = 0;
    for(int j : sInd){
      Star st = new Star();
      st = allStar.get(j);
      Object[] data = {st.getId(),st.getRa(),st.getDeclination(),st.getMagnitude(),st.getDistance(),st.getConstellation(),st.getType()};
      rowData[i] = data; // add data into table row
      i++;
    }

    switch(s){
      case "brightest":
      brightestS = new JTable(rowData, column); // initialize table
      area = brightestS;
      break;

      case "faintest":
      faintestS = new JTable(rowData, column);
      area = faintestS;
      break;

      case "max":
      furthestS = new JTable(rowData, column);
      area = furthestS;
      break;

      case "min":
      nearestS = new JTable(rowData, column);
      area = nearestS;
      break;
    }
    TableColumnModel col = area.getColumnModel();
    // set the width of table columns
    col.getColumn(0).setPreferredWidth(30);
    col.getColumn(6).setPreferredWidth(30);
    // add scroll to table
    JScrollPane scroll = new JScrollPane(area);
    scroll.setPreferredSize(new Dimension(250,80));
    scroll.setAlignmentX(LEFT_ALIGNMENT);
    area.setEnabled(false);
    // add table to panel
    p.add(scroll);
  }

  /*
  * planetData
  * search and print the results of Planet objects
  */

  private void planetData(ArrayList<Planet> allPlanet, String s){
    DataSet setPlanet = sum.analysis(allPlanet); // find the index of min and max values
    JTable area = null;
    ArrayList<Integer> pInd = null;
    JPanel p = null;
    String[] column = {"ID","Ra","Declination","Magnitude", "Distance", "Albedo"};


    switch(s){
      case "brightest":
      pInd = setPlanet.getBrightestIndex();
      p = brightestPanel;
      break;

      case "faintest":
      pInd = setPlanet.getFaintestIndex();
      p = faintestPanel;
      break;

      case "max":
      pInd = setPlanet.getMaxIndex();
      p = furthestPanel;
      break;

      case "min":
      pInd = setPlanet.getMinIndex();
      p = nearestPanel;
      break;
    }

    Object[][] rowData = new Object[pInd.size()][6];
    int i = 0;
    for(int j: pInd){
      Planet pl = new Planet();
      pl = allPlanet.get(j);
      Object[] data = {pl.getId(),pl.getRa(),pl.getDeclination(),pl.getMagnitude(),pl.getDistance(),pl.getAlbedo()};
      rowData[i] = data;
      i++;
    }

    switch(s){
      case "brightest":
      brightestP = new JTable(rowData, column);
      area = brightestP;
      break;

      case "faintest":
      faintestP = new JTable(rowData, column);
      area = faintestP;
      break;

      case "max":
      furthestP = new JTable(rowData, column);
      area = furthestP;
      break;

      case "min":
      nearestP = new JTable(rowData, column);
      area = nearestP;
      break;
    }
    JScrollPane scroll = new JScrollPane(area);
    scroll.setPreferredSize(new Dimension(250,80));
    scroll.setAlignmentX(LEFT_ALIGNMENT);

    area.setEnabled(false);
    p.add(scroll);
  }

  /*
  * messierData
  * search and print the results of Messier objects
  */
  private void messierData(ArrayList<Messier> allMessier, String s){
    DataSet setMessier = sum.analysis(allMessier); // find the index of min and max value
    JTable area = null;
    ArrayList<Integer> mInd = null;
    JPanel p = null;
    String[] column = {"ID","Ra","Declination","Magnitude", "Distance", "Constellation", "Description"};


    switch(s){
      case "brightest":
      mInd = setMessier.getBrightestIndex();
      p = brightestPanel;
      break;

      case "faintest":
      mInd = setMessier.getFaintestIndex();
      p = faintestPanel;
      break;

      case "max":
      mInd = setMessier.getMaxIndex();
      p = furthestPanel;
      break;

      case "min":
      mInd = setMessier.getMinIndex();
      p = nearestPanel;
      break;
    }

    Object[][] rowData = new Object[mInd.size()][7];

    int i = 0;
    for(int j: mInd){
      Messier m = new Messier();
      m = allMessier.get(j);
      Object[] data = {m.getId(),m.getRa(),m.getDeclination(),m.getMagnitude(),m.getDistance(),m.getConstellation(),m.getDescription()};
      rowData[i] = data;
      i++;
    }

    switch(s){
      case "brightest":
      brightestM = new JTable(rowData, column);
      area = brightestM;
      break;

      case "faintest":
      faintestM = new JTable(rowData, column);
      area = faintestM;
      break;

      case "max":
      furthestM = new JTable(rowData, column);
      area = furthestM;
      break;

      case "min":
      nearestM = new JTable(rowData, column);
      area = nearestM;
      break;
    }
    TableColumnModel col = area.getColumnModel();
    col.getColumn(0).setPreferredWidth(50);
    col.getColumn(6).setPreferredWidth(180);
    JScrollPane scroll = new JScrollPane(area);
    scroll.setPreferredSize(new Dimension(250,80));
    scroll.setAlignmentX(LEFT_ALIGNMENT);
    area.setEnabled(false);
    p.add(scroll);
  }

  public static void main(String[] args){
    ArrayList<Star> allStar = new ArrayList<>();
    ArrayList<Planet> allPlanet = new ArrayList<>();
    ArrayList<Messier> allMessier = new ArrayList<>();
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        FrameSearch fs = new FrameSearch(allStar, allPlanet, allMessier);
        fs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fs.setVisible(true);
        }
      });
  }
}
