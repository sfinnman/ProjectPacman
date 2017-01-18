package game.utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventHandler {
	private static Map<String, List<Listener>> events = new HashMap<>();
	
	private EventHandler(){};
	
	public static boolean initEvent(String key) {
		if (events.containsKey(key)) {
			return false;
		}
		events.put(key, new LinkedList<>());
		return true;
	}
	
	public static boolean listenEvent(String key, Listener listener){
		if (!events.containsKey(key)) {
			return false;
		}
		events.get(key).add(listener);
		return true;
	}
	
	public static boolean triggerEvent(String key, Object src){
		if (events.containsKey(key)) {
			return false;
		}
		List<Listener> hooks = events.get(key);
		for (Listener hook : hooks) {
			hook.onRegister(key, src);
		}
		return true;
	}
	
}
