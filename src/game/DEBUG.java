package game;

public class DEBUG {
	
	public static boolean ENABLED = false;
	
	public static void print(String str){
		if (ENABLED) {
			System.out.println(str);
		}
	}
	
	private DEBUG(){}
}
