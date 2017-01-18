package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EventHandler {
	private static Map<String, List<Listener>> events = new HashMap<>();
	
	private EventHandler(){};
	
	public static boolean registerEvent(String key) {
		if (events.containsKey(key)) {
			return false;
		}
		events.put(key, new LinkedList<>());
		return true;
	}
	
	public static boolean subscribeEvent(String key, Listener listener){
		if (!events.containsKey(key)) {
			return false;
		}
		events.get(key).add(listener);
		return true;
	}

	public static boolean unsubscribeEvent(String key, Listener listener){
		if (!events.containsKey(key)) {
			return false;
		}
		events.get(key).remove(listener);
		return true;
	}
	
	public static boolean triggerEvent(String key, EventData data){
		if (events.containsKey(key)) {
			return false;
		}
		List<Listener> hooks = events.get(key);
		for (Listener hook : hooks) {
			hook.onRegister(key, data);
		}
		return true;
	}
	
	public static void init(){
		System.out.println("EventHandler initializing");
		Scanner sc = null;
		try {
			sc = new Scanner(new File("src/config/EventHandler.cfg"));
			while (sc.hasNextLine()){
				EventHandler.registerEvent(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("EventHandler configuration file was not found! ");
			e.printStackTrace();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		System.out.println("EventHandler initialized!");
		System.out.println(events);
	}
	
}
