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
	private static final EventHandler self = new EventHandler();
	private final List<String> keySet = new LinkedList<>();
	private final Deque<Map<String, List<Listener>>> eventStack;
	private final Deque<List<Listener>> executionStack;
	private final Deque<SimpleEntry<String, Listener>> removals;
	private final Deque<SimpleEntry<String, Listener>> additions;

	private EventHandler() {
		System.out.println("EventHandler initializing");
		eventStack = new LinkedList<>();
		executionStack = new LinkedList<>();
		removals = new LinkedList<>();
		additions = new LinkedList<>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("src/config/EventHandler.cfg"));
			while (sc.hasNextLine()) {
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
		System.out.println("EventHandler initialized!");
		pushLayer();
		System.out.println(eventStack.peek());
	}
	
	public static EventHandler instance(){
		return self;
	}

	private void registerEvent(String key) {
		if (eventStack.peek().containsKey(key)) {
			return;
		}
		eventStack.peek().put(key, new LinkedList<>());
	}

	public void subscribeEvent(String key, Listener listener) {
		if (!executionStack.isEmpty()) {
			this.queueSubscribeEvent(key, listener);
			return;
		}
		if (!eventStack.peek().containsKey(key)) {
			return;
		}
		eventStack.peek().get(key).add(listener);
	}

	private void queueSubscribeEvent(String key, Listener listener) {
		additions.push(new SimpleEntry<String, Listener>(key, listener));
	}

	public void unsubscribeEvent(String key, Listener listener) {
		if (!executionStack.isEmpty()) {
			this.queueUnsubscribeEvent(key, listener);
			return;
		}
		if (!eventStack.peek().containsKey(key)) {
			return;
		}
		eventStack.peek().get(key).remove(listener);
	}

	private void queueUnsubscribeEvent(String key, Listener listener) {
		removals.push(new SimpleEntry<String, Listener>(key, listener));
	}

	private void flush() {
		while (!removals.isEmpty()) {
			SimpleEntry<String, Listener> entry = removals.pop();
			this.unsubscribeEvent(entry.getKey(), entry.getValue());
		}
		while (!additions.isEmpty()) {
			SimpleEntry<String, Listener> entry = additions.pop();
			this.subscribeEvent(entry.getKey(), entry.getValue());
		}
	}

	public synchronized void triggerEvent(String key, EventData data) {
		if (!eventStack.peek().containsKey(key)) {
			return;
		}
		List<Listener> hooks = new LinkedList<>();
		hooks.addAll(eventStack.peek().get(key));
		executionStack.push(hooks);
		for (int i = 0; i < hooks.size(); i++) {
			hooks.get(i).onRegister(key, data);
		}
		executionStack.pop();
		if (executionStack.isEmpty()) {
			flush();
		}
	}

	public synchronized void pushLayer() {
		this.killCurrent();
		eventStack.push(new HashMap<>());
		for (String key : keySet) {
			this.registerEvent(key);
		}
	}

	public synchronized void popLayer() {
		this.killCurrent();
		if (!eventStack.isEmpty()) {
			eventStack.pop();
		}
	}

	public synchronized void killCurrent() {
		for (List<Listener> hooks : executionStack) {
			hooks.clear();
		}
		removals.clear();
		additions.clear();
	}

	public synchronized void clear() {
		popLayer();
		pushLayer();
	}

	public void free(Listener listener) {
		for (String key : eventStack.peek().keySet()) {
			this.unsubscribeEvent(key, listener);
		}
	}

	@Override
	public String toString() {
		return eventStack.peek().toString() + System.lineSeparator() + removals.toString() + System.lineSeparator()
				+ additions.toString();
	}

	public static class EventData {
		public final Object src; // worst case!
		public final Point p;

		public EventData(Object src, Point p) {
			this.src = src;
			this.p = p;
		}
	}

}
