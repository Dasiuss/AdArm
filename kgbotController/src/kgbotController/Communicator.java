package kgbotController;

public class Communicator {

	private SerialManager serial;

	public Communicator() {

		serial = new SerialManager();
		serial.initialize();

	}

	public void move(int s, int speed, int steps) {
		// TODO porusz o step obrotów
		sendVal(1);

		sendVal(s == 0 ? steps : 0);// x
		sendVal(s == 1 ? steps : 0);// y
		sendVal(s == 2 ? steps : 0);// z

	}

	public void moveTo(int s, int speed, int steps) {
		// TODO porusz o text obrotów
		sendVal(2);

		sendVal(s == 0 ? steps : 0);// x
		sendVal(s == 1 ? steps : 0);// y
		sendVal(s == 2 ? steps : 0);// z

	}

	public void undo() {
		// TODO codfnij

	}

	public void redo() {
		// TODO cofnij cofanie

	}

	public void moveVertical(String string) {
		// TODO rusz w pionie

	}

	public void moveSides(int steps) {
		// TODO rusz na boki
		sendVal(3);
		sendVal(steps);
	}

	public void Voff() {
		// TODO podac napiecie

	}

	public void plugOn() {
		// TODO wlacz wtyczke

	}

	public void plugOff() {
		// TODO wylacz wtyczke

	}

	public void kalibracja() {
		// TODO kalibruj

	}

	public void Von(String text) {
		// TODO Auto-generated method stub

	}

	private void sendVal(int value) {
		byte msg = 0;
		msg = (byte) (value % 256);
		serial.write(msg);
		msg = (byte) (value >> 8);
		serial.write(msg);

	}
}
