package ents;

import game.LevelSettings;
import utility.EventHandler;
import utility.EventHandler.EventData;
import utility.Listener;
import utility.Point;

public class TunnelTile extends Static implements Listener{
	
	public TunnelTile(Point p){
		super(p, "tunnelTile", null);
		EventHandler.subscribeEvent("ghost_move", this);
	}


	@Override
	public void onRegister(String key, EventData src) {
		if (key.equals("ghost_move") && src.p.equals(this.p)){
			Ghost g = (Ghost)src.src;
			g.tileSpeedMod(LevelSettings.ghostTnlMlt());
		}
		
	}}
