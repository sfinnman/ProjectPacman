package game;

import java.util.ArrayList;
import java.util.List;

import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;

public class GhostStateSwitcher implements Listener{
	private List<Integer> times;
	private int liveTime;
	
	public GhostStateSwitcher(){
		restart();
		EventHandler.subscribeEvent("game_think", this);
		EventHandler.subscribeEvent("game_win", this);
		EventHandler.subscribeEvent("game_lose", this);
	}
	
	

	@Override
	public void onRegister(String key, EventData src) {
		switch(key){
		case("game_think"):
			liveTime++;
			if (times.get(0) - liveTime == 0) {
				times.remove(0);
				GameInfo.scatter();
			}
			break;
		case("game_win"):
			EventHandler.free(this);
			break;
		case("game_lose"):
			EventHandler.free(this);
			new GhostStateSwitcher();
			break;
		}
	}
	
	public void restart(){
		liveTime = 0;
		times = new ArrayList<>();
		times.add(700); //Spread
		times.add(2700); //Chase
		times.add(3400); //Spread
		times.add(5400); //Chase
		times.add(5800); //Spread
		times.add(Integer.MAX_VALUE);
	}
	
}
