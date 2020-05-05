/*
* FrameQuery
* The frame for the queryButton in FrameButton
* @author CTH
* First version 15/04/2020
* Second version 20/04/2020
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import javax.swing.table.TableColumnModel;


public class FrameQuery extends JFrame{
  public static void main(String[] args){
    ArrayList<Star> allStar = new ArrayList<>();
    ArrayList<Planet> allPlanet = new ArrayList<>();
    ArrayList<Messier> allMessier = new ArrayList<>();
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        FrameQuery fb = new FrameQuery(allStar, allPlanet, allMessier);
        fb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fb.setVisible(true);
        }
      });
  }

  // instance variables
  private ArrayList<Star> allStar = new ArrayList<>();
  private ArrayList<Planet> allPlanet = new ArrayList<>();
  private ArrayList<Messier> allMessier = new ArrayList<>();
  private ArrayList<String> data = new ArrayList<>();
  private JPanel panel, buttonPanel, enterPanel;
  private JLabel queryLabel;
  private JTextField queryText;
  private JButton buttonAdd, buttonSearch, buttonDel, buttonUpdate;
  private JTable table = new JTable();
  private Object[] columns = {"Query"}; // table title
  private DefaultTableModel model = new DefaultTableModel();
  private Object[] row = new Object[1]; // table row


  public FrameQuery(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){

    this.allStar = allStar;
    this.allPlanet = allPlanet;
    this.allMessier = allMessier;

    setTitle("SearchByQuery");
    setSize(600, 300);

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));


    queryLabel = new JLabel("Query");
    queryText = new JTextField();
    enterPanel = new JPanel();
    buttonAdd = new JButton("Add query");
    buttonDel = new JButton("Delete");
    buttonUpdate = new JButton("Update");
    buttonSearch = new JButton("Search");

    enterPanel.setLayout(new BoxLayout(enterPanel, BoxLayout.LINE_AXIS));
    enterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    enterPanel.add(Box.createHorizontalGlue()); // set panel to add item horizeontal
    enterPanel.add(queryLabel);
    // set rigid
    enterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    enterPanel.add(queryText);
    enterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    enterPanel.add(buttonAdd);
    enterPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    enterPanel.add(buttonUpdate);

    JLabel label = new JLabel("Added Query:");
    JPanel scrollPanel = new JPanel();
    scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));

    model.setColumnIdentifiers(columns);
    table.setModel(model);
    // set table column width
    TableColumnModel col = table.getColumnModel();
    col.getColumn(0).setPreferredWidth(30);

    // create mouse event to table
    table.addMouseListener(new MouseAdapter(){
      @Override
      public void mouseClicked(MouseEvent e){
        int j = table.getSelectedRow();
        queryText.setText(model.getValueAt(j,0).toString());
      }
    });
    // add scroll to table
    JScrollPane scroll = new JScrollPane(table);
    scroll.setPreferredSize(new Dimension(250,80));
    scroll.setAlignmentX(LEFT_ALIGNMENT);
    scrollPanel.add(label);
    scrollPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    scrollPanel.add(scroll);
    scrollPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    buttonPanel = new JPanel();
    // set panel layout
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    buttonPanel.add(Box.createHorizontalGlue());
    buttonPanel.add(buttonDel);
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    buttonPanel.add(buttonSearch);

    // add button action
    buttonUpdate.addActionListener(new ButtonAct());
    buttonDel.addActionListener(new ButtonAct());
    buttonAdd.addActionListener(new ButtonAct());
    buttonSearch.addActionListener(new ButtonAct());

    add(enterPanel, BorderLayout.PAGE_START);
    add(scrollPanel);
    add(buttonPanel, BorderLayout.PAGE_END);
  }

  /*
  * ButtonAct
  * Implements ActionListener to override the button action
  */
  private class ButtonAct implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
      if(e.getSource() == buttonUpdate){
        // update the data to the contecnt of queryText
        int j = table.getSelectedRow();
        if(j >= 0){
          model.setValueAt(queryText.getText(), j, 0);
        }
        queryText.setText("");
      }else if(e.getSource() == buttonDel){
        // delete the data that is selected
        int j = table.getSelectedRow();
        if (j >= 0){
          model.removeRow(j);
        }
      }else if(e.getSource() == buttonAdd){
        // add a query to table
        String aQuery = queryText.getText();
        queryText.setText("");
        row[0] = aQuery;
        model.addRow(row);
      }else if(e.getSource() == buttonSearch){
        // clear earlier data added
        data.clear();
        int j = table.getRowCount();
        if(j >= 0){
          // add queries into data array list to process
          for(int k = 0; k < j; k++){
            data.add(table.getValueAt(k, 0).toString());
          }
        }
        // create result frame
        QuerySearch qs = new QuerySearch(allStar, allPlanet, allMessier);
        qs.setVisible(true);
        qs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    }
  }

  /*
  * QuerySearch
  * The frame for the buttonSearch button in FrameQuery
  */

  private class QuerySearch extends JFrame{
    private JPanel resultPanel = new JPanel();
    private JPanel panelOne = new JPanel();
    private JPanel panelTwo = new JPanel();

    public QuerySearch(ArrayList<Star> allStar, ArrayList<Planet> allPlanet, ArrayList<Messier> allMessier){
      QueryRunner qr = new QueryRunner(allStar, allPlanet, allMessier);
      // check the format of entered queires
      ArrayList<String> allQ = qr.checkFormat(data);
      ArrayList<QueryResults> result = qr.runAll(allQ);
      ArrayList<String> errorM = qr.getErrorMessage();

      // set layout
      setTitle("QuerySearchResults");
      setSize(700, 400);
      resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));

      JTable tableOne = new JTable();
      Object[] oneCol = {"Query","Result"}; // table title
      DefaultTableModel aModel = new DefaultTableModel();
      Object[] oneRow = new Object[2]; // table row

      // set table columns width
      aModel.setColumnIdentifiers(oneCol);
      tableOne.setModel(aModel);
      TableColumnModel col = tableOne.getColumnModel();
      col.getColumn(0).setPreferredWidth(470);
      col.getColumn(1).setPreferredWidth(60);

      // add queries and result number into table
      for(int j = 0; j < result.size(); j++){
        oneRow[0] = allQ.get(j).trim();
        ArrayList<String> n = result.get(j).getIDs();
        oneRow[1] = n.size();
        aModel.addRow(oneRow);
      }
      // add error queires
      if(errorM != null){
        for (String e : errorM){
          oneRow[0] = e;
          oneRow[1] = "error";
          aModel.addRow(oneRow);
        }
      }

      // add scroll to table
      JScrollPane scroll = new JScrollPane(tableOne);
      scroll.setPreferredSize(new Dimension(250,80));
      scroll.setAlignmentX(LEFT_ALIGNMENT);
      JLabel labelOne = new JLabel("Summary:");

      // set panel layout
      panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.PAGE_AXIS));
      panelOne.add(labelOne);
      panelOne.add(Box.createRigidArea(new Dimension(0, 5)));
      panelOne.add(scroll);
      panelOne.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

      resultPanel.add(panelOne);

      JPanel panelTwo = new JPanel();
      JLabel labelTwo = new JLabel("Result lists:");
      JTable tableTwo = new JTable();

      Object[] twoCol = {"ID"}; // table title
      DefaultTableModel tModel = new DefaultTableModel();
      Object[] twoRow = new Object[1]; // table row

      tModel.setColumnIdentifiers(twoCol);
      tableTwo.setModel(tModel);
      // add scroll to table
      JScrollPane aScroll = new JScrollPane(tableTwo);
      aScroll.setPreferredSize(new Dimension(250,80));
      aScroll.setAlignmentX(LEFT_ALIGNMENT);

      // set layout
      panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.PAGE_AXIS));
      panelTwo.add(labelTwo);
      panelTwo.add(Box.createRigidArea(new Dimension(0, 5)));
      panelTwo.add(aScroll);
      panelTwo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      resultPanel.add(panelTwo);

      // create mouse event to table
      tableOne.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
          int j = tableOne.getSelectedRow();
          // clear table content
          while(tModel.getRowCount() > 0){
            tModel.removeRow(0);
          }
          // add ID into table while the query is not error
          if(!tableOne.getValueAt(j, 1).toString().equals("error")){
            ArrayList<String> n = result.get(j).getIDs();
            for(int i = 0; i < n.size(); i++){
              twoRow[0] = n.get(i);
              tModel.addRow(twoRow);
            }
          }
        }
      });

      add(resultPanel);

    }
  }
}
