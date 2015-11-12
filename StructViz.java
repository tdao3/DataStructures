// Programmer:  TO, AB, TD
// Assignment:  Group Project 2
// Date:        November 8th, 2015
// Description: The class is the interface for the tree. IT will show the insertion, search, and
//              deletion of the nodes within the tree.
//


import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color; 

public class StructViz extends JApplet implements ActionListener
{
    private JButton insertButton;      // Button to insert a value into the tree.
    private JButton searchButton;      // Button to search for a value in the tree.
    private JButton deleteButton;      // Button to delete a value in the tree.
    private JButton stepButton;        // Button to step to the next step of the operation on the tree.
    private JButton finishButton;      // Button to finish the rest of the steps of the current operation.
    
    private JTextField inputValueField;  // Input field to gather a data value for the operation.
    private JTextField infoField;        // Text field to display description of current operation on tree.
    private JLabel inputLabel;           // Label for the inputValueField.
    private JLabel infoLabel;            // Label for the infoField.
    
    private JPanel buttonPanelSection;   // Panel to hold all button panels.
    private JPanel topButtonRow;         // Panel to hold operation buttons and input field.
    private JPanel middleInputRow;       // Panel to hold input value field and label.
    private JPanel bottomButtonRow;      // Panel to hold stepping through operation buttons.
    private JPanel infoPanel;            // Panel to hold text field.
    private JPanel drawingArea;          // Panel where the tree will be displayed.
    
    private int drawAreaHeight;          // Height of JPanel drawingArea used for drawing tree.
    private int drawAreaWidth;           // Width of JPanel drawingArea  used for drawing tree.
    private int inputValue;              // Integer value entered by the user.
    
    private boolean isSearch;            // True if user is searching through tree
                                         // false otherwise.
    private boolean isInsert;            // Value true if user is inserting into the tree
                                         // false otherwise.
    private boolean isDelete;            // Value true if user is deleting a value in the tree
                                         // false otherwise.
    
    private String currentKeys;          // String representation of keys of current node.
    private String infoString;           // String representation of the text inside infoField.

    private Node[] nodes;                // Node array all nodes in the tree
    private Node current;                // Current node accessed
    private Node root;                   // Root node of the tree
    private Node previous;               // Keeps track of previously accessed node
    
    public void init()
    {
        //Set the original GUI size.
        setSize(800,680);                                        
        setLayout(new BorderLayout());        
        
        //Initialize default values.
        isSearch = false;
        isInsert = false;
        isDelete = false;
        inputValue = 0;
        currentKeys = "";
        infoString = "";
        
        //Initialize buttons.
        insertButton = new JButton("Insert");
        searchButton = new JButton("Search");
        deleteButton = new JButton("Delete");
        stepButton = new JButton("Step");
        finishButton = new JButton("Finish");
        
        //Initialize text fields.
        inputValueField = new JTextField(10);
        infoField = new JTextField(50);
        infoField.setEditable(false);
        
        //Initialize labels.
        inputLabel = new JLabel("Enter integer:");
        infoLabel = new JLabel("Current Step:");
        
        //Initialize panels.
        buttonPanelSection = new JPanel();
        buttonPanelSection.setLayout(new BorderLayout());
        
        infoPanel = new JPanel();
        topButtonRow = new JPanel();
        middleInputRow = new JPanel();
        bottomButtonRow = new JPanel();

        //Initialize drawing area.
        drawingArea = new JPanel(new BorderLayout());
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
        
        //Add components to top row of input label and text field.
        topButtonRow.add(inputLabel);
        topButtonRow.add(inputValueField);

        //Add components to middle row of buttons and text field.
        middleInputRow.add(insertButton);
        middleInputRow.add(searchButton);
        middleInputRow.add(deleteButton);
        
        //Add components to bottom row of buttons.
        bottomButtonRow.add(stepButton);
        bottomButtonRow.add(finishButton);
        
        //Add components to information panel.
        infoPanel.add(infoLabel);
        infoPanel.add(infoField);
        
        //Add panels to border layout.
        buttonPanelSection.add(topButtonRow, BorderLayout.NORTH);
        buttonPanelSection.add(middleInputRow, BorderLayout.CENTER);
        buttonPanelSection.add(bottomButtonRow, BorderLayout.SOUTH);
        drawingArea.add(infoPanel);
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
        nodes[12] = new Node4(new int[]{125, 130, 135}, new Node[]{null, null, null, null}, null, new ScaledPoint());
        
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
        root = null;
    }
    
    //Paint the GUI, and gets the graphics object.   
    public void paint(Graphics g)
    {
        super.paint(g);    

        if(isSearch || isInsert)    //If user is searching or inserting in tree
        { 
            //Disable input text field.
            inputValueField.setEnabled(false);
        }
        else //otherwise
        {
            //Enable input text field.
            inputValueField.setEnabled(true);
        }
        

        // change the size of the window in ScaledPoint to the current size
        ScaledPoint.setWindowSize(getWidth(), getHeight());        

        // draw the tree as long as the root isn't null
        if(root != null)
        {
            root.getSubtreeWidths();
            root.repositionNodes();
            root.drawTree(g, root, current);
        }

        deleteButton.setEnabled(false);

        if(root == null)
        {
            searchButton.setEnabled(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
    	//Set the class member lastNode to false for 
        //every node in the tree 
    	if(root != null)   //if tree is not empty
    	{
    	    if(!root.isLeaf())    //if root has children
            {
                root.setLastNodeAll();
            }
    	    else  // if root is the only node
    	    {
    	        root.setLastNode(false);
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
                      searchButton.setEnabled(false);
                      
                      
                      //User is searching tree
                      isSearch = true;
                      
                      current = root;
                      
                      if(current == null)  //If tree is empty
                      {
                          infoField.setText("The tree is empty.\n");
                          current = root;
                      }
                      else if(current.hasKey(inputValue))  //If current node contains the key 
                      {
                          //Reset other buttons
                          stepButton.setEnabled(false);
                          finishButton.setEnabled(false);
                          insertButton.setEnabled(true);
                          deleteButton.setEnabled(true);
                          searchButton.setEnabled(true);
                          
                          //User done searching
                          isSearch = false;
                      }
                      
                      
                      //Disable insert and delete buttons
                      insertButton.setEnabled(false);
                      deleteButton.setEnabled(false);
                      searchButton.setEnabled(false);
                      

                      //User is searching tree
                      isSearch = true;
                      
                      //Update current node.
                      current = root;
                      
                      if(current != null)  //If tree is not empty
                      {
                          //Update current step info.
                          displayComparison();
                      }
                      
                      if(current == null)  //If tree is empty
                      {
                          //Update current step info.
                          infoField.setText("The value " + inputValue + " is not found.");
                          current = root;
                      }
                      else if(current.hasKey(inputValue))  //If current node contains the key 
                      {
                          //Update current step info.
                          infoField.setText(infoString + " The value " + inputValue + " has been found.");
                          
                          //Reset other buttons
                          stepButton.setEnabled(false);
                          finishButton.setEnabled(false);
                          insertButton.setEnabled(true);
                          deleteButton.setEnabled(true);
                          searchButton.setEnabled(true);

                          
                          //User done searching
                          isSearch = false;
                      }
                      
                    } 
                    catch (NumberFormatException d) 
                    {
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
                    
                    //Enable step and finish buttons
                    stepButton.setEnabled(true);
                    finishButton.setEnabled(true);
                    
                    //Disable search and delete buttons
                    searchButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    insertButton.setEnabled(false);
                    
                    //Assign current node to begin at root
                    current = root;
                    
                    //User is inserting in tree
                    isInsert = true;
                    
                    //Update current step info.
                    if(current == null)  //if tree is empty
                    {
                        stepInsert();
                        current = root;
                    }
                    else   //tree is not empty
                    {
                    	displayComparison();
                        infoField.setText(infoString);
                    }

                }
                catch (NumberFormatException d)
                {
                    //Invalid input given
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer." );
                }
                 
            }
        }
        
        
        /**************************DELETE****************************/

        if(e.getSource() == deleteButton)             //User clicked delete button 
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
                    
                    //Enable step and finish buttons
                    //stepButton.setEnabled(true);
                    //finishButton.setEnabled(true);
                    
                    //Disable search, delete, and insert buttons
                    searchButton.setEnabled(false);
                    insertButton.setEnabled(false);
                    //deleteButton.setEnabled(false);
                    
                    //Assign current node to begin at root
                    current = root;
                    
                    //User is inserting in tree
                    isDelete = true;
                    
                    finishDelete();
                    
                    //Reset other buttons
                    stepButton.setEnabled(false);
                    finishButton.setEnabled(false);
                    insertButton.setEnabled(true);
                    searchButton.setEnabled(true);
                    deleteButton.setEnabled(true);  
                }
                catch (NumberFormatException d)
                {
                    //Invalid input given
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer." );
                }
                 
            }
        }
        

        /************************STEP AND FINISH*******************/
        if(e.getSource() == stepButton)   //If step button is pressed
        {
            if(isSearch)  //If user is searching
            {
                stepSearch();
            }
            
            if(isInsert)   //If user is inserting
            {
                stepInsert();
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
            
            if(isInsert)
            {
                finishInsert();
                
                //User done inserting
                isSearch = false;
                
                //Reset other buttons
                finishButton.setEnabled(false);
                stepButton.setEnabled(false);
                searchButton.setEnabled(true);
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
        
        if(current != null)   //If current node is not null
        {
            //Update current step info.
            displayComparison();
        }
        
        if(current == null)  //If traversed through whole tree
        {
            //User is done searching
            isSearch = false;
            infoField.setText("The value " + inputValue + " has not been found.");
            
            //Set previous node as last accessed node
            previous.setLastNode(true);
            
            //Reset other buttons
            finishButton.setEnabled(false);
            stepButton.setEnabled(false);
            insertButton.setEnabled(true);
            deleteButton.setEnabled(true);
            searchButton.setEnabled(true);
        }
        else if(current.hasKey(inputValue))  //If key has been found
        {
            //User is done searching
            isSearch = false;
            
            //Update current step info.
            infoField.setText(infoString + " The value " + inputValue + " has been found.");
            
            //Reset other buttons
            finishButton.setEnabled(false);
            stepButton.setEnabled(false);
            insertButton.setEnabled(true);
            deleteButton.setEnabled(true);
            searchButton.setEnabled(true);
        }
    }
    
    public void stepInsert()
    {
        Node temp;      // temporary node to hold the new node with the value inserted
        
        boolean locationFound; // boolean to keep track of iteration through tree
        
        infoField.setText(""); // clear the info field
        
        // locationFound = false to start
        locationFound = false;
        
        if(current == null)  // if the tree is empty
        {
            locationFound = true;
        }
        else if(current.hasKey(inputValue))  //If key already exists in tree
        {
        	//Update current step info.
        	displayComparison();
            infoField.setText(infoString + " Key already exists in tree.");
            locationFound = true;
        }
        else if(current instanceof Node4) // if it is a node4 it has to be split
        {
            // split the current node, current moves up 1 level
            current = ((Node4)current).splitNode4();
            
            //Update current step info.
            displayComparison();
            infoField.setText("The previous node has been split. " + infoString);
            
            if(current.isRoot()) // if current has no parent
            {
                root = current;  // must set root to be this new split parent
            }
        }
        else   //Any other node in the tree
        {
            if(!current.isLeaf())  //if current node is not a leaf node
            {
                //Find the next node to traverse to
                current = current.findPath(inputValue);
                
                //Update current step info.
            	displayComparison();
                infoField.setText(infoString);
            }
            else  //reached a leaf node
            {
                locationFound = true;
            }
        }
        
        if(locationFound)  //location to insert is found
        {
            if(current == null)  //if tree is empty
            {
            	//Update current step info
                infoField.setText("Tree is empty. Inserting at empty root.");
                
                //Insert at root
                temp = new Node2(inputValue, new Node[]{null, null}, null, new ScaledPoint(.5, .5));
                root = temp;
            }
            else if(current.hasKey(inputValue))   // if the value is in the tree
            {
                infoField.setText("Key already exists in tree.");
            }
            else  //insert the new node
            {
            	infoField.setText("Location to insert is found. Value " + inputValue + " inserted.");
            	
                if(current instanceof Node2)   //if the current node is a type Node2
                {
                    //Insert key into a new Node3 using Node2's insert node method
                    temp = ((Node2)current).insertNode(inputValue);
                    
                    if(!temp.isRoot())  //if the new inserted node is not the root
                    {
                        //Update the parent of the previous node to point to the new inserted node.
                        temp.getParent().updateChildPtr(current, temp);
                    }
                    else  //if new inserted node is still the root
                    {
                        root = temp;
                    }
                    
                    //Reassign current node
                    current = temp;
                }
                else if(current instanceof Node3)  //if the current node is a type Node3
                {
                    //Insert key into a new Node4 using Node3's insert node method
                    temp = ((Node3)current).insertNode(inputValue);
                    
                    if(!temp.isRoot())  //if the new inserted node is not the root
                    {
                        //Update the parent of the previous node to point to the new inserted node.
                        temp.getParent().updateChildPtr(current, temp);
                    }
                    else  //if new inserted node is still the root
                    {
                        root = temp;
                    }
                    
                    //Reassign current node
                    current = temp;
                }
                
            }
            
            //User is done inserting
            isInsert = false;
            
            //Reset other buttons
            stepButton.setEnabled(false);
            finishButton.setEnabled(false);
            searchButton.setEnabled(true);
            deleteButton.setEnabled(true);  
            insertButton.setEnabled(true);
            
        }
        
        
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
            //Update current step info.
            infoField.setText("The value " + inputValue + " is not found.");
            
            //Set previous node as last accessed node
            previous.setLastNode(true);
        }
        else if(current.hasKey(inputValue))  //If key has been found
        {
            //Update current step info.
            displayComparison();
            infoField.setText(infoString + " The value " + inputValue + " has been found.");
        }
    }
    
    public void finishInsert() 
    {
        Node temp;      // temporary node to hold the new node with the value inserted
        
        boolean locationFound; // boolean to keep track of iteration through tree
        
        infoField.setText(""); // clear the info field
        
        current = root;
        
        // locationFound = false to start
        locationFound = false;
        
        // loop until a leaf is found
        while(!locationFound)
        {
            if(current.hasKey(inputValue))   //If key already exists in tree
            {
                locationFound = true;
            }
            else if(current instanceof Node4) // if it is a node4 it has to be split
            {
                // split the current node, current moves up 1 level
                current = ((Node4)current).splitNode4();
                
                infoField.setText("Split node");
                
                if(current.isRoot()) // if current has no parent
                {
                    root = current;  // must set root to be this new split parent
                }
            }
            else  //Any other node in the tree
            {
                if(!current.isLeaf())    //if current node is not a leaf node
                {
                    //Find the next node to traverse to
                    current = current.findPath(inputValue);
                }
                else   //reached a leaf node
                {
                    locationFound = true;
                }
            }
        }
        

        if(current.hasKey(inputValue))   // if the value is already in the tree
        {
            infoField.setText("Key already exists in tree.");
        }
        else     //insert the new node
        {
        	infoField.setText("Location to insert is found. Value " + inputValue + " inserted.");
        	
            if(current instanceof Node2)    //if the current node is a type Node2
            {
            	//Insert key into a new Node3 using Node2's insert node method
                temp = ((Node2)current).insertNode(inputValue);
                
                if(!temp.isRoot())  //if the new inserted node is not the root
                {
                	//Update the parent of the previous node to point to the new inserted node.
                    temp.getParent().updateChildPtr(current, temp);
                }
                else  //if new inserted node is still the root
                {
                    root = temp;
                }
                
                //Reassign current node
                current = temp;
            }
            else if(current instanceof Node3)   //if the current node is a type Node3
            {
            	//Insert key into a new Node4 using Node3's insert node method
                temp = ((Node3)current).insertNode(inputValue);
                
                if(!temp.isRoot())   //if the new inserted node is not the root
                {
                	//Update the parent of the previous node to point to the new inserted node.
                    temp.getParent().updateChildPtr(current, temp);
                }
                else  //if new inserted node is still the root
                {
                    root = temp;
                }
                
                //Reassign current node
                current = temp;
            }
            
        }
        
        //User is done inserting
        isInsert = false;
        
        //Reset other buttons
        stepButton.setEnabled(false);
        finishButton.setEnabled(false);
        searchButton.setEnabled(true);
        deleteButton.setEnabled(true);  
        insertButton.setEnabled(true);
    }
    
    
    public void finishDelete()
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
            //Update current step info.
            infoField.setText("The value " + inputValue + " does not exist.");
        }
        else if(current.hasKey(inputValue))  //If key has been found
        {
            if(current.isLeaf())  // if current node is a leaf
            {
                current = current.deleteLeafKey(inputValue);
            }
        }
        
    }
    
    public void displayComparison()
    {
        //Reset info strings.
        currentKeys = "";
        infoString = "";
        
        //Populate the currentKeys with the keys of the current node.
        for(int k : current.getKeys())
        {
            currentKeys += Integer.toString(k) + ", ";   
        }
        
        //Set the text of the infoField to represent the current comparison.
        currentKeys = currentKeys.substring(0, currentKeys.length() - 2);
        infoString = "Now comparing input value " + inputValue + " with node key(s) " + currentKeys + ".";
        infoField.setText(infoString);
    }

}
