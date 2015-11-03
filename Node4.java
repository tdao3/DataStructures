import java.awt.Graphics;

public class Node4 extends Node
{
    public Node4()
    // POST: a new Node4 is created with keys of {1, 2, 3} and children 
    //       of {null, null, null, null}
    {
        this(new int[]{1, 2, 3}, null, null);
    }
    
    public Node4(int[] keys, Node[] children, Node parent)
    // PRE:  keys is an array of length 3 with 3 values in it and children is
    //       an array which is null denoting no children, or an array of length
    //       4 with 4 initialized Nodes in it. parent is null if the node has no
    //       parent, or otherwise is initialized.
    // POST: a new Node4 is created with class member keys and children equal to 
    //       the parameters keys and children, respectively
    {
        this.keys = new int[3];         // allocate the array of keys
        this.children = new Node[4];
        
        this.keys[0] = keys[0];
        this.keys[1] = keys[1];
        this.keys[2] = keys[2];
        
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
    }
    
    public void drawNode(Graphics g)
    {
        // to be determined
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
        else if(n < keys[2])
        {
            return children[2];
        }
        else
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
        
        Node2 lChild = new Node2(keys[0], new Node[]{children[0], children[1]});
        Node2 rChild = new Node2(keys[2], new Node[]{children[2], children[3]});
        Node2 newParent = new Node2(keys[1], new Node[]{lChild, rChild});
        
        lChild.setParent(newParent);
        rChild.setParent(newParent);
        
        if(parent != null) // this is not the root node
        {
            return parent.absorbNode(newParent);
        }
        // otherwise just return the newParent node
        return newParent;
    }
}