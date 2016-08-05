package model.elements;

import model.actions.Action;
import model.connection.ComConnection;

public class Robot {

	private ComConnection comConnection;

	public final System system = new System(this);
	public final Laser laser = new Laser(this);
	public final Servo C0 = new Servo(0, this);
	public final Servo C1 = new Servo(1, this);
	public final Servo C2 = new Servo(2, this);

	public final Servo S0 = new Servo(10, this);
	public final Servo S1 = new Servo(11, this);
	public final Servo S2 = new Servo(12, this);

	public Robot(ComConnection comConnection) {
		this.comConnection = comConnection;
	}

	public Robot() {
		comConnection = new ComConnection();
	}

	public Robot(String comName) {
		comConnection = new ComConnection(comName);
	}

	public void execute(Action action) {
		comConnection.send(action.encode().getValue());
	}
}