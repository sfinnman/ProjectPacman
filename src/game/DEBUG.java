package game;

public class DEBUG {
	
	public static boolean ENABLED = false;
	
	public static void print(Object str){
		if (ENABLED) {
			System.out.println(str.toString());
		}
	}
	
	private DEBUG(){}
}
