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
    private JLabel inputLabel;           // Label for the inputValueField
    
    private JPanel buttonPanelSection;   // Panel to hold all button panels.
    private JPanel topButtonRow;         // Panel to hold operation buttons and input field.
    private JPanel middleInputRow;       // Panel to hold input value field and label
    private JPanel bottomButtonRow;      // Panel to hold stepping through operation buttons.
    private JPanel drawingArea;          // Panel where the tree with be displayed 
    
    private int drawAreaHeight;          // Height of JPanel drawingArea used for drawing tree
    private int drawAreaWidth;           // Width of JPanel drawingArea  used for drawing tree
    
    public void init()
    {
        //Set the original GUI size.
        setSize(800,680);                                        
        setLayout(new BorderLayout());        
        
        //Initialize buttons.
        insertButton = new JButton("Insert");
        searchButton = new JButton("Search");
        deleteButton = new JButton("Delete");
        stepButton = new JButton("Step");
        finishButton = new JButton("Finish");
        
        //Initialize text field.
        inputValueField = new JTextField(10);
        
        //Initialize label
        inputLabel = new JLabel("Enter value:");
        
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
       
    }
    
    //Paint the GUI, and gets the graphics object.   
    public void paint(Graphics g)
    {
        super.paint(g);    
    }
	
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        // TODO Auto-generated method stub
    }

}
