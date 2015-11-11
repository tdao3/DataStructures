// Programmer:  TO, AB, TD
// Assignment:  Group Project 2
// Date:        November 8th, 2015
// Description: The class models one type of node that will be placed into the tree. Node2
//				has exactly two children and one key value.

import java.awt.Graphics;
import java.awt.Color;

public class Node2 extends Node
{
    public static final int WIDTH = 50; // width of this node
    
    public Node2()
    //POST: A default Node2 object is created with a key set to 1, child nodes and
    //      parent node set to null, coordinates of 0,0, lastNode, and selected set to false.
    {
        this(1, null, null, new ScaledPoint(0, 0)); 
    }
    
    public Node2(int key, Node[] children, Node parent, ScaledPoint coord)
    //PRE: key, and parent initialized. children initialized with 2 initialized 
    //     pointers in the array, and coord is initialized.
    //POST: A Node2 object is created with a key set to key, child nodes set to 
    //      corresponding pointers in children, a parent node set to parent,
    //      and coordinates set to coord, subtreeWidth initialized to 0,
    //      lastNode, and selected set to false.
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
        selected = false;
        
        subtreeWidth = 0;
    }

    @Override
    public void drawNode(Graphics g, Node current) 
    //PRE: g and selected are initialized.
    //POST: a rectangle 2 node and connector lines to its children are drawn
    //      and its color is based on whether this node is the current node 
    //      in the step process, the last node accessed in the stepping process,
    //      or the selected node.
    {
        int nodeX;     // x coord of top left corner of node
        int nodeY;     // y coord of top left corner of node
        
        nodeX = coord.getX();
        nodeY = coord.getY();
        
        if(selected)   // if this is the selected node 
        {
        	g.setColor(new Color(120,255,120));  //green
            g.fillRect(nodeX, nodeY, WIDTH, HEIGHT);
        }
        else if(lastNode)   //If this is the last node in the stepping process
        {
            g.setColor(new Color(255, 120, 120));
            g.fillRect(nodeX, nodeY, WIDTH, HEIGHT);
        }
        else if(this == current)    //If this is the current node  
        {
            g.setColor(new Color(200, 200, 200));   //grey
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
            if(children[i] != null) // if the child isn't null
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
                               children[i].coord.getX() + Node4.WIDTH/2,
                               children[i].coord.getY());
                }
                
                children[i].drawNode(g, current); // draw the child
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
        if(!isRoot())
        {
            parent.updateChildPtr(this, updatedNode); //the new Node3 becomes the parent of 
                                                      //    all the previous children
        }
        
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

    public Node3 insertNode(int insertKey)
    //PRE:  inserted is initialized
    //POST: An object of Node3 is created with keys being a combination of the original key
    //      and the new inserted key. The new object will have the same parent, children and
    //      coord as the original node
    {
        //Create a new key array for the new values
        int[] newKeys = new int[2];

        //insert the keys in correct value order 
        if(this.keys[0] < insertKey)      //insert key is greater than orig. key
        {
            newKeys[0] = this.keys[0];
            newKeys[1] = insertKey;       //insert the key at the end 
        }
        else                              //orig. key is greater than the new key
        {
            newKeys[0] = insertKey;       //inser the key at the beginning 
            newKeys[1] = this.keys[0];
        }

        // new now has 2 values, the same children, parent , and coord 
        Node3 newNode = new Node3(newKeys, null, this.parent, this.coord);
        
        return newNode;
    }

	@Override
	public Node deleteLeafKey(int key) 
	{
		Node sibling;     //Sibling of this node
		Node newSibling;  //New sibling node if rotation occurs
		int[] newKeys;    //New key values for the new sibling node
		
		//Initialize to default values
		sibling = null;
		newSibling = null;
		newKeys = null;
		
        //Case 1: check if we can steal a key from a sibling Node3 or Node4
		//        or if fusion must take place
		
		if(parent instanceof Node2)  // if parent is a Node2
		{
		    if(key < parent.getKeys()[0])  // if this node is the left child of the parent
		    {
		        //Set sibling to right child
		        sibling = parent.getChildren()[1];
		        
		        if(sibling instanceof Node4)  // if sibling is a Node4
		        {
		            //Rotate with the Node4 sibling
		            siblingNode4Rotation(sibling, newSibling, newKeys, false, false, false);
		        }
		        else if(sibling instanceof Node3)  // if sibling is a Node3
		        {
		            //Rotate with the Node3 sibling
		            siblingNode3Rotation(sibling, newSibling, newKeys, false, false, false);
		        }
		    }
		    else if(key > parent.getKeys()[0])  // if this node is the right child of the parent
		    {
		        //Set sibling to left child
		    	sibling = parent.getChildren()[0];
		    	
		    	if(sibling instanceof Node4)   // if sibling is a Node4
		        {
		    		//Rotate with the Node4 sibling
		    		siblingNode4Rotation(sibling, newSibling, newKeys, true, false, false);
		        }
		    	else if(sibling instanceof Node3)   // if sibling is a Node3
		        {
		    		//Rotate with the Node3 sibling
		        	siblingNode3Rotation(sibling, newSibling, newKeys, true, false, false);
		        }
		    }
		}
		else if(parent instanceof Node3)   // if the parent is a Node3
		{
			if(key < parent.getKeys()[0])   // if this node is the leftmost child
		    {
			    //Set the sibling to the middle child
		        sibling = parent.getChildren()[1];
		        
		        if(sibling instanceof Node4)   // if sibling is a Node4
		        {
		        	//Rotate with the Node4 sibling
		        	siblingNode4Rotation(sibling, newSibling, newKeys, false, false, false);
		        }
		        else if(sibling instanceof Node3)   // if sibling is a Node3
		        {
		        	//Rotate with the Node3 sibling
		        	siblingNode3Rotation(sibling, newSibling, newKeys, false, false, false);
		        }
		    }
			else if(key < parent.getKeys()[1])   // if this node is the middle child
			{
			    //Check to steal from left sibling first
			    if(parent.getChildren()[0] instanceof Node3 || parent.getChildren()[0] instanceof Node4) // if left sibling can be stolen from
			    {
			        //Set sibling to left child
			        sibling = parent.getChildren()[0];
			        
			        if(sibling instanceof Node4)   // if sibling is a Node4
			        {
			        	//Rotate with the Node4 sibling
			            siblingNode4Rotation(sibling, newSibling, newKeys, true, false, false);
			        }
			    	else if(sibling instanceof Node3)   // if sibling is a Node3
			        {
			    		//Rotate with the Node3 sibling
			        	siblingNode3Rotation(sibling, newSibling, newKeys, true, false, false);
			        }
			        
			        
			    }
			    else if(parent.getChildren()[2] instanceof Node3 || parent.getChildren()[2] instanceof Node4)  // if right sibling can be stolen from
			    {
			        //Set sibling to right child
			        sibling = parent.getChildren()[2];
			        
			        if(sibling instanceof Node4)   // if sibling is a Node4
			        {
			        	//Rotate with the Node4 sibling
			        	siblingNode4Rotation(sibling, newSibling, newKeys, false, true, false);
			        }
			    	else if(sibling instanceof Node3)   // if sibling is a Node3
			        {
			    		//Rotate with the Node3 sibling
			        	siblingNode3Rotation(sibling, newSibling, newKeys, false, true, false);
			        }
			    }
			}
			else if(key > parent.getKeys()[1])   // if this node is the rightmost child
		    {
			    //Set sibling to middle child
		        sibling = parent.getChildren()[1];
		        
		        if(sibling instanceof Node4)   // if sibling is a Node4
		        {
		        	//Rotate with the Node4 sibling
		            siblingNode4Rotation(sibling, newSibling, newKeys, true, false, true);
		        }
		    	else if(sibling instanceof Node3)   // if sibling is a Node3
		        {
		    		//Rotate with the Node3 sibling
		            siblingNode3Rotation(sibling, newSibling, newKeys, true, false, true);
		        }
		    }
		}
		return this;
        
	}
	
	private void siblingNode3Rotation(Node sibling, Node newSibling, int[] newKeys, boolean leftSibling, boolean middleChild, boolean rightChild)
	{
	    //Allocate key for a Node2 object
        newKeys = new int[1];
        
	    if(leftSibling)   // if this sibling is a left sibling
	    {
	        if(rightChild) // if this node is the rightmost child of a Node3 parent
	        {
	            //Rotate values accordingly
	        	keys[0] = parent.getKeys()[1];
	            parent.getKeys()[1] = sibling.getKeys()[1];
	            newKeys[0] = sibling.getKeys()[0];
	        }
	        else  // this node is a right child of a Node2 parent
	        {
	        	//Rotate values accordingly
	            keys[0] = parent.getKeys()[0];
	            parent.getKeys()[0] = sibling.getKeys()[1];
	            newKeys[0] = sibling.getKeys()[0];
	        }
         
	    }
	    else   // this sibling is a right sibling
	    {
	        if(middleChild) // if this node is the middle child of a Node3 parent
	        {
	        	//Rotate values accordingly
	            keys[0] = parent.getKeys()[1];
	            parent.getKeys()[1] = sibling.getKeys()[0];
	            newKeys[0] = sibling.getKeys()[1];
	        }
	        else  // this node is a left child of a Node2 parent
	        {
	        	//Rotate values accordingly
	        	keys[0] = parent.getKeys()[0];
	            parent.getKeys()[0] = sibling.getKeys()[0];
	            newKeys[0] = sibling.getKeys()[1];
	        } 
	    }
	    
	    //Create the updated sibling and have the old sibling parent point to the new one
	    newSibling = new Node2(newKeys[0], new Node[]{null, null}, parent, sibling.getCoord());
        parent.updateChildPtr(sibling, newSibling);
	}
	
	
	private void siblingNode4Rotation(Node sibling, Node newSibling, int[] newKeys, boolean leftSibling, boolean middleChild, boolean rightChild)
	{
	    //Allocate new keys for a Node3 object
        newKeys = new int[2];
        
	    if(leftSibling)   // if this sibling is a left sibling
	    {
	        if(rightChild) // if this node is the rightmost child of a Node3 parent
	        {
	        	//Rotate values accordingly
	        	keys[0] = parent.getKeys()[1];
	            parent.getKeys()[1] = sibling.getKeys()[2];
	            newKeys[0] = sibling.getKeys()[0];
	            newKeys[1] = sibling.getKeys()[1];
	        }
	        else  // this node is a right child of a Node2 parent
	        {
	        	//Rotate values accordingly
	        	keys[0] = parent.getKeys()[0];
	            parent.getKeys()[0] = sibling.getKeys()[2];
	            newKeys[0] = sibling.getKeys()[0];
	            newKeys[1] = sibling.getKeys()[1];
	        }
         
	    }
	    else   // this sibling is a right sibling
	    {
	        if(middleChild) // if this node is the middle child of a Node3 parent
	        {
	        	//Rotate values accordingly
	        	keys[0] = parent.getKeys()[1];
	            parent.getKeys()[1] = sibling.getKeys()[0];
	            newKeys[0] = sibling.getKeys()[1];
	            newKeys[1] = sibling.getKeys()[2];
	        }
	        else  // this node is a left child of a Node2 parent
	        {
	        	//Rotate values accordingly
	        	keys[0] = parent.getKeys()[0];
	            parent.getKeys()[0] = sibling.getKeys()[0];
	            newKeys[0] = sibling.getKeys()[1];
	            newKeys[1] = sibling.getKeys()[2];
	        } 
	    }
	    
	    //Create the updated sibling and have the old sibling parent point to the new one
	    newSibling = new Node3(newKeys, new Node[]{null, null, null}, parent, sibling.getCoord());
        parent.updateChildPtr(sibling, newSibling);
	}
}
