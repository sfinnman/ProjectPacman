package utility;

import utility.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import game.DEBUG;

public class EventHandler {
	private static Map<String, List<Listener>> events;
	private static Deque<AbstractMap.SimpleEntry<String, Listener>> removals;
	private static Deque<AbstractMap.SimpleEntry<String, Listener>> additions;
	private static boolean working;
	
	private EventHandler(){};
	
	public static boolean registerEvent(String key) {
		
		if (events.containsKey(key)) {
			return false;
		}
		events.put(key, new LinkedList<>());
		return true;
	}
	
	public static void subscribeEvent(String key, Listener listener){
		if (working) {
			EventHandler.queueSubscribeEvent(key, listener); //Cannot remove entries while executing an event, leads to concurrency issues.
			return;
		}
		DEBUG.print(listener.toString() + " trying to register as a Listener on " + key);
		if (!events.containsKey(key)) {
			DEBUG.print("events didnt contain key " + key);
			return;
		}
		DEBUG.print(listener.toString() + " registered as a Listener on " + key);
		events.get(key).add(listener);
		return;
	}
	
	private static void queueSubscribeEvent(String key, Listener listener){
		additions.push(new AbstractMap.SimpleEntry<String, Listener>(key, listener)); //Land here when we are working
	}

	public static void unsubscribeEvent(String key, Listener listener){
		if (working) {
			EventHandler.queueUnsubscribeEvent(key, listener); //Cannot remove entries while executing an event, leads to concurrency issues.
			return;
		}
		if (!events.containsKey(key)) {
			return;
		}
		events.get(key).remove(listener);
		return;
	}
	
	private static void queueUnsubscribeEvent(String key, Listener listener){
		removals.push(new AbstractMap.SimpleEntry<String, Listener>(key, listener)); //Land here when we are working
	}
	
	private static void flush(){ //flushes the entries to be removed!
		while(!removals.isEmpty()){
			AbstractMap.SimpleEntry<String, Listener> entry = removals.pop();
			EventHandler.unsubscribeEvent(entry.getKey(), entry.getValue());
		}
		while(!additions.isEmpty()){
			AbstractMap.SimpleEntry<String, Listener> entry = additions.pop();
			EventHandler.subscribeEvent(entry.getKey(), entry.getValue());
		}
	}
	
	public static synchronized boolean triggerEvent(String key, EventData data){
		if (!events.containsKey(key)) {
			return false;
		}
		working = true; // Make sure to stop all unsubscribes during execution!
		List<Listener> hooks = events.get(key);
		for (int i = 0; i<hooks.size(); i++){
			hooks.get(i).onRegister(key, data);
		}
		working = false;
		flush();
		return true;
	}
	
	public static synchronized void init(){
		System.out.println("EventHandler initializing");
		events = new HashMap<>();
		removals = new LinkedList<>();
		additions = new LinkedList<>();
		working = false;
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
		for (String key : events.keySet()) {
			EventHandler.unsubscribeEvent(key, listener);
		}
	}
	
	public static void show(){
		DEBUG.print(events);
		DEBUG.print(removals);
		DEBUG.print(additions);
	}
	
	public static class EventData {
		public final Object src; //worst case!
		public final Point p;
		public EventData(Object src, Point p){
			this.src = src;
			this.p = p;
		}
	}
	
}
