package game;

public class DEBUG {
	
	public static boolean ENABLED = false;
	
	public static void DEBUG_MSG(String str){
		if (ENABLED) {
			System.out.println(str);
		}
	}
	
	private DEBUG(){}
}
