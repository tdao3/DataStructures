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
    
    private Node[] nodes;
    private Node current;
    
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
       
        
        
        // testing drawing nodes
        nodes = new Node[13];
        
        // initialize 5 nodes, where the 5th is the parent of the other 4 nodes
        nodes[5] = new Node2(-125, new Node[]{null, null}, null, new ScaledPoint());
        nodes[6] = new Node2(-80, new Node[]{null, null}, null, new ScaledPoint());
        nodes[7] = new Node2(-60, new Node[]{null, null}, null, new ScaledPoint());
        nodes[8] = new Node2(-40, new Node[]{null, null}, null, new ScaledPoint());
        nodes[9] = new Node2(40, new Node[]{null, null}, null, new ScaledPoint());
        nodes[10] = new Node2(60, new Node[]{null, null}, null, new ScaledPoint());
        nodes[11] = new Node2(80, new Node[]{null, null}, null, new ScaledPoint());
        nodes[12] = new Node2(125, new Node[]{null, null}, null, new ScaledPoint());
        
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
        
        current = nodes[4];
    }
    
    //Paint the GUI, and gets the graphics object.   
    public void paint(Graphics g)
    {
        super.paint(g);    
        
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
        // TODO Auto-generated method stub
    }

}
