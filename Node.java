import java.awt.Graphics;

public abstract class Node
{
    protected int[] keys;         // the keys (1, 2, or 3) of the node
    protected Node[] children;    // the children (2, 3, or 4) of the node
    protected Node parent;        // the parent of this node (null if root)

    protected ScaledPoint coord;

    public abstract void drawNode(Graphics g);

    public abstract Node findPath(int n);
    
    public void setParent(Node parent)
    // PRE:  parent is null if no parent or is initialized otherwise
    // POST: class member parent equals the parameter Node parent
    {
        this.parent = parent;
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
        
        if(children[0] == null) // if the child exists
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