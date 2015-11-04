import java.awt.Graphics;
import java.awt.Color;

public class Node3 extends Node
{
	public Node3()
	//POST: An object of Node3 is created with keys {1,2} 
	//		and no parent or child values (both set to null)
	{
		this(new int[]{1,2}, null, null, new ScaledPoint(0, 0));
	}
	
	public Node3(int[] keys, Node[] children, Node parent, ScaledPoint coord)
	//PRE:	keys is an initialized array with exactly 2 values, children is a array
	//		with exactly 3 values or is null meaning there is no children, parent is 
	//		null if Node3 has no parent, otherwise initialized
	//POST: a new Node3 is created with class members keys, children, and parent equal to parameters
	//		keys, children and parent
	{
		int i; 			//counter for loop

		this.keys = new int[2];			//allocate array of keys 
		this.children = new Node[4];	//allocate array of children

		this.keys[0] = keys[0];			//keys set to parameter keys 
		this.keys[1] = keys[1];

		if(children != null)			//sets children to be children parameters 
		{	
			for(i = 0; i < 3; i++)	
			{
				this.children[i] = children[i];
			}
		}

		this.parent = parent; 	//set parent of the node 
		
		this.coord = coord;
	}

	public void drawNode(Graphics g, boolean selected)
    {
    	int nodeX; 		//x coord of top left corner of node
    	int nodeY;		//y coord of top left corner of node

    	//grabbing coordinates of the node 
    	nodeX = coord.getX(g.getWidth());
    	nodeY = coord.getX(g.getHeight());

    	if(selected)
    	{
    		g.setColor(120,255,120);	//when node is selected border is green
    	    g.fillRect(coord.getX(g.getWidth()), coord.getY(g.getHeight()), 75, 23);

    	}
    
    	g.setColor(Color.BLACK);			//sets color to normally be black

    	g.drawRect(nodeX, nodeY, 50, 5);	//draws the actual node 

    	g.drawLine(nodeX + 25, nodeY + 25)		
    				nodeX + 25, nodeY + 25);
		
    	g.drawString(keys[0], nodeX + 2, nodeY + 15);		//draws 2 keys and distance between keys
    	g.drawString(keys[1], nodeX + 27, nodeY + 15);

    	for(int i = 0; i < children.length; i++)			//go through children
    	{
    		if(children[i] instance of Node2)				//if child is a 2 Node 
    		{
    			g.drawLine(nodeX + (25*i), nodeY + 25, children[i].coord.getX(g.getWidth()) + 12,
    			g.getHeight()));
    		}
    	}
    }

	public Node4 absorbNode(Node2 pushedNode)
    //PRE:  pushedNode must be initialized.
    //POST: FCTVAL = a Node4 object whose keys and children become a combination of this node's
    //      key and children, and the pushedNode's key and children, based on the relationship 
    //      between this node and the pushedNode.
    //      The child pointer of the parent of this node is updated to point to the new Node4 object
    //      created.
    {
    	int[] newKeys;             // keys of the new created node
    	Node[] updatedChildren;    // new array containing 3 children 
        Node4 updatedNode;         // the newly created Node 
    	
        // allocating space for the 3 new keys and 4 children (format of Node4)
    	newKeys = new int[3];
    	updatedChildren = new Node[4]; 
    	
    	if(pushedNode.keys[0] < keys[0])   // updating order of children
    	{
			newKeys[0] = pushedNode.keys[0];   // adds the newly added key to newKeys
			newKeys[1] = this.keys[0];         // copies original key to newKeys 
			newKeys[2] = this.keys[1];
    	    updatedChildren[0] = pushedNode.getChildren()[0];
    	    updatedChildren[1] = pushedNode.getChildren()[1];
    	    updatedChildren[2] = children[1];
			updatedChildren[3] = children[2];
    	}
    	else if(pushedNode.keys[0] < keys[1])
    	{
			newKeys[0] = this.keys[0];         // copies original key to newKeys 
			newKeys[1] = pushedNode.keys[0];   // adds the newly added key to newKeys
			newKeys[2] = this.keys[1];
    		updatedChildren[0] = children[0];
    	    updatedChildren[1] = pushedNode.getChildren()[0];
    	    updatedChildren[2] = pushedNode.getChildren()[1];
			updatedChildren[3] = children[2];
    	}
		else								   // 
		{
			newKeys[0] = this.keys[0];         // copies original key to newKeys 
			newKeys[1] = this.keys[1];
			newKeys[2] = pushedNode.keys[0];   // adds the newly added key to newKeys
    		updatedChildren[0] = children[0];
			updatedChildren[1] = children[1];
			updatedChildren[2] = pushedNode.getChildren()[0];
    	    updatedChildren[3] = pushedNode.getChildren()[1];
		}
    	
    	updatedNode = new Node4(newKeys, updatedChildren, parent, coord);
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
	
    public Node findPath(int n)
    //PRE:	n is initialized
    //POST:	FCTVAL == the next node on the path to find location of n.
    //		FCTVAL == null if n is not in the tree
    {
    	if(n < keys[0])				//n is less than first key
    	{
    		return children[0];
    	}
    	else if(n < keys[1])		//n is between first key and last key
    	{
    		return children[1];
    	}	
    	else if (n > keys[2])		//n greater than the last key
    	{
    		return children[2];
    	}

    	return null;
    }
}