import java.awt.Graphics;

public abstract class Node
{
    private int[] keys;         // the keys (1, 2, or 3) of the node
    private Node[] children;    // the children (2, 3, or 4) of the node
    private Node parent;        // the parent of this node (null if root)

    private ScaledPoint coord;

    public abstract void drawNode(Graphics g);

    public abstract Node findPath(int n);

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
    
    public void setParent(Node parent)
    // PRE:  parent is null if no parent or is initialized otherwise
    // POST: class member parent equals the parameter Node parent
    {
        this.parent = parent;
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