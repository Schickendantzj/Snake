import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class jPanel extends JPanel{

	private List<ActionClassListener> listeners = new ArrayList<ActionClassListener>();

	public jPanel(){
		
	}

	public void addListener(ActionClassListener toAdd){
		listeners.add(toAdd);
	}

	public void eventFired() {
		for  (ActionClassListener hl : listeners) {
			hl.EventFired();
		}
		System.out.println("Event Fired");
	}
}
