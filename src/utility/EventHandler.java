package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import game.DEBUG;

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
		DEBUG.print(listener.toString() + " trying to register as a Listener on " + key);
		if (!events.containsKey(key)) {
			DEBUG.print("events didnt contain key " + key);
			return false;
		}
		DEBUG.print(listener.toString() + " registered as a Listener on " + key);
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
		DEBUG.print(key + " triggered!");
		if (!events.containsKey(key)) {
			return false;
		}
		List<Listener> hooks = events.get(key);
		for (int i = 0; i<hooks.size(); i++){
			hooks.get(i).onRegister(key, data);
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
	
	public static void free(Listener listener) {
		Set<Entry<String, List<Listener>>> ES = events.entrySet();
		for (Entry<String, List<Listener>> Entry : ES) {
			Entry.getValue().remove(listener); //Frees all references to this object! Do not use this concurrently with loops!
		}
	}
	
	public static class EventData {
		public final Object src; //worst case!
		public final int x;
		public final int y;
		public EventData(Object src, int x, int y){
			this.src = src;
			this.x = x;
			this.y = y;
		}
	}
	
}
