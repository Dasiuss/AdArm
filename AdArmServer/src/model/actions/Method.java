package model.actions;

public enum Method {
	delay(0), move(1), moveFast(2), attach(3), detach(4), setIntensity(5), setStepDelay(6), setStepDelayS(7);

	private final int value;

	private Method(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}