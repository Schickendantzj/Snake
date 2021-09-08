import java.util.ArrayList;
import java.util.List;

public class ActionClass {
	private List<ActionClassListener> listeners = new ArrayList<ActionClassListener>();
	public ActionClass(){
		
	}
	public void addListener(ActionClassListener toAdd){
		listeners.add(toAdd);
	}
	public void eventFired() {
		System.out.println("Event Fired");
	}
}
