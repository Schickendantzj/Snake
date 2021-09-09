import java.util.ArrayList;
import java.util.List;

// This class is used as the trigger of an action for the ActionClassListener
public class ActionClass {

	// List holds all the Action Listeners
	private List<ActionClassListener> listeners = new ArrayList<ActionClassListener>();

	public ActionClass() {

	}

	// Adds a listener to the list (to trigger later)
	public void addListener(ActionClassListener toAdd) {
		listeners.add(toAdd);
	}

	// Triggers events on the Action Listeners
	public void eventFired() {
		// Loops through all Action listeners and fires the event
		for (ActionClassListener hl : listeners) {
			hl.EventFired();
		}
		System.out.println("Event Fired");
	}
}
