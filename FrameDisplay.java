/*
* FrameDisplay
* The frame for the displayButton in FrameButton
* @author CTH
* First version 08/04/2020
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.TableColumn;




public class FrameDisplay extends JFrame{
  public static void main(String[] args){
    ArrayList<Star> allStar = new ArrayList<>();
    ArrayList<Planet> allPlanet = new ArrayList<>();
    ArrayList<Messier> allMessier = new ArrayList<>();
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        FrameDisplay fb = new FrameDisplay(allStar, allPlanet, allMessier);
        fb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fb.setVisible(true);
        }
      });
  }
  // instance variables
  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>();
  private JButton starButton, planetButton, messierButton;
  private JLabel label;
  private JPanel buttonPanel, mainPanel;
  private static final int DEFAULT_WIDTH = 450;
  private static final int DEFAULT_HEIGHT = 200;

  public FrameDisplay(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
    this.allStar = allStar;
    this.allPlanet = allPlanet;
    this.allMessier = allMessier;
    setTitle("DispalyObjects");
    setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);


    mainPanel = new JPanel();
    mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    mainPanel.setLayout(new GridLayout(0, 1));

    label = new JLabel("Choose the objects to display", SwingConstants.CENTER);
    mainPanel.add(label, BorderLayout.CENTER);

    buttonPanel = new JPanel();
    starButton = new JButton("Stars");
    // add the action to the button
    starButton.addActionListener(new DisplayAction());
    planetButton = new JButton("Planets");
    planetButton.addActionListener(new DisplayAction());
    messierButton = new JButton("Messiers");
    messierButton.addActionListener(new DisplayAction());

    // add buttons to buttonPanel
    buttonPanel.add(starButton);
    buttonPanel.add(planetButton);
    buttonPanel.add(messierButton);
    mainPanel.add(buttonPanel); // add to main panel

    add(mainPanel, BorderLayout.CENTER); // add to the frame
  }

  /*
  * DisplayAction
  * Implements ActionListener to override the button action
  */

  private class DisplayAction implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      if(e.getSource() == starButton){
        // create StarFrame when starButton is clicked
        StarFrame sf = new StarFrame(allStar);
        sf.setVisible(true);
        // only close the specific frame instead of the all application
        sf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }else if(e.getSource() == planetButton){
        // create PlanetFrame while the planetButton is clicked
        PlanetFrame pf = new PlanetFrame(allPlanet);
        pf.setVisible(true);
        pf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }else if(e.getSource() == messierButton){
        // create MessierFrame when the messierButton is clicked
        MessierFrame mf = new MessierFrame(allMessier);
        mf.setVisible(true);
        mf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    }
  }

  /*
  * StarFrame
  * The frame for the starButton in FrameDisplay
  */

  private class StarFrame extends JFrame{

    // instance variable
    private JPanel starPanel;
    private JLabel starLabel;

    public StarFrame(ArrayList<Star> allStar){
      setTitle("StarObjects");
      setSize(580, 300);

      starPanel = new JPanel();

      starPanel.setLayout(new BoxLayout(starPanel, BoxLayout.PAGE_AXIS));


      // label to show the number of results
      starLabel = new JLabel(allStar.size()+" star objects are founded:");


      String[] column = {"ID","Ra","Declination","Magnitude", "Distance", "Constellation", "Type"};
      Object[][] rowData = new Object[allStar.size()][7];

      int i = 0;
      for(Star s : allStar){
        Object[] data = {s.getId(),s.getRa(),s.getDeclination(),s.getMagnitude(),s.getDistance(),s.getConstellation(),s.getType()};
        rowData[i] = data;
        i++;
      }
      JTable table = new JTable(rowData, column);
      table.setEnabled(false);

      TableColumn col = null;
      for (int j = 0; j < 7 ; j++) {
        col= table.getColumnModel().getColumn(j);
        if (j == 0 || j == 6) {
            col.setPreferredWidth(60);
        } else if (j == 3){
            col.setPreferredWidth(75);
        } else {
          col.setPreferredWidth(100);
        }
      }

      JScrollPane scroll = new JScrollPane(table);
      scroll.setPreferredSize(new Dimension(250,80));
      scroll.setAlignmentX(LEFT_ALIGNMENT);

      starPanel.add(starLabel);
      starPanel.add(Box.createRigidArea(new Dimension(0,5)));
      starPanel.add(scroll); // add to the panel
      starPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      add(starPanel, BorderLayout.CENTER); // add to the frame
    }
  }

  /*
  * MessierFrame
  * The frame for the messierButton in FrameDisplay
  */

  private class MessierFrame extends JFrame{

    // instance varialbe
    private JPanel messierPanel;
    private JLabel messierLabel;


    public MessierFrame(ArrayList<Messier> allMessier){
      setTitle("MessierObjects");
      setSize(700, 400);


      messierPanel = new JPanel();

      messierPanel.setLayout(new BoxLayout(messierPanel, BoxLayout.PAGE_AXIS));

      // label to show the number of results
      messierLabel = new JLabel(allMessier.size()+" messier objects are founded:");

      String[] column = {"ID","Ra","Declination","Magnitude", "Distance", "Constellation", "Description"};
      Object[][] rowData = new Object[allMessier.size()][7];

      int i = 0;
      for(Messier m : allMessier){
        Object[] data = {m.getId(),m.getRa(),m.getDeclination(),m.getMagnitude(),m.getDistance(),m.getConstellation(),m.getDescription()};
        rowData[i] = data;
        i++;
      }
      JTable table = new JTable(rowData, column);
      table.setEnabled(false);

      TableColumn col = null;
      for (int j = 0; j < 7 ; j++) {
        col= table.getColumnModel().getColumn(j);
        if (j == 0) {
            col.setPreferredWidth(50);
        } else if (j == 3 || j == 5){
            col.setPreferredWidth(75);
        } else if (j == 6){
          col.setPreferredWidth(150);
        } else {
          col.setPreferredWidth(100);
        }
      }

      JScrollPane scroll = new JScrollPane(table);
      scroll.setPreferredSize(new Dimension(250,80));
      scroll.setAlignmentX(LEFT_ALIGNMENT);

      messierPanel.add(messierLabel);
      messierPanel.add(Box.createRigidArea(new Dimension(0,5)));
      messierPanel.add(scroll); // add the scroll to panel
      messierPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      add(messierPanel, BorderLayout.CENTER); // add to the frame
    }
  }

  /*
  * PlanetFrame
  * The frame for the planetButton in FrameDisplay
  */

  private class PlanetFrame extends JFrame{

    // instance variable
    private JPanel planetPanel;
    private JLabel planetLabel;


    public PlanetFrame(ArrayList<Planet> allPlanet){
      setTitle("PlanetObjects");
      setSize(540, 300);

      planetPanel = new JPanel();
      planetPanel.setLayout(new BoxLayout(planetPanel, BoxLayout.PAGE_AXIS));

      // lable to show the number of results
      planetLabel = new JLabel(allPlanet.size()+" planet objects are founded:");

      String[] column = {"ID","Ra","Declination","Magnitude", "Distance", "Albedo"};
      Object[][] rowData = new Object[allPlanet.size()][6];

      int i = 0;
      for(Planet p : allPlanet){
        Object[] data = {p.getId(),p.getRa(),p.getDeclination(),p.getMagnitude(),p.getDistance(),p.getAlbedo()};
        rowData[i] = data;
        i++;
      }
      JTable table = new JTable(rowData, column);
      table.setEnabled(false);
      JScrollPane scroll = new JScrollPane(table);
      scroll.setPreferredSize(new Dimension(250,80));
      scroll.setAlignmentX(LEFT_ALIGNMENT);

      planetPanel.add(planetLabel);
      planetPanel.add(Box.createRigidArea(new Dimension(0,5)));
      planetPanel.add(scroll); // add to the panel
      planetPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      add(planetPanel, BorderLayout.CENTER); // add to the frame
    }
  }

}
