public class ScaledPoint
{
	// static variables given starting values, MUST be updated
	// when paint() is called always
	private static int windowWidth = 600;	// total width of the window ScaledPoint refers to
	private static int windowHeight = 400;	// total height of the window ScaledPoint refers to

	private double x;					// percentage width across window
	private double y;					// percentage height across window
	
	public ScaledPoint()
	// POST: an instance of ScaledPoint is created with x and y percentages
	//		 both equal to 0.0
	{
		this(0.0, 0.0);
	}
	
	public ScaledPoint(double xPercent, double yPercent)
	// PRE:  1.0 >= xPercent and yPercent >= 0.0
	// POST: an instance of ScaledPoint is created with x and y percentages
	//		 equal to xPercent and yPercent, respectively
	{
		x = xPercent;
		y = yPercent;
	}
	
	public ScaledPoint(int x, int y, int width, int height)
	// PRE:  (0 <= x <= width) and (0 <= y <= height)
	// POST: an instance of ScaledPoint is created with class members 
	// 		 x and y equal to x/width, and y/height, respectively
	{
		this.x = ((double)x / width);
		this.y = ((double)y / height);
	}
	
	public void setX(int x, int width)
	// PRE:  0 <= x <= width
	// POST: class member x is set to the percentage (x/width)
	{
		this.x = ((double)x / width);
	}
	
	public void setY(int y, int height)
	// PRE:  0 <= y <= height
	// POST: class member y is set to the percentage (y/height)
	{
		this.y = ((double)y / height);
	}

	public void setWindowSize(int width, int height)
	// PRE:  width and height are both greater than 0, and are the 
	//		 current width of the window to be drawn in (for correctness
	//		 of output of the getX() and getY() methods
	{
		windowWidth = width;
		windowHeight = height;
	}
	
	public int getX()
	// PRE:  windowWidth is updated to the current height of the window
	//		 where this pixel value is being used to paint
	// POST: FCTVAL == class member x * windowWidth
	{
		return (int)(x * windowWidth);
	}
	
	public int getY()
	// PRE:  windowHeight is updated to the current height of the window
	//		 where this pixel value is being used to paint
	// POST: FCTVAL == class member y * windowHeight
	{
		return (int)(y * windowHeight);
	}
}
