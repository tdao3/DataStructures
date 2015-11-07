/*
Team: 2-3-4 Trio
Members: TO, TD, AB
*/


import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color; 

public class StructureVisualization extends JApplet implements ActionListener
{
    private JButton insertButton;      // Button to insert a value into the tree.
    private JButton searchButton;      // Button to search for a value in the tree.
    private JButton deleteButton;      // Button to delete a value in the tree.
    private JButton stepButton;        // Button to step to the next step of the operation on the tree.
    private JButton finishButton;      // Button to finish the rest of the steps of the current operation.
    
    private JTextField inputValueField;  // Input field to gather a data value for the operation.
    private JLabel inputLabel;           // Label for the inputValueField.
    
    private JPanel buttonPanelSection;   // Panel to hold all button panels.
    private JPanel topButtonRow;         // Panel to hold operation buttons and input field.
    private JPanel middleInputRow;       // Panel to hold input value field and label.
    private JPanel bottomButtonRow;      // Panel to hold stepping through operation buttons.
    private JPanel drawingArea;          // Panel where the tree will be displayed.
    
    private int drawAreaHeight;          // Height of JPanel drawingArea used for drawing tree.
    private int drawAreaWidth;           // Width of JPanel drawingArea  used for drawing tree.
    private int inputValue;              // Integer value entered by the user.
    
    private boolean isSearch;            // True if user is searching through tree
                                         // false otherwise.
    private boolean isInsert;            // Value true if user is inserting into the tree
                                         // false otherwise.
    
    private Node[] nodes;                // Node array all nodes in the tree
    private Node current;                // Current node accessed
    private Node root;                   // Root node of the tree
    private Node previous;               // Keeps track of previously accessed node
    
    public void init()
    {
        //Set the original GUI size.
        setSize(800,680);                                        
        setLayout(new BorderLayout());        
        
        //Initialize default values
        isSearch = false;
        inputValue = 0;
        
        //Initialize buttons.
        insertButton = new JButton("Insert");
        searchButton = new JButton("Search");
        deleteButton = new JButton("Delete");
        stepButton = new JButton("Step");
        finishButton = new JButton("Finish");
        
        //Initialize text field.
        inputValueField = new JTextField(10);
        
        //Initialize label
        inputLabel = new JLabel("Enter integer:");
        
        //Initialize panels
        buttonPanelSection = new JPanel();
        buttonPanelSection.setLayout(new BorderLayout());
        
        topButtonRow = new JPanel();
        middleInputRow = new JPanel();
        bottomButtonRow = new JPanel();

        //Initialize drawing area
        drawingArea = new JPanel();
        drawAreaHeight = drawingArea.getHeight();       //getting height and width of drawing area
        drawAreaWidth = drawingArea.getWidth();    
        
        //Add action listeners to buttons.
        insertButton.addActionListener(this);
        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);
        stepButton.addActionListener(this);
        finishButton.addActionListener(this);
        
        //Disable step and finish buttons
        stepButton.setEnabled(false);
        finishButton.setEnabled(false);
        
        //Add components to top row of buttons and text field.
        topButtonRow.add(insertButton);
        topButtonRow.add(searchButton);
        topButtonRow.add(deleteButton);
        
        //Add components to middle row of input label and text field.
        middleInputRow.add(inputLabel);
        middleInputRow.add(inputValueField);
        
        //Add components to bottom row of buttons.
        bottomButtonRow.add(stepButton);
        bottomButtonRow.add(finishButton);
        
        //Add panels to border layout.
        buttonPanelSection.add(topButtonRow, BorderLayout.NORTH);
        buttonPanelSection.add(middleInputRow, BorderLayout.CENTER);
        buttonPanelSection.add(bottomButtonRow, BorderLayout.SOUTH);
        add(buttonPanelSection, BorderLayout.NORTH);
        add(drawingArea, BorderLayout.CENTER);
       
        
        
        // testing drawing nodes
        nodes = new Node[13];
        
        // initialize 5 nodes, where the 5th is the parent of the other 4 nodes
        nodes[5] = new Node2(-125, new Node[]{null, null}, null, new ScaledPoint());
        nodes[6] = new Node2(-80, new Node[]{null, null}, null, new ScaledPoint());
        nodes[7] = new Node3(new int[]{-60, -55}, new Node[]{null, null, null}, null, new ScaledPoint());
        nodes[8] = new Node2(-40, new Node[]{null, null}, null, new ScaledPoint());
        nodes[9] = new Node2(40, new Node[]{null, null}, null, new ScaledPoint());
        nodes[10] = new Node2(60, new Node[]{null, null}, null, new ScaledPoint());
        nodes[11] = new Node2(80, new Node[]{null, null}, null, new ScaledPoint());
        nodes[12] = new Node3(new int[]{125, 130}, new Node[]{null, null, null}, null, new ScaledPoint());
        
        nodes[0] = new Node2(-100, new Node[]{nodes[5], nodes[6]}, null, new ScaledPoint(.1, .9));
        nodes[1] = new Node2(-50, new Node[]{nodes[7], nodes[8]}, null, new ScaledPoint(.3, .9));
        nodes[2] = new Node2(50, new Node[]{nodes[9], nodes[10]}, null, new ScaledPoint(.5, .9));
        nodes[3] = new Node2(100, new Node[]{nodes[11], nodes[12]}, null, new ScaledPoint(.7, .9));
        nodes[4] = new Node4(new int[]{-75, 0, 75}, 
                             new Node[]{nodes[0], nodes[1], nodes[2], nodes[3]},
                             null, new ScaledPoint(.5, .5));
        
        // set parent pointer of the children nodes to the parent created
        for(Node n : nodes[4].getChildren())
        {
            n.setParent(nodes[4]);
            for(Node m : n.getChildren())
            {
                m.setParent(n);
            }
        }
        
        //current = nodes[4];
        root = nodes[4];
    }
    
    //Paint the GUI, and gets the graphics object.   
    public void paint(Graphics g)
    {
        super.paint(g);    
        
        if(isSearch)    //If user is searching through tree
        { 
            //Disable input text field.
            inputValueField.setEnabled(false);
        }
        else  //otherwise
        {
        	//Enable input text field.
            inputValueField.setEnabled(true);
        }
        
        // change the size of the window in ScaledPoint to the current size
        ScaledPoint.setWindowSize(getWidth(), getHeight());
        
        nodes[4].getSubtreeWidths();
        nodes[4].repositionNodes();

        // iterate through the test nodes array and draw nodes
        for(Node n : nodes)
        {
            n.drawNode(g, (current == n)); // boolean to see if this node is the current
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //Iterate through the nodes of the tree
        for(Node n : root.getChildren())
        {
            //Set the class member lastNode to false for 
        	//every node in the tree 
            n.setLastNode(false);
            for(Node m : n.getChildren())
            {
                m.setLastNode(false);
            }
        }
    	

        /**************************SEARCH****************************/

        if(e.getSource() == searchButton)    //If search button is pressed
        {	
            if(inputValueField.getText().equals(""))  //If no input entered
            {
            	JOptionPane.showMessageDialog(null, "You must enter in a value." );
            }
            else   //input was entered
            {
            	//Check if input is a valid integer 
            	try{
            		  inputValue = Integer.parseInt(inputValueField.getText());
                    	
            		  //Enable step and finish buttons
                      stepButton.setEnabled(true);
                	  finishButton.setEnabled(true);
                	  
                	  //Disable insert and delete buttons
                	  insertButton.setEnabled(false);
                	  deleteButton.setEnabled(false);
                	  
                	  //User is searching tree
                	  isSearch = true;
                	  
            		  current = root;
            		  
            		  if(current == null)  //If tree is empty
            		  {
            			  JOptionPane.showMessageDialog(null, "The value " + inputValue + " is not found." );
            			  current = root;
            		  }
            		  else if(current.hasKey(inputValue))  //If current node contains the key 
            		  {
            			  JOptionPane.showMessageDialog(null, "The value " + inputValue + " has been found." );
            			  
            			  //Reset other buttons
            			  stepButton.setEnabled(false);
                    	  finishButton.setEnabled(false);
                    	  insertButton.setEnabled(true);
                    	  deleteButton.setEnabled(true);
                    	  
                    	  //User done searching
                    	  isSearch = false;
            		  }
            		  
            		} 
                    catch (NumberFormatException d) {
            			//Invalid input given
            			JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer." );
            		}
            }
        }


        /**************************INSERT****************************/

        if(e.getSource() == insertButton)             //User clicked insert button 
        {
            if(inputValueField.getText().equals(""))  //If no input entered
            {
                JOptionPane.showMessageDialog(null, "You must enter in a value." );
            }
            else
            {
                // error checking for user input 
                try
                {
                    inputValue = Integer.parseInt(inputValueField.getText());
                }
                catch (NumberFormatException d)
                {
                    //Invalid input given
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer." );
                }
                
                insert();   
            }
        }
        

        /************************STEP AND FINISH*******************/
        if(e.getSource() == stepButton)   //If step button is pressed
        {
            if(isSearch)  //If user is searching
            {
                stepSearch();
            }
           
        }
        
        if(e.getSource() == finishButton)   //If finish button is pressed
        {
            if(isSearch)   //If user is searching
            {
                finishSearch();
                
                //User done searching
                isSearch = false;
                
                //Reset other buttons
                finishButton.setEnabled(false);
                stepButton.setEnabled(false);
                insertButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        }

        repaint();
    }
    

    public void stepSearch()
    {
        //Keep track of previous node
        previous = current;
    	
        //Find the next node to traverse to
        current = current.findPath(inputValue);
        
        if(current == null)  //If traversed through whole tree
        {
            //User is done searching
            isSearch = false;
            JOptionPane.showMessageDialog(null, "The value " + inputValue + " is not found." );
            
            //Set previous node as last accessed node
            previous.setLastNode(true);
            
            //Reset other buttons
            finishButton.setEnabled(false);
            stepButton.setEnabled(false);
            insertButton.setEnabled(true);
      	    deleteButton.setEnabled(true);
        }
        else if(current.hasKey(inputValue))  //If key has been found
        {
            //User is done searching
            isSearch = false;
            JOptionPane.showMessageDialog(null, "The value " + inputValue + " has been found." );
            
            //Reset other buttons
            finishButton.setEnabled(false);
            stepButton.setEnabled(false);
            insertButton.setEnabled(true);
      	    deleteButton.setEnabled(true);
        }
        validate(); //what does it this do????????????????????????
    }
    
    public void finishSearch()
    {
    	//Traverse through the tree until no node left or until key is found
        while(current != null && !current.hasKey(inputValue))
        {
        	//Keep track of previous node
        	previous = current;
        	
        	//Find the next node to traverse to
            current = current.findPath(inputValue);
        }
        
        if(current == null)    //If traversed through whole tree
        {
            JOptionPane.showMessageDialog(null, "The value " + inputValue + " is not found." );
            
            //Set previous node as last accessed node
            previous.setLastNode(true);
        }
        else if(current.hasKey(inputValue))  //If key has been found
        {
            JOptionPane.showMessageDialog(null, "The value " + inputValue + " has been found." );
        }
        validate();
    }

     public void insert() 
    {
        Node3 newNode3 = new Node3();
        Node4 newNode4 = new Node4();

        //Enable step and finish buttons
        stepButton.setEnabled(true);
        finishButton.setEnabled(true);
          
        //Disable insert and delete buttons
        searchButton.setEnabled(false);
        deleteButton.setEnabled(false);
          
        //User is searching tree
        isInsert= true;

        //Set first node 
        current = root;

        /* traverse through the tree and find where to insert */
        while(current != null && !current.hasKey(inputValue))
        {
            if(current.findPath(inputValue) == null)
            {
                break;
            }
            //Keep track of previous node
            previous = current;
            
            //Find the next node to traverse to
            current = current.findPath(inputValue);
        }
        
        // inserting in to different types of nodes 
        if(current instanceof Node2)
        {
            newNode3 = ((Node2)current).insertNode(inputValue);  
            previous.updateChildPtr(current, newNode3);     
            current = newNode3;                 
        }
        else if (current instanceof Node3)
        {
            newNode4 = ((Node3)current).insertNode(inputValue);
            previous.updateChildPtr(current, newNode4);
            current = newNode4;
        }
        else if (current instanceof Node4)      //Node4 should never be inserted to
        {
            previous = ((Node4)current).splitNode4();   //splits 4Node

            //FIX

            insert();       // calls insert again since 4Node is now 2Nodes 
        }

        //Reset other buttons
        stepButton.setEnabled(false);
        finishButton.setEnabled(false);
        searchButton.setEnabled(true);
        deleteButton.setEnabled(true);  
    }

}
