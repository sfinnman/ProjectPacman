package ents;

public abstract class Static {
	protected final int x;
	protected final int y;
	private final String name;
	
	protected Static(int x, int y, String name){
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	@Override
	public String toString(){
		return String.format(name + " |%d, %d| ", x, y);
	}
	
}
