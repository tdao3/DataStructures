// Programmer:  TO, AB, TD
// Assignment:  Group Project 2
// Date:        November 8th, 2015
// Description: The class models the general functions of the nodes that will be
//              placed and used in the 2-3-4 tree.

import java.awt.Graphics;

public abstract class Node
{
    public static final int NODE_SPACING_X = 15; // width between drawn nodes
    public static final int NODE_SPACING_Y = 25;
    public static final int HEIGHT = 50;  // height of all nodes
    
    protected int[] keys;         // the keys (1, 2, or 3) of the node
    protected Node[] children;    // the children (2, 3, or 4) of the node
    protected Node parent;        // the parent of this node (null if root)

    protected int subtreeWidth;   // used for spacing nodes in drawing
    
    protected boolean lastNode;   // true if node was the last node in the stepping process,
                                  // false otherwise
    
    protected boolean selected;   // true if node is found during search, or is the inserted node
                                  // false otherwise
    
    protected ScaledPoint coord;  // used to represent location of node based on varying applet size

    public abstract void drawNode(Graphics g, Node current); // subclasses draws the corresponding node 

    public abstract Node findPath(int n);           // subclasses finds path taken to get 
                                                    // to node being searched
    
    public abstract int getNodeWidth();             // returns the width of the type of node    
    
    public abstract Node deleteLeafKey(int key);    // deletes the key from the leaf node
    
    public void drawTree(Graphics g, Node root, Node current)
    // PRE:  g is the Graphics context to be drawn in, root and current 
    //       are both initialized.
    // POST: all nodes in the tree have drawNode invoked upon them, recursively
    //       from the root to the leaves
    {
        root.drawNode(g, current);
    }
    
    public void setParent(Node parent)
    // PRE:  parent is null if no parent or is initialized otherwise
    // POST: class member parent equals the parameter Node parent
    {
        this.parent = parent;
    }
    
    public void setCoord(ScaledPoint coord)
    // PRE:  coord is initialized
    // POST: class member coord is set to the parameter coord
    {
        this.coord = coord;
    }
    
    public void updateChildPtr(Node oldChild, Node newChild)
    // PRE:  oldChild and newChild are initialized
    // POST: the pointer in children to oldChild now points to newChild.
    //       to be used in the event of a child node being updated between types
    {
        // iterate through the children nodes to find the oldChild
        for(int i = 0; i < children.length; i++)
        {
            if(children[i] == oldChild) // if oldChild is found
            {
            	
                children[i] = newChild; // set it to newChild
            }
        }
    }
    
    public boolean hasKey(int n)
    // PRE:  n is initialized
    // POST: FCTVAL == true if this node has the key n inside it, false otherwise
    {
        // iterate through the keys of the node
        for(int k : keys)
        {
            if(n == k) // key found inside the node
            {
                return true;
            }
        }
        // the key wasn't found inside the node
        return false;
    }
    
    public boolean isLeaf()
    // POST: FCTVAL == TRUE if this Node has no children, FALSE otherwise
    {
        // iterate through children to see if any aren't null
        // there should really be only 2 cases, all children are null or no children are null
        
        if(children[0] == null) // if the child doesn't exists
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    public boolean isRoot()
    // POST: FCTVAL == TRUE if parent is null, FALSE otherwise
    {
        if(parent == null) // if the node has no parent
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public int[] getKeys()
    // POST: FCTVAL == an int[] keys of this Node
    {
        return keys;
    }
    
    public Node[] getChildren()
    // POST: FCTVAL == a Node[] of children of this Node
    {
        return children;
    }
    
    public Node getParent()
    // POST: FCTVAL == the parent of this Node
    {
        return parent;
    }
    
    public ScaledPoint getCoord()
    // POST: FCTVAL == the ScaledPoint coord of this Node
    {
        return coord;
    }
    
    public void setLastNode(boolean lastNode)
    // POST: class member lastNode is set to parameter lastNode
    {
        this.lastNode = lastNode;
    }
   
    public void setSelected(boolean selected)
    // POST: class member selected is set to parameter selected
    {
        this.selected = selected;
    }
    
    public int getSubtreeWidths()
    // POST: FCTVAL == the width of the subtree of the Node that this method
    //       is invoked upon. Also, subtreeWidth is set to the sum of the children's
    //       subtrees, or the width of the Node(if isLeaf)
    {
        if(isLeaf())
        {
            subtreeWidth = getNodeWidth();
            return subtreeWidth;
        }
        else
        {
            subtreeWidth = NODE_SPACING_X/2; // start as a buffer value for left spacing
            
            // iterate through each child and find and add child's subtreeWidth
            // plus a buffer value to space out the nodes
            for(Node n : children)
            {
               subtreeWidth += n.getSubtreeWidths() + NODE_SPACING_X;
            }
            
            // subtract half of the buffer distance on right side
            subtreeWidth -= NODE_SPACING_X/2;
         
            return subtreeWidth;
        }
    }
    
    
    public void setLastNodeAll()
    //PRE: Must be called by a root node object.
    //POST: Recursively traverse through every node in the tree and set class
    //      member lastNode to false.
    {
    	lastNode = false;
    	
    	if(!isLeaf())  // if this node has children
    	{
    		// iterate through each child and set lastNode to false
            for(Node n : children)
            {
               n.setLastNodeAll();
            }     
    	}
  
    }
    
    public void setSelectedAll()
    //PRE: Must be called by a root node object.
    //POST: Recursively traverse through every node in the tree and set class
    //      member selected to false.
    {
    	selected = false;
    	
    	if(!isLeaf())  // if this node has children
    	{
    		// iterate through each child and set selected to false
            for(Node n : children)
            {
               n.setSelectedAll();
            }     
    	}
  
    }
    
    public void repositionNodes()
    // PRE:  to be used after getSubtreeWidths() has updated the subtreeWidth 
    //       of each Node
    // POST: repositions all the nodes of the tree from top down so they are 
    //       evenly spaced. 
    {
        int totalChildrenWidth; // width of the node's children's subtrees
        int subtreeStartX;      // the x coordinate of the leftmost corner of
                                // this node's subtree
        
        int currentX;           // used during the repositioning of the children
                                // of this node.
                                
        totalChildrenWidth = 0; // begins at zero before adding up
        
        if(isRoot())            // if this node is the root set at specific position
        {
            // set it at the top middle of the screen
            coord = new ScaledPoint(.5, .25);
            // shift over slightly so it is centered on screen
            coord.setX(coord.getX() - (getNodeWidth()/2));
        }
        
        if(!isLeaf())      // if this node is not a leaf then position its children
        {
            //for(Node n : children) // iterate through children adding up their subtreeWidths
            //{
            //    totalChildrenWidth += n.subtreeWidth;
            //}
            
            // the start is the top left corner of this node, minus half the width of the children
            // plus half of the width of this node to center it again
            subtreeStartX = (coord.getX() + (getNodeWidth()/2)) - (subtreeWidth / 2);
            
            currentX = subtreeStartX + NODE_SPACING_X/2; // given initial left side buffer
            for(Node n : children) // iterate through children and position them
            {
                n.coord.setX(currentX + (n.subtreeWidth/2)- (n.getNodeWidth()/2));
                n.coord.setY(coord.getY() + HEIGHT + NODE_SPACING_Y);
                
                currentX += n.subtreeWidth + NODE_SPACING_X;
                
                n.repositionNodes(); // once this child's position is set, 
                                     // position this child's children
            }
            
        }
        
        
        // if it is a leaf and not the root then there is nothing for the node to do
        // it will already be in the right position
    }
}