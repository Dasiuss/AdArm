package model.elements;

public class Robot {
	public final static System system = new System();
	public final static Laser laser = new Laser();
	public final static Servo C0 = new Servo(0);
	public final static Servo C1 = new Servo(1);
	public final static Servo C2 = new Servo(2);

	public final static Servo S0 = new Servo(10);
	public final static Servo S1 = new Servo(11);
	public final static Servo S2 = new Servo(12);
}