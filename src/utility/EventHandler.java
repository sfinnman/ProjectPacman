package utility;

import utility.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.DEBUG;

public class EventHandler {
	private static final List<String> keySet = new LinkedList<>();
	private static Deque<Map<String, List<Listener>>> eventStack;
	private static Deque<List<Listener>> executionStack;
	private static Deque<SimpleEntry<String, Listener>> removals;
	private static Deque<SimpleEntry<String, Listener>> additions;
	
	private EventHandler(){};
	
	private static boolean registerEvent(String key) {
		if (eventStack.peek().containsKey(key)) {
			return false;
		}
		eventStack.peek().put(key, new LinkedList<>());
		return true;
	}
	
	public static void subscribeEvent(String key, Listener listener){
		if (!executionStack.isEmpty()) {
			EventHandler.queueSubscribeEvent(key, listener); //Cannot remove entries while executing an event, leads to concurrency issues.
			return;
		}
		if (!eventStack.peek().containsKey(key)) {
			DEBUG.print("eventStack.peek() didnt contain key " + key);
			return;
		}
		eventStack.peek().get(key).add(listener);
		return;
	}
	
	private static void queueSubscribeEvent(String key, Listener listener){
		additions.push(new SimpleEntry<String, Listener>(key, listener)); //Land here when we are working
	}

	public static void unsubscribeEvent(String key, Listener listener){
		if (!executionStack.isEmpty()) {
			EventHandler.queueUnsubscribeEvent(key, listener); //Cannot remove entries while executing an event, leads to concurrency issues.
			return;
		}
		if (!eventStack.peek().containsKey(key)) {
			return;
		}
		eventStack.peek().get(key).remove(listener);
		return;
	}
	
	private static void queueUnsubscribeEvent(String key, Listener listener){
		removals.push(new SimpleEntry<String, Listener>(key, listener)); //Land here when we are working
	}
	
	private static void flush(){ //flushes the entries to be removed!
		while(!removals.isEmpty()){
			SimpleEntry<String, Listener> entry = removals.pop();
			EventHandler.unsubscribeEvent(entry.getKey(), entry.getValue());
		}
		while(!additions.isEmpty()){
			SimpleEntry<String, Listener> entry = additions.pop();
			EventHandler.subscribeEvent(entry.getKey(), entry.getValue());
		}
	}
	
	public static synchronized boolean triggerEvent(String key, EventData data){
		if (!eventStack.peek().containsKey(key)) {
			return false;
		}
		List<Listener> hooks = new LinkedList<>();
		hooks.addAll(eventStack.peek().get(key));
		executionStack.push(hooks);
		for (int i = 0; i<hooks.size(); i++){ //Notice that if a view is popped it will not care really...
			hooks.get(i).onRegister(key, data);
		}
		executionStack.pop();
		if (executionStack.isEmpty()) {
			flush();
		}
		return true;
	}
	
	public static synchronized void pushLayer(){
		EventHandler.killCurrent();
		eventStack.push(new HashMap<>());
		for(String key: keySet){
			EventHandler.registerEvent(key);
		}
	}
	
	public static synchronized void popLayer(){
		EventHandler.killCurrent();
		if (!eventStack.isEmpty()) {
			eventStack.pop();
		}
	}
	
	public static synchronized void init(){
		System.out.println("EventHandler initializing");
		eventStack = new LinkedList<>();
		executionStack = new LinkedList<>();
		removals = new LinkedList<>();
		additions = new LinkedList<>();
		if (keySet.isEmpty()){
			Scanner sc = null;
			try {
				sc = new Scanner(new File("src/config/EventHandler.cfg"));
				while (sc.hasNextLine()){
					keySet.add(sc.nextLine());
				}
			} catch (FileNotFoundException e) {
				System.out.println("EventHandler configuration file was not found! ");
				e.printStackTrace();
			} finally {
				if (sc != null) {
					sc.close();
				}
			}
		}
		System.out.println("EventHandler initialized!");
		pushLayer();
		System.out.println(eventStack.peek());
	}
	
	public synchronized static void killCurrent(){
		for (List<Listener> hooks : executionStack){
			hooks.clear();
		}
		removals.clear();
		additions.clear();
	}
	
	public synchronized static void clear(){
		popLayer();
		pushLayer();
	}
	
	public static void free(Listener listener) {
		for (String key : eventStack.peek().keySet()) {
			EventHandler.unsubscribeEvent(key, listener);
		}
	}
	
	public static void show(){
		DEBUG.print(eventStack.peek());
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
