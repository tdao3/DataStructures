import java.awt.Graphics;

public class Node3 extends Node
{
	public Node3()
	//POST: An object of Node3 is created with keys {1,2} 
	//		and no parent or child values (both set to null)
	{
		this(new int[]{1,2}, null, null);
	}
	
	public Node3(int[] keys, Node[] children, Node parent)
	//PRE:	keys is an initialized array with exactly 2 values, children is a array
	//		with exactly 3 values or is null meaning there is no children, parent is 
	//		null if Node3 has no parent, otherwise initialized
	//POST: a new Node3 is created with class members keys, children, and parent equal to paramaters
	//		keys, children and parent
	{
		int i; 			//counter for loop

		this.keys = new int[2];			//allocate array of keys 
		this.children = new Node[4];	//allocate array of children

		this.keys[0] = keys[0];			//keys set to paramater keys 
		this.keys[1] = keys[1];

		if(children != null)			//sets children to be children parameters 
		{	
			for(i = 0; i < 3; i++)	
			{
				this.children[i] = children[i];
			}
		}

		this.parent = parent; 	//set parent of the node 
	}

	public void drawNode(Graphics g)
    {
        // TO DOOOOOOOOOOOOOOOOOOOO
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