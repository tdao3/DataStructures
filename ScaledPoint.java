public class ScaledPoint
{
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

	public int getX(int width)
	// PRE:  width > 0
	// POST: FCTVAL == class member x * width
	{
		return (int)(x * width);
	}
	
	public int getY(int height)
	// PRE:  height > 0
	// POST: FCTVAL == class member y * height
	{
		return (int)(y * height);
	}
}
