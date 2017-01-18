package utility;

import utility.EventData;

public interface Listener {
	public void onRegister(String key, EventData src);
}
