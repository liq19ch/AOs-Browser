/*
* FrameButton
* The main frame for the AstronomicalObjectsBrowser
* @author CTH
* First version 07/04/2020
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class FrameButton extends JFrame{
  // instance variable
  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>(); 
  private JPanel panel; // main panel
  private JPanel buttonPanel;
  private JButton displayButton, queryButton, searchButton;
  public static final int DEFAULT_WIDTH = 480;
  public static final int DEFAULT_HEIGHT = 200;

  public FrameButton(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
    this.allStar = allStar;
    this.allPlanet = allPlanet;
    this.allMessier = allMessier;

    setTitle("AstronomicalObjectsBrowser");
    setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    panel = new JPanel(); // initialize panel
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    panel.setLayout(new GridLayout(0, 1));

    JLabel title = new JLabel("Astronomical Objects Browser System", SwingConstants.CENTER);
    panel.add(title, BorderLayout.PAGE_START);

    buttonPanel = new JPanel();
    displayButton = new JButton("Display Objects");
    // add action to the button
    displayButton.addActionListener(new ButtonAction());
    queryButton = new JButton("Search by Query");
    queryButton.addActionListener(new ButtonAction());
    searchButton = new JButton("Search Min/Max");
    searchButton.addActionListener(new ButtonAction());
    // add button to panel
    buttonPanel.add(displayButton);
    buttonPanel.add(queryButton);
    buttonPanel.add(searchButton, BorderLayout.CENTER);
    panel.add(buttonPanel); // add to main panel
    add(panel, BorderLayout.CENTER); // add main panel to the frame
  }

  /*
  * ButtonAction
  * Implements ActionListener to override the button action
  */

  private class ButtonAction implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      if(e.getSource() == displayButton){
        // create FrameDisplay while clicking the displayButton
        FrameDisplay frame = new FrameDisplay(allStar, allPlanet, allMessier);
        // only close it instead of closing whole application
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
      }else if(e.getSource() == queryButton){
        // create FrameQuery while the queryButton is clicked
        FrameQuery frame = new FrameQuery(allStar, allPlanet, allMessier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
      }else if(e.getSource() == searchButton){
        // create FrameSearch when the searchButton is clicked
        FrameSearch frame = new FrameSearch(allStar, allPlanet, allMessier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
      }
    }
  }

  public static void main (String[] args){
    ArrayList<Star> allStar = new ArrayList<>();
    ArrayList<Planet> allPlanet = new ArrayList<>();
    ArrayList<Messier> allMessier = new ArrayList<>();
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        FrameButton fb = new FrameButton(allStar, allPlanet, allMessier);
        fb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fb.setVisible(true);
        }
      });
    }
}
