import java.awt.Graphics;
import java.awt.Color;

public class Node2 extends Node
{
	public Node2()
    //POST: A default Node2 object is created with a key set to 1, child nodes and
	//      parent node set to null.
    {
        this(1, null, null, new ScaledPoint(0, 0)); 
    }
	
    public Node2(int key, Node[] children, Node parent, ScaledPoint coord)
    //PRE: key, and parent initialized. children initialized with 2 initialized 
    //     pointers in the array, and coord is initialized.
    //POST: A Node2 object is created with a key set to key, child nodes set to 
    //      corresponding pointers in children, and a parent node set to parent.
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
		
    }

    @Override
    public void drawNode(Graphics g, boolean selected) 
    //PRE: g and selected is initialized.
    //POST: a rectangle node is drawn  and its color is
    //      based on whether this node is the current node in the step
    //      process.
    {
    	int nodeX;     // x coord of top left corner of node
    	int nodeY;     // y coord of top left corner of node
    	
    	nodeX = coord.getX(g.getWidth());
    	nodeY = coord.getY(g.getHeight());
    	
        if(selected)    //If this is the current node  
        {
            g.setColor(new Color(0, 200, 0);
            g.fillRect(nodeX, nodeY, 45, 25);
        }

        //Draw a black rectangle border.
    	g.setColor(Color.BLACK);
        g.drawRect(nodeX, nodeY, 45, 25);
        
        //Draw the key inside the rectangle.
        g.drawString(keys[0], nodeX + 2, nodeY + 12);
        
        for(int i = 0; i < children.length; i++) // iterate through children
		{
			if (children[i] instanceof Node2) // if the child is a Node2
			{
				g.drawLine(nodeX + (25*i), nodeY + 25,
						   children[i].coord.getX(g.getWidth()) + 12,
						   children[i].coord.getY(g.getHeight()));
			}
			else if (children[i] instanceof Node3) // if the child is a Node3
			{
				g.drawLine(nodeX + (25*i), nodeY + 25,
						   children[i].coord.getX(g.getWidth()) + 25,
						   children[i].coord.getY(g.getHeight()));
			}
			else if (children[i] instanceof Node4) // if the child is a Node4
			{
				g.drawLine(nodeX + (25*i), nodeY + 25,
						   children[i].coord.getX(g.getWidth()) + 37,
						   children[i].coord.getY(g.getHeight()));
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
    
}
