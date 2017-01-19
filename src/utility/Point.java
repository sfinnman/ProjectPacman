package utility;

public class Point {
	public final int x;
	public final int y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y){
		this.x = (int)x;
		this.y = (int)y;
	}
	
	public boolean isInside(Point p, Point size){
		return (this.x - p.x)>0 && (this.x - p.x)<size.x && (this.y - p.y)>0 && (this.y - p.y)<size.y; 
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof Point) {
			Point p = (Point)obj;
			return this.x == p.x && this.y == p.y;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode(){
		return toString().hashCode();
	}
	
	@Override
	public String toString(){
		return this.x + ", " + this.y;
	}

}
