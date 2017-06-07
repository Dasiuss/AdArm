package model.elements;

import java.util.LinkedList;
import java.util.List;

import model.actions.Action;
import model.connection.ComConnection;

public class Robot extends AngledMachine {

	private ComConnection comConnection;

	public final System system = new System(this);
	public final Laser laser = new Laser(this);
	public final Servo C0 = new Servo(0, 30, this);
	public final Servo C1 = new Servo(1, 0, this);
	public final Servo C2 = new Servo(2, 0, this);

	public final Servo S0 = new Servo(10, 90, this);
	public final Servo S1 = new Servo(11, 90, this);
	public final Servo S2 = new Servo(12, 70, this);

	public Robot(ComConnection comConnection) {
		this.comConnection = comConnection;
	}

	public Robot() {
		comConnection = new ComConnection();
	}

	public Robot(String comName) {
		comConnection = new ComConnection(comName);
	}

	void execute(Action action) {
		comConnection.send(action.encode().getValue());
	}

	public List<Integer> getPositions() {
		List<Integer> positions = new LinkedList<>();
		positions.add(laser.getLastPos());

		positions.add(S0.getLastPos());
		positions.add(S1.getLastPos());
		positions.add(S2.getLastPos());

		positions.add(C0.getLastPos());
		positions.add(C1.getLastPos());
		positions.add(C2.getLastPos());
		return positions;
	}

	public void reachTo(double effectorTargetLevel){

	}

	public void levelTo(double effectorTargetLevel){

	}



	public void setPositions(List<Integer> positions) {
		laser.on(positions.get(0));

		S0.moveFast(positions.get(1));

		S1.moveFast(positions.get(2));
		S2.moveFast(positions.get(3));

		C0.moveFast(positions.get(4));
		C1.moveFast(positions.get(5));
		C2.moveFast(positions.get(6));

	}

	public void setPositionsSlow(List<Integer> positions) {
		laser.on(positions.get(0));

		S0.move(positions.get(1));

		S1.move(positions.get(2));
		S2.move(positions.get(3));

		C0.move(positions.get(4));
		C1.move(positions.get(5));
		C2.move(positions.get(6));

	}

	@Override
	double getAlpha() {
		return S1.getLastPos() + s1BaseOffset;
	}

	@Override
	double getBeta() {
		return S2.getLastPos() + s2BaseOffset;
	}

}
