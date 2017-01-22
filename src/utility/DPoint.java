package utility;

public class DPoint {
	public final double x;
	public final double y;

	public DPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double distance(DPoint p) {
		double x = this.x - p.x;
		double y = this.y - p.y;
		return Math.sqrt(x*x + y*y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DPoint) {
			DPoint p = (DPoint) obj;
			return this.x == p.x && this.y == p.y;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.x + ", " + this.y;
	}

}
