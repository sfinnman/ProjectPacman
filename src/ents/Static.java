package ents;

abstract class Static {
	protected final int x;
	protected final int y;
	
	protected Static(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString(){
		return String.format("|%d, %d| ", x, y);
	}
	
}
