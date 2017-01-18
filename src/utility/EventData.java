package utility;

public class EventData {
	public final Object src; //worst case!
	public final int x;
	public final int y;
	public EventData(Object src, int x, int y){
		this.src = src;
		this.x = x;
		this.y = y;
	}
}