package game;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class GhostStateSwitcher {
	private static Timer timer;
	
	private GhostStateSwitcher(){
	}
	
	public static void go(){
		if (timer != null)
			timer.cancel();
		timer = new Timer();
		Queue<Long> times = new LinkedList<>();
		times.add(new Long(7000));
		times.add(new Long(25000));
		times.add(new Long(7000));
		times.add(new Long(25000));
		times.add(new Long(5000));
		times.add(new Long(25000));
		times.add(new Long(100));
		TimerTask scatter = new TimerTask(){
			@Override
			public void run() {
				GameInfo.scatter();
				if (!times.isEmpty())
					timer.schedule(this, times.poll());
			}
		};
		timer.schedule(scatter, times.poll());
	}
	
	
}
