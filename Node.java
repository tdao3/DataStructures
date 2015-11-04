import java.awt.Graphics;

public abstract class Node
{
    protected int[] keys;         // the keys (1, 2, or 3) of the node
    protected Node[] children;    // the children (2, 3, or 4) of the node
    protected Node parent;        // the parent of this node (null if root)

    protected ScaledPoint coord;

    public abstract void drawNode(Graphics g);      // subclasses draws the corresponding node 

    public abstract Node findPath(int n);           // subclasses finds path taken to get 
                                                    // to node being searched
    
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
        
        if(children[0] == null) // if the child doesnt exists
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
}