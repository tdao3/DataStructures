import java.awt.Graphics;
import java.awt.Color;

public class Node4 extends Node
{
    public Node4()
    // POST: a new Node4 is created with keys of {1, 2, 3} and children 
    //       of {null, null, null, null}
    {
        this(new int[]{1, 2, 3}, null, null, new ScaledPoint(0, 0));
    }
    
    public Node4(int[] keys, Node[] children, Node parent, ScaledPoint coord)
    // PRE:  keys is an array of length 3 with 3 values in it and children is
    //       an array which is null denoting no children, or an array of length
    //       4 with 4 initialized Nodes in it. parent is null if the node has no
    //       parent, or otherwise is initialized.
    // POST: a new Node4 is created with class member keys, children, and parent equal to 
    //       the parameters keys, children and parent, respectively
    {
        this.keys = new int[3];         // allocate the array of keys
        this.children = new Node[4];
        
        // sets all keys to the input keys
        this.keys[0] = keys[0];
        this.keys[1] = keys[1];
        this.keys[2] = keys[2];
        
        // sets all children, if they exist, to the input children
        if(children != null)
        {
            this.children[0] = children[0];
            this.children[1] = children[1];
            this.children[2] = children[2];
            this.children[3] = children[3];
        }
        else
        {
            this.children[0] = null;
            this.children[1] = null;
            this.children[2] = null;
            this.children[3] = null;
        }
        
        this.parent = parent;
		
		this.coord = coord;
    }
    
    public void drawNode(Graphics g, boolean selected)
    {
        // 4: [ | | ] 3: [ | ] 2: [ ] 
		
		int nodeX;		// x coord of top left corner of node
		int nodeY;		// y coord of top left corner of node
		
		// get the drawing coordinates of the node at this window size
		
		nodeX = coord.getX(g.getWidth());
		nodeY = coord.getY(g.getHeight());
		
		if(selected)
		{
			g.setColor(120, 255, 120); // selected so set to light green
			g.fillRect(nodeX, nodeY, 75, 25); // fill background green
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(nodeX, nodeY, 75, 25); // draw black border
		
		// draw first divider
		g.drawLine(nodeX + 25, nodeY),
				   nodeX + 25, nodeY + 25);
		
		// draw second divider
		g.drawLine(nodeX + 50, nodeY),
				   nodeX + 50, nodeY + 25);
		
		// draw numbers of 3 keys
		g.drawString(keys[0], nodeX + 2, nodeY + 15);
		g.drawString(keys[1], nodeX + 27, nodeY + 15);
		g.drawString(keys[2], nodeX + 52, nodeY + 15);
		
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

    public Node findPath(int n)
    // PRE:  n is initialized
    // POST: FCTVAL == the Next node on the path to find the location of n.
    //       FCTVAL == null if n is not in the tree (reached a leaf node in search)
    {
        if(n < keys[0]) // n is less than the first key
        {
            return children[0];
        }
        // n is not less than the first key, and is less than the second key
        else if(n < keys[1])
        {
            return children[1];
        }
        else if(n < keys[2])        //n is between the second and third keys
        {
            return children[2];
        }
        else                        //n is greater than last key
        {
            return children[3];
        }
    }
    
    public Node splitNode4()
    // POST: this Node4 will be split into 3 x Node2, and if this is not the root, the
    //       middle Node2 will be absorbed by the parent node. FCTVAL == the parent
    //       Node of the two child Node2
    {
        // creating 3 new Node2 by splitting the Node4
        
        Node2 lChild = new Node2(keys[0], new Node[]{children[0], children[1]}, null);
        Node2 rChild = new Node2(keys[2], new Node[]{children[2], children[3]}, null);
        Node2 newParent = new Node2(keys[1], new Node[]{lChild, rChild}, null);
        
        lChild.setParent(newParent);
        rChild.setParent(newParent);
        
        if(parent != null) // this is not the root node
        {
        	if(parent instanceof Node2)  //If the parent is a Node2 object
        	{
                return ((Node2)(parent)).absorbNode(newParent);
        	}
        	else   //If the parent is a Node3 object
        	{
        	    return ((Node3)(parent)).absorbNode(newParent);
        	}
        }
        // otherwise just return the newParent node
        return newParent;
    }
}
