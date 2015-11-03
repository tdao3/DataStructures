import java.awt.Graphics;

public class Node2 extends Node
{
	
	public Node2()
    //POST: A default Node2 object is created with a key set to 1, child nodes and
	//      parent node set to null.
    {
        this(1, null, null); 
    }
	
    public Node2(int key, Node[] children, Node parent)
    //PRE: key, and parent initialized. children initialized with 2 initialized 
    //     pointers in the array.
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
        
    }

    @Override
    public void drawNode(Graphics g) 
    {
	    // TODO Auto-generated method stub
    	
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
    	Node[] updatedChildren;    // new node containing 3 children 
    	
        // allocating space for the 2 new keys and 3 children (format of Node3)
    	newKeys = new int[2];
    	updatedChildren = new Node[3]; 
    
    	newKeys[0] = this.keys[0];         //copies original key to newKeys 
    	newKeys[1] = pushedNode.keys[0];   //adds the newly added key to newKeys
    	
    	if(pushedNode.keys[0] < keys[0])
    	{
    	    updatedChildren[0] = pushedNode.getChildren()[0];
    	    updatedChildren[1] = pushedNode.getChildren()[1];
    	    updatedChildren[2] = children[1];
    	}
    	else
    	{
    		updatedChildren[0] = children[0];
    	    updatedChildren[1] = pushedNode.getChildren()[0];
    	    updatedChildren[2] = pushedNode.getChildren()[1];
    	}
    	
    	Node3 updatedNode = new Node3(newKeys, updatedChildren, parent);
    	parent.updateChildPtr(this, updatedNode);
    	
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
