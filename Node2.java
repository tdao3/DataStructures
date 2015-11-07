/*
Team: 2-3-4 Trio
Members: TO, TD, AB
*/

import java.awt.Graphics;
import java.awt.Color;

public class Node2 extends Node
{
    public static final int WIDTH = 50; // width of this node
    
	public Node2()
    //POST: A default Node2 object is created with a key set to 1, child nodes and
	//      parent node set to null, coordinates of 0,0, and lastNode set to false.
    {
        this(1, null, null, new ScaledPoint(0, 0)); 
    }
	
    public Node2(int key, Node[] children, Node parent, ScaledPoint coord)
    //PRE: key, and parent initialized. children initialized with 2 initialized 
    //     pointers in the array, and coord is initialized.
    //POST: A Node2 object is created with a key set to key, child nodes set to 
    //      corresponding pointers in children, a parent node set to parent,
    //      and coordinates set to coord, subtreeWidth initialized to 0,
    //      and lastNode set to false.
    {
    	int counter;           //counter for for loop

    	keys = new int[1];
    	this.children = new Node[2];
    	
        // setting the key and parent input parameters 
        keys[0] = key;
        this.parent = parent;
        
        // setting children to input parameters
        for(counter = 0; counter < 2; counter++)
        {
            this.children[counter] = children[counter];
        }
        
		this.coord = coord;
		
		lastNode = false;
		
		subtreeWidth = 0;
    }

    //@Override
    public void drawNode(Graphics g, boolean selected) 
    //PRE: g and selected are initialized.
    //POST: a rectangle 2 node and connector lines to its children are drawn
    //      and its color is based on whether this node is the current node 
    //      in the step process or the last node accessed in the stepping process.
    {
    	int nodeX;     // x coord of top left corner of node
    	int nodeY;     // y coord of top left corner of node
    	
    	nodeX = coord.getX();
    	nodeY = coord.getY();
    	
        if(selected)    //If this is the current node  
        {
            g.setColor(new Color(120, 255, 120));
            g.fillRect(nodeX, nodeY, WIDTH, HEIGHT);
        }
        else if(lastNode)   //If this is the last node in the stepping process
        {
        	g.setColor(new Color(255, 120, 120));
            g.fillRect(nodeX, nodeY, WIDTH, HEIGHT);
        }

        //Draw a black rectangle border.
    	g.setColor(Color.BLACK);
        g.drawRect(nodeX, nodeY, WIDTH, HEIGHT);
        
        //Draw the key inside the rectangle.
        g.drawString(Integer.toString(keys[0]), nodeX + 4, nodeY + (HEIGHT/2) + 4);
        
        
        // draw lines to children of this node
        for(int i = 0; i < children.length; i++) // iterate through children
		{
			if (children[i] instanceof Node2) // if the child is a Node2
			{
				g.drawLine(nodeX + (WIDTH*i), nodeY + HEIGHT,
						   children[i].coord.getX()+ Node2.WIDTH/2,
						   children[i].coord.getY());
			}
			else if (children[i] instanceof Node3) // if the child is a Node3
			{
				g.drawLine(nodeX + (WIDTH*i), nodeY + HEIGHT,
						   children[i].coord.getX() + Node3.WIDTH/2,
						   children[i].coord.getY());
			}
			else if (children[i] instanceof Node4) // if the child is a Node4
			{
				g.drawLine(nodeX + (WIDTH*i), nodeY + HEIGHT,
						   children[i].coord.getX() + Node3.WIDTH/2,
						   children[i].coord.getY());
			}
		}
    }
    
    public Node3 absorbNode(Node2 pushedNode)
    //PRE: pushedNode must be initialized.
    //POST: FCTVAL = a Node3 object whose keys and children become a combination of this node's
    //      key and children, and the pushedNode's key and children, based on the relationship 
    //      between this node and the pushedNode.
    //      The child pointer of the parent of this node is updated to point to the new Node3 object
    //      created.
    {
    	int[] newKeys;             // keys of the new created node
    	Node[] updatedChildren;    // new array containing 3 children 
        Node3 updatedNode;         // the newly created Node 
    	
        // allocating space for the 2 new keys and 3 children (format of Node3)
    	newKeys = new int[2];
    	updatedChildren = new Node[3]; 
    
    	if(pushedNode.keys[0] < keys[0])   // updating order of children
    	{
			newKeys[0] = pushedNode.keys[0];   // adds the newly added key to newKeys
			newKeys[1] = this.keys[0];         // copies original key to newKeys 
    	    updatedChildren[0] = pushedNode.getChildren()[0];
    	    updatedChildren[1] = pushedNode.getChildren()[1];
    	    updatedChildren[2] = children[1];
    	}
    	else
    	{
			newKeys[0] = this.keys[0];         // copies original key to newKeys 
			newKeys[1] = pushedNode.keys[0];   // adds the newly added key to newKeys
    		updatedChildren[0] = children[0];
    	    updatedChildren[1] = pushedNode.getChildren()[0];
    	    updatedChildren[2] = pushedNode.getChildren()[1];
    	}
    	
    	updatedNode = new Node3(newKeys, updatedChildren, parent, coord);
    	parent.updateChildPtr(this, updatedNode);     //the new Node3 becomes the parent of 
                                                      //    all the previous children
		
		// set children of new updatedNode to point to updated
		// node as parent
		for(Node n : updatedChildren)
		{
			n.setParent(updatedNode);
		}
		
		
        return updatedNode;
    }

    @Override
    public Node findPath(int n) 
    //PRE:  n is initialized.
    //POST: FCTVAL = children[0] Node if n is less than key[0] otherwise
    //      children[1].
    {
        if(n < keys[0])
        {
            return children[0];
        }
        else
        {
            return children[1];
        }
    }
    
    @Override
    public int getNodeWidth()
    // POST: FCTVAL == the width of a Node2
    {
        return WIDTH;
    }
}
